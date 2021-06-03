package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.connection.PoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/staff")
public class NhanVienController {
	Logger logger = LoggerFactory.getLogger(NhanVienController.class);

	@Autowired
	private NhanVienService nhanVienService;

	@Autowired
	private PoolService poolService;

	private final String redirectUrl = "redirect:/staff/%s/staff/view";

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("loginStatus", "login");
		return "staffLogin";
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public String home(@PathVariable("id") String id) {
		if (poolService.isLoggedIn(id)) {
			return "redirect:" + id + "/staff/view";
		}
		return "staffLogin";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(Model model, @RequestParam("id") String id, @RequestParam("password") String password) {
		NhanVien nhanVien = nhanVienService.getLoginInfo(id, password);

		if (nhanVien == null) {
			model.addAttribute("loginStatus", "failed");
			return "staffLogin";
		}
		nhanVien.setPassword(password);
		poolService.newLogin(nhanVien);
		return "redirect:" + nhanVien.getId();
	}

	@RequestMapping(method = RequestMethod.GET, value="/{staffId}/staff/{mode}")
	public String getStaffList(Model model,
							   @PathVariable("staffId") String staffId,
							   @PathVariable("mode") String mode) {
		logger.info("{}", poolService.isLoggedIn(staffId));
		if (!poolService.isLoggedIn(staffId)) return "login";
		List<NhanVien> staffList = nhanVienService.getStaffList((NhanVien) poolService.getUser(NhanVien.class, staffId));
		model.addAttribute("mode", mode);
		model.addAttribute("staffId", staffId);
		model.addAttribute("staffList", staffList);
		return "staff";
	}

	@RequestMapping(method = RequestMethod.POST, value="/{staffId}/staff/{mode}")
	public String addStaff(@PathVariable("staffId") String staffId,
						   @RequestParam("staffId") String id,
						   @RequestParam("name") String name,
						   @RequestParam("email") String email,
						   @RequestParam("salary") int salary,
						   @RequestParam("username") String username,
						   @RequestParam("password") String password) {
		if (!poolService.isLoggedIn(staffId)) return "login";
		nhanVienService.addStaff(id, name, email, salary, username, password);
		return String.format(redirectUrl, staffId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{staffId}/staff/{id}/delete")
	public String deleteStaff(@PathVariable("staffId") String staffId,
							  @PathVariable("id") String id) {
		if (!poolService.isLoggedIn(staffId)) return "login";
		nhanVienService.deleteStaff(id);
		return String.format(redirectUrl, staffId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{staffId}/staff/update")
	public String updateStaff(@PathVariable("staffId") String staffId,
							  @RequestParam("staffId") String id,
							  @RequestParam("name") String name,
							  @RequestParam("email") String email,
							  @RequestParam("salary") int salary,
							  @RequestParam("username") String username) {
		if (!poolService.isLoggedIn(staffId)) return "login";
		nhanVienService.updateStaffById(id, name, email, salary, username);
		return String.format(redirectUrl, staffId);
	}
}
