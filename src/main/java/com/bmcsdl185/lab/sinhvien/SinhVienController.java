package com.bmcsdl185.lab.sinhvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SinhVienController {
	@Autowired
	private SinhVienService sinhVienService;

	@RequestMapping(method = RequestMethod.GET, value = "/staff/{staffId}/classList/{classId}")
	public String studentsByClass(Model model,
								  @PathVariable("staffId") String staffId,
								  @PathVariable("classId") String classId) {
		List<SinhVien> sinhVienList = sinhVienService.getStudentsByClass(classId);
		model.addAttribute("staffId", staffId);
		model.addAttribute("classId", classId);
		model.addAttribute("studentList", sinhVienList);
		return "studentsByClass";
	}
}
