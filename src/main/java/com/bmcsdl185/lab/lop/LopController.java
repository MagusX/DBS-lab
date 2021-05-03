package com.bmcsdl185.lab.lop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LopController {
	@Autowired
	private LopService lopService;

	@RequestMapping(method = RequestMethod.GET, value = "staff/{staffId}/classList")
	public String lopList(Model model, @PathVariable("staffId") String staffId) {
		List<Lop> lopList = lopService.getAllLop();
		model.addAttribute("staffId", staffId);
		model.addAttribute("classList", lopList);
		return "classList";
	}
}
