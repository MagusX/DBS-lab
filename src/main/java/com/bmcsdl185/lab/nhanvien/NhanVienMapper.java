package com.bmcsdl185.lab.nhanvien;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienMapper implements RowMapper<NhanVien> {
	@Override
	public NhanVien mapRow(ResultSet rs, int rowNum) throws SQLException {
		NhanVien nv = new NhanVien();
		nv.setId(rs.getString("MANV"));
//		nv.setName(rs.getString("HOTEN"));
		nv.setEmail(rs.getString("EMAIL"));
		nv.setSalary(rs.getInt("LUONGCB"));
//		nv.setDnName(rs.getString("TENDN"));
//		nv.setPasswordE(rs.getString("MATKHAU"));

		return nv;
	}
}
