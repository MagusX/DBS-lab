package com.bmcsdl185.lab.lop;

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
@RequestMapping("staff/{staffId}/class")
public class LopController {
	Logger logger = LoggerFactory.getLogger(LopController.class);
	@Autowired
	private LopService lopService;
	@Autowired
	private PoolService poolService;
	private final String redirectUrl = "redirect:/staff/%s/class/view/view";

	@RequestMapping(method = RequestMethod.GET, value = "/view/{mode}")
	public String lopList(Model model, @PathVariable("staffId") String staffId, @PathVariable("mode") String mode) {
		if (!poolService.isLoggedIn(staffId)) return "staffLogin";
		List<Lop> lopList = lopService.getAllLop();
		model.addAttribute("staffId", staffId);
		model.addAttribute("classList", lopList);
		model.addAttribute("mode", mode);
		return "classList";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addLop(@PathVariable("staffId") String staffRequest,
						 @RequestParam("classId") String classId,
						 @RequestParam("className") String className,
						 @RequestParam("staffId") String staffId) {
		if (!poolService.isLoggedIn(staffRequest)) return "staffLogin";
		lopService.addClass(classId, className, staffId);
		return String.format(redirectUrl, staffRequest);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{classId}")
	public String editLop(@PathVariable("staffId") String staffRequest,
						  @PathVariable("classId") String classId,
						  @RequestParam("className") String className,
						  @RequestParam("staffId") String staffId) {
		if (!poolService.isLoggedIn(staffRequest)) return "staffLogin";
		lopService.updateClass(classId, className, staffId);
		return String.format(redirectUrl, staffRequest);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{classId}/delete")
	public String deleteLop(@PathVariable("staffId") String staffRequest,
							@PathVariable("classId") String classId) {
		if (!poolService.isLoggedIn(staffRequest)) return "staffLogin";
		lopService.deletClass(classId);
		return String.format(redirectUrl, staffRequest);
	}
}
