package com.bmcsdl185.lab.diem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiemMapper implements RowMapper<Diem> {
	Logger logger = LoggerFactory.getLogger(DiemMapper.class);
	@Override
	public Diem mapRow(ResultSet rs, int rowNum) throws SQLException {
		Diem diem = new Diem();
		diem.setStudentId(rs.getString("MASV"));
		diem.setSubjectId(rs.getString("MAHP"));
		diem.setScoreE(rs.getString("DIEMTHI"));
		return diem;
	}
}
