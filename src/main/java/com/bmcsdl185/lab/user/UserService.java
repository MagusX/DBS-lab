package com.bmcsdl185.lab.user;

import com.bmcsdl185.lab.encrypt.Digest;
import com.bmcsdl185.lab.encrypt.Utils;
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
	@Autowired
	private Digest digest;
	@Autowired
	private Utils utils;

	public User getLoginInfo(String userType, String algorithm, String username, String password) {
		String sql;
		String passwordE = "0x" + utils.toHexString(digest.hashBytes(password, algorithm));
		try {
			sql = String.format("SELECT * FROM %s WHERE TENDN = '%s' AND MATKHAU = %s",
					userType, username, passwordE);
			logger.info(sql);
			return jdbcTemplate.queryForObject(sql,
					new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
