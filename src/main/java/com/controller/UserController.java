package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;

	protected PagedListHolder<User> articles;
	private boolean isInitedList = false;

	private void init(String name) {
		if (!isInitedList) {
			isInitedList = true;
			articles = new PagedListHolder<User>();
			articles.setSource(userService.getUsers(name));
			articles.setPageSize(10);
		}
	}

	// TODO merge listSearchedUsers and listOfUsers
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public ModelAndView listSearchedUsers(@ModelAttribute User user, String name) {
		init(name);

		ModelAndView modelAndView = new ModelAndView("list-of-users");

		// implement of getUsers work correct any data and empty data, do not need check @name before invoke getUsers
		List<User> users = userService.getUsers(name);

		modelAndView.addObject("users", users);

		return modelAndView;
	}


	// TODO merge listSearchedUsers and listOfUsers
	@RequestMapping(value="/list")
	public ModelAndView listOfUsers(@ModelAttribute User user) {
		init("");
		ModelAndView modelAndView = new ModelAndView("list-of-users");

		List<User> users = articles.getPageList();
		modelAndView.addObject("users", users);

		return modelAndView;
	}

	@RequestMapping("nextPage")
	public ModelAndView getNextPage() {
		articles.nextPage();
		return new ModelAndView("redirect:list");
	}

	@RequestMapping("prevPage")
	public ModelAndView getPrevPage() {
		articles.previousPage();
		return new ModelAndView("redirect:list");
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addUserPage() {
		ModelAndView modelAndView = new ModelAndView("add-user-form");
		modelAndView.addObject("user", new User());
		isInitedList=false;
		return modelAndView;
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addingUser(@ModelAttribute User user) {
		ModelAndView modelAndView = new ModelAndView("home");
		userService.addUser(user);
		String message = "User was successfully added.";
		modelAndView.addObject("message", message);
		isInitedList=false;
		return modelAndView;
	}

	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-user-form");
		User user = userService.getUser(id);
		modelAndView.addObject("user", user);
		isInitedList=false;
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView edditingUser(@ModelAttribute User user, @PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("home");
		userService.updateUser(user);
		String message = "User was successfully edited.";
		modelAndView.addObject("message", message);
		isInitedList=false;
		return modelAndView;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Integer id) {

		ModelAndView modelAndView = new ModelAndView("home");

		userService.deleteUser(id);

		isInitedList=false;

		String message = "User was successfully deleted.";
		modelAndView.addObject("message", message);
		return modelAndView;
	}
}
