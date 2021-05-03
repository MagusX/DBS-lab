package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class NhanVienService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public NhanVien getLoginInfo(String algorithm, String id, String password) {
		String sql;
		try {
			sql = String.format("SELECT * FROM NHANVIEN WHERE MANV = '%s' AND MATKHAU = HASHBYTES('%s',N'%s')",
					id, algorithm, password);
			return (NhanVien) jdbcTemplate.queryForObject(sql, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
