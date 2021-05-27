package com.bmcsdl185.lab.diem;

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
@RequestMapping(value = "/staff/{staffId}/score/{classId}/{studentId}")
public class DiemController {
	Logger logger = LoggerFactory.getLogger(DiemController.class);
	@Autowired
	private DiemService diemService;
	@Autowired
	private PoolService poolService;
	private final String redirectUrl = "redirect:/staff/%s/score/%s/%s";

	@RequestMapping(method = RequestMethod.GET)
	public String studentTranscript(Model model,
									@PathVariable("staffId") String staffId,
									@PathVariable("classId") String classId,
									@PathVariable("studentId") String studentId) {
		if (!poolService.isLoggedIn(staffId)) return "staffLogin";
		List<Diem> transcript = diemService.getStudentTranscript(studentId, staffId);
		model.addAttribute("studentId", studentId);
		model.addAttribute("classId", classId);
		model.addAttribute("transcript", transcript);
		return "studentTranscript";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addStudentScore(@PathVariable("staffId") String staffId,
								  @PathVariable("classId") String classId,
								  @PathVariable("studentId") String studentId,
								  @RequestParam("subjectId") String subjectId,
								  @RequestParam("score") float score) {
		if (!poolService.isLoggedIn(staffId)) return "staffLogin";
		diemService.addScore(studentId, subjectId, score, staffId);
		return String.format(redirectUrl, staffId, classId, studentId);
	}
}
