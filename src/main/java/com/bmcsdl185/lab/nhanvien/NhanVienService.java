package com.bmcsdl185.lab.nhanvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<NhanVien> getAllNhanVien() {
		String sql = "EXEC SP_SEL_NHANVIEN";
		return jdbcTemplate.query(sql, new NhanVienMapper());
	}
}
