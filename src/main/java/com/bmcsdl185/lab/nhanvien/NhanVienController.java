package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.user.User;
import com.bmcsdl185.lab.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@RequestMapping("/staff")
public class NhanVienController {
	Set<User> loggedIns = new LinkedHashSet<>();

	@Autowired
	private NhanVienService nhanVienService;

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("loginStatus", "login");
		return "staffLogin";
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public String home(@PathVariable("id") String id, Model model) {
		if (loggedIns.size() != 0) {
			Object[] nhanVienArray = loggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
			NhanVien nhanVien = nhanVienArray.length != 0 ? (NhanVien) nhanVienArray[0] : null;
			if (nhanVien != null) {
				model.addAttribute("username", nhanVien.getName());
				return "loginSuccess";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(Model model, @RequestParam("id") String id, @RequestParam("password") String password) {
		User staff = nhanVienService.getLoginInfo("SHA1", id, password);

		if (staff == null) {
			model.addAttribute("loginStatus", "failed");
			return "staffLogin";
		}
		loggedIns.add(staff);
		return "redirect:" + staff.getId();
	}
}
