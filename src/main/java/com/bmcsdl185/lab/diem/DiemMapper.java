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
		return new Diem(
				rs.getString("MASV"),
				rs.getString("MAHP"),
				Float.parseFloat(rs.getString("DIEMTHI"))
		);
	}
}
