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
			if (hasColumn(rs, "TENDN")) {
				user.setUsername(rs.getString("TENDN"));
			}
			if (hasColumn(rs, "MATKHAU")) {
				user.setPasswordE(rs.getString("MATKHAU"));
			}
			if (hasColumn(rs, "PUBKEY")) {
				user.setPublicKey(rs.getString("PUBKEY"));
			}
			user.setId(rs.getString(idLabel));
			user.setName(rs.getString("HOTEN"));
			if (idLabel == "MANV") {
				user.setEmail(rs.getString("EMAIL"));
				user.setSalaryE(rs.getString("LUONG"));
			} else if (idLabel == "MASV") {
				user.setDob(rs.getDate("NGAYSINH"));
				user.setAddress(rs.getString("DIACHI"));
				user.setClassId(rs.getString("MALOP"));
			}
		}
		return user;
	}
}
