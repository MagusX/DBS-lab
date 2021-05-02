package com.bmcsdl185.lab.nhanvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nhanvien")
public class NhanVienController {
	@Autowired
	private NhanVienService nhanVienService;

	@RequestMapping(method = RequestMethod.GET)
	public List<NhanVien> getAllNhanVien() {
		return nhanVienService.getAllNhanVien();
	}
}
