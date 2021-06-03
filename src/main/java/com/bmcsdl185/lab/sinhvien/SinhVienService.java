package com.bmcsdl185.lab.sinhvien;

import com.bmcsdl185.lab.encrypt.Digest;
import com.bmcsdl185.lab.encrypt.Utils;
import com.bmcsdl185.lab.user.User;
import com.bmcsdl185.lab.user.UserController;
import com.bmcsdl185.lab.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SinhVienService {
	/*
	 * select sv.* from SINHVIEN sv, LOP l, NHANVIEN nv
	 * where sv.MALOP = l.MALOP and nv.MANV = l.MANV and nv.MANV = @MANV
	 * */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Digest digest;
	@Autowired
	private Utils utils;

	public List<SinhVien> getStudentsByClass(String classId) {
		try {
			String sql = String.format("SELECT sv.* FROM SINHVIEN sv, LOP l " +
					"WHERE sv.MALOP = l.MALOP AND l.MALOP = '%s'", classId);
			return jdbcTemplate.query(sql, new UserMapper())
					.stream()
					.map(user -> (SinhVien) user)
					.collect(Collectors.toList());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean updateStudentById(String studentId,
									 String name,
									 String dob,
									 String address,
									 String newClassId,
									 String username,
									 String password) {
		try {
			String pwdQuery = "";
			if (password != "" && password != null) {
				String passwordE = utils.toHexString(digest.hashBytes(password, "MD5"));
				pwdQuery = String.format(", MATKHAU = CONVERT(VARBINARY(MAX), CONCAT('0x', '%s'), 1) ", passwordE);
			}
			int rows = jdbcTemplate.update(String.format("UPDATE SINHVIEN " +
							"SET HOTEN = N'%s', NGAYSINH = N'%s', DIACHI = '%s', MALOP = N'%s', TENDN = '%s' " +
							pwdQuery +
							"WHERE MASV = N'%s'",
					name, dob, address, newClassId, username, studentId));
			return rows != 0;
		} catch (Exception e) {
			return false;
		}
	}
}
