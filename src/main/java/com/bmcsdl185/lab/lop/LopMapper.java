package com.bmcsdl185.lab.lop;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LopMapper implements RowMapper<Lop> {
	@Override
	public Lop mapRow(ResultSet rs, int rowNum) throws SQLException {
		Lop lop = new Lop();
		lop.setClassId(rs.getString("MALOP"));
		lop.setClassName(rs.getString("TENLOP"));
		lop.setStaffId(rs.getString("MANV"));

		return lop;
	}
}
