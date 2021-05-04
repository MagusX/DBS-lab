package com.bmcsdl185.lab.lop;

import com.bmcsdl185.lab.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Lop> getAllLop() {
		String sql;
		try {
			sql = "SELECT * FROM LOP";
			return jdbcTemplate.query(sql, new LopMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean isOwner(String staffId, String classId) {
		try {
			return jdbcTemplate.queryForObject(
					String.format("SELECT l.* FROM LOP l, NHANVIEN nv " +
						"WHERE l.MALOP = '%s' AND nv.MANV = '%s' AND l.MANV = nv.MANV",
						classId, staffId), new LopMapper()) != null;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
