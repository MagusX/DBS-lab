package com.bmcsdl185.lab.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	Set<String> loggedIns = new LinkedHashSet<>();

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String showlogin(Model model) {
		model.addAttribute("loginStatus", "login");
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value="/home/{name}")
	public String home(@PathVariable("name") String name, Model model) {
		if ((loggedIns.size() != 0 && loggedIns.contains(name))) {
			logger.debug("{}", loggedIns);
			model.addAttribute("name", name);
			return "loginSuccess";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value="/login")
	public String login(Model model, @RequestParam("name") String name, @RequestParam("password") String password) {
		User userInfo;
		userInfo = userService.getLoginInfo("NHANVIEN", "SHA1", name, password);
		if (userInfo == null) {
			userInfo = userService.getLoginInfo("SINHVIEN", "MD5", name, password);
		}

		if (userInfo == null) {
			model.addAttribute("loginStatus", "failed");
			return "login";
		}
		logger.debug("{}", userInfo);
		loggedIns.add(name);
		return "redirect:home/" + name;
	}
}
