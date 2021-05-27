package com.bmcsdl185.lab.user;

import com.bmcsdl185.lab.connection.PoolService;
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

	@Autowired
	private UserService userService;
	@Autowired
	private PoolService poolService;

	@RequestMapping(method = RequestMethod.GET)
	public String showlogin(Model model) {
		model.addAttribute("loginStatus", "login");
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST, value="/login")
	public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
		User userInfo;
		userInfo = userService.getLoginInfo("NHANVIEN", "SHA1", username, password);
		if (userInfo == null) {
			userInfo = userService.getLoginInfo("SINHVIEN", "MD5", username, password);
		}

		if (userInfo == null) {
			model.addAttribute("loginStatus", "failed");
			return "login";
		}
		poolService.newLogin(userInfo);
		return "redirect:staff/" + userInfo.getId() + "/staff/view";
	}

	@RequestMapping(method = RequestMethod.GET, value="/logout/{staffId}")
	public String logout(@PathVariable("staffId") String staffId) {
		poolService.logout(staffId);
		return "redirect:/";
	}
}
