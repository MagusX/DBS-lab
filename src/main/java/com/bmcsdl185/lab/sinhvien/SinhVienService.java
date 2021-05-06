package com.bmcsdl185.lab.sinhvien;

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
			int rows = jdbcTemplate.update(String.format("EXEC SP_UPDATE_SINHVIEN N'%s', N'%s', '%s', N'%s', '%s', N'%s', N'%s'",
					studentId, name, dob, address, newClassId, username, password));
			return rows != 0;
		} catch (Exception e) {
			return false;
		}
	}
}
