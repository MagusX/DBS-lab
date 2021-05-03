package com.bmcsdl185.lab.user;
import com.bmcsdl185.lab.nhanvien.NhanVien;
import com.bmcsdl185.lab.sinhvien.SinhVien;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
	private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		String idLabel;
		User user = null;
		if (hasColumn(rs, "MANV")) {
			idLabel = "MANV";
			user = new NhanVien();
		} else if (hasColumn(rs, "MASV")) {
			idLabel = "MASV";
			user = new SinhVien();
		} else return null;

		if (user != null) {
			user.setId(rs.getString(idLabel));
			user.setName(rs.getString("HOTEN"));
			user.setUsername(rs.getString("TENDN"));
			user.setPasswordE(rs.getString("MATKHAU"));
		}

		return user;
	}
}
