package com.bmcsdl185.lab.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User getLoginInfo(String userType, String algorithm, String username, String password) {
		String sql;
		try {
			sql = String.format("SELECT * FROM %s WHERE TENDN = N'%s' AND MATKHAU = HASHBYTES('%s',N'%s')",
					userType, username, algorithm, password);
			return jdbcTemplate.queryForObject(sql,
					new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
