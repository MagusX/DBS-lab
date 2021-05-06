package com.bmcsdl185.lab.lop;

import com.bmcsdl185.lab.connection.PoolSerive;
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
@RequestMapping("staff/{staffId}")
public class LopController {
	Logger logger = LoggerFactory.getLogger(LopController.class);
	@Autowired
	private LopService lopService;
	@Autowired
	private PoolSerive poolSerive;

	@RequestMapping(method = RequestMethod.GET, value = "/class")
	public String lopList(Model model, @PathVariable("staffId") String staffId) {
		if (!poolSerive.isLoggedIn(staffId)) return "redirect:/";
		List<Lop> lopList = lopService.getAllLop();
		model.addAttribute("staffId", staffId);
		model.addAttribute("classList", lopList);
		return "classList";
	}

//	@RequestMapping(method = RequestMethod.POST, value = "/class")
//	public String addLop(@PathVariable("staffId") String staffRequest,
//						 @RequestParam("classId") String classId,
//						 @RequestParam("className") String className,
//						 @RequestParam("staffId") String staffId) {
//		if (!poolSerive.isLoggedIn(staffRequest)) return "redirect:/";
//		lopService.addClass(classId, className, staffId);
//		return String.format("redirect:staff/%s/classList", staffRequest);
//	}
//
//	@RequestMapping(method = RequestMethod.PUT, value = "/{classId}")
//	public String editLop(@PathVariable("staffId") String staffRequest,
//						  @PathVariable("classId") String classId,
//						  @RequestParam("className") String className,
//						  @RequestParam("staffId") String staffId) {
//		if (!poolSerive.isLoggedIn(staffRequest)) return "redirect:/";
//		lopService.updateClass(classId, className, staffId);
//		return String.format("redirect:staff/%s/classList", staffRequest);
//	}

//	@RequestMapping(method = RequestMethod.DELETE, value = "/{classId}")
//	public String deleteLop(@PathVariable("staffId") String staffRequest,
//							@RequestParam("classId") String classId,
//		if (!poolSerive.isLoggedIn(staffRequest)) return "redirect:/";
//		lopService.deletClass(classId);
//		return String.format("redirect:staff/%s/classList", staffRequest);
//	}
}
