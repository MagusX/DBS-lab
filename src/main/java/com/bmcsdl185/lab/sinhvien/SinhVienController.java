package com.bmcsdl185.lab.sinhvien;

import com.bmcsdl185.lab.connection.PoolSerive;
import com.bmcsdl185.lab.lop.LopService;
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
public class SinhVienController {
	@Autowired
	private SinhVienService sinhVienService;
	@Autowired
	private PoolSerive poolSerive;
	@Autowired
	private LopService lopService;

	Logger logger = LoggerFactory.getLogger(SinhVienService.class);

	@RequestMapping(method = RequestMethod.GET, value = "/staff/{staffId}/classList/{classId}")
	public String studentsByClass(Model model,
								  @PathVariable("staffId") String staffId,
								  @PathVariable("classId") String classId) {
		if (!poolSerive.isLoggedIn(staffId)) return "redirect:staff";
		List<SinhVien> sinhVienList = sinhVienService.getStudentsByClass(classId);
		model.addAttribute("staffId", staffId);
		model.addAttribute("classId", classId);
		model.addAttribute("classOwner", lopService.isOwner(staffId, classId));
		model.addAttribute("studentList", sinhVienList);
		return "studentsByClass";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/staff/{staffId}/{classId}/{studentId}/studentInfo")
	public String student(Model model,
						  @PathVariable("staffId") String staffId,
						  @PathVariable("classId") String classId,
						  @PathVariable("studentId") String studentId) {
		if (!poolSerive.isLoggedIn(staffId)) return "redirect:staff";
		model.addAttribute("studentId", studentId);
		return "studentInfo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/staff/{staffId}/{classId}/{studentId}/studentInfo")
	public String updateStudent(@PathVariable("staffId") String staffId,
								@PathVariable("classId") String classId,
								@RequestParam("newStudentId") String newStudentId,
								@RequestParam("name") String name,
								@RequestParam("dob") String dob,
								@RequestParam("address") String address,
								@RequestParam("newClassId") String newClassId,
								@RequestParam("username") String username,
								@RequestParam("password") String password) {
		if (!poolSerive.isLoggedIn(staffId) || !lopService.isOwner(staffId, classId)) return "staffLogin";
		sinhVienService.updateStudentById( newStudentId, name, dob, address, newClassId, username, password);
		return String.format("redirect:/staff/%s/classList/%s", staffId, classId);
	}
}
