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
import java.util.Optional;

@Controller
@RequestMapping(value = "staff/{staffId}/student")
public class SinhVienController {
	@Autowired
	private SinhVienService sinhVienService;
	@Autowired
	private PoolSerive poolSerive;
	@Autowired
	private LopService lopService;

	Logger logger = LoggerFactory.getLogger(SinhVienController.class);

	@RequestMapping(method = RequestMethod.GET, value = {
			"/{classId}",
			"/{classId}/{updateStatus}"
	})
	public String studentsByClass(Model model,
								  @PathVariable("staffId") String staffId,
								  @PathVariable("classId") String classId,
								  @PathVariable("updateStatus") Optional<String> updateStatus) {
		if (!poolSerive.isLoggedIn(staffId)) return "staffLogin";
		List<SinhVien> sinhVienList = sinhVienService.getStudentsByClass(classId);
		model.addAttribute("staffId", staffId);
		model.addAttribute("classId", classId);
		model.addAttribute("classOwner", lopService.isOwner(staffId, classId));
		model.addAttribute("studentList", sinhVienList);
		if (updateStatus.isEmpty()) updateStatus = updateStatus.of("");
		model.addAttribute("updateStatus", updateStatus.get());
		return "studentsByClass";
	}

//	@RequestMapping(method = RequestMethod.GET, value = "/{studentId}/studentInfo")
//	public String student(Model model,
//						  @PathVariable("staffId") String staffId,
//						  @PathVariable("classId") String classId,
//						  @PathVariable("studentId") String studentId) {
//		if (!poolSerive.isLoggedIn(staffId)) return "redirect:staff";
//		model.addAttribute("studentId", studentId);
//		return "studentInfo";
//	}

	@RequestMapping(method = RequestMethod.POST, value = "/{classId}/{studentId}")
	public String updateStudent(Model model,
								@PathVariable("staffId") String staffId,
								@PathVariable("classId") String classId,
								@PathVariable("studentId") String studentId,
								@RequestParam("name") String name,
								@RequestParam("dob") String dob,
								@RequestParam("address") String address,
								@RequestParam("newClassId") String newClassId,
								@RequestParam("username") String username,
								@RequestParam("password") String password) {
		if (!poolSerive.isLoggedIn(staffId) || !lopService.isOwner(staffId, classId)) return "staffLogin";
		String updateStatus;
		if (sinhVienService.updateStudentById(studentId, name, dob, address, newClassId, username, password)) {
			updateStatus = "success";
		} else updateStatus = "failure";
		return String.format("redirect:%s", updateStatus);
	}
}
