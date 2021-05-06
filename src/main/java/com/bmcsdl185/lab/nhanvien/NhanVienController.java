package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.connection.PoolSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/staff")
public class NhanVienController {
	@Autowired
	private NhanVienService nhanVienService;

	@Autowired
	private PoolSerive poolService;

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("loginStatus", "login");
		return "staffLogin";
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public String home(@PathVariable("id") String id) {
		if (poolService.isLoggedIn(id)) {
			return "redirect:" + id + "/class/view/view";
		}
		return "staffLogin";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(Model model, @RequestParam("id") String id, @RequestParam("password") String password) {
		NhanVien nhanVien = nhanVienService.getLoginInfo("SHA1", id, password);

		if (nhanVien == null) {
			model.addAttribute("loginStatus", "failed");
			return "staffLogin";
		}
		poolService.newLogin(nhanVien);
		return "redirect:" + nhanVien.getId();
	}
}
