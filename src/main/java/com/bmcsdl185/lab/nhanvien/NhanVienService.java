package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.encrypt.AES256;
import com.bmcsdl185.lab.encrypt.Digest;
import com.bmcsdl185.lab.encrypt.Utils;
import com.bmcsdl185.lab.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienService {
	Logger logger = LoggerFactory.getLogger(NhanVienService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AES256 aes256;
	@Autowired
	private Digest digest;
	@Autowired
	private Utils utils;

	public NhanVien getLoginInfo(String id, String password) {
		String sql;
		String passwordE = "0x" + utils.toHexString(digest.hashBytes(password, "SHA1"));
		try {
			sql = String.format("SELECT * FROM NHANVIEN WHERE MANV = '%s' AND MATKHAU = %s",
					id, passwordE);
			logger.info(sql);
			return (NhanVien) jdbcTemplate.queryForObject(sql, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean addStaff(String id, String name, String email, int salary, String username, String password) {
		SecretKeySpec key = aes256.getKeyIfNullCreate("18120185", "18120185", "123");
		String salaryE;
		String passwordE;

		try {
			salaryE = utils.toHexString(aes256.encrypt(String.valueOf(salary), key));
			passwordE = utils.toHexString(digest.hashBytes(password, "SHA1"));
		} catch (Exception e) {
			logger.error("{}", e);
			return false;
		}

		try {
			int rows = jdbcTemplate.update(String.format("EXEC SP_INS_ENCRYPT_NHANVIEN '%s','%s','%s','%s','%s','%s'",
					id, name, email, salaryE, username, passwordE));
			return rows != 0;
		} catch (Exception e) {
			logger.warn("{}", e);
			return false;
		}
	}

	public List<NhanVien> getStaffList() {
		try {
			return jdbcTemplate.query("EXEC SP_SEL_ENCRYPT_NHANVIEN", new UserMapper())
					.stream()
					.map(user -> (NhanVien) user)
					.collect(Collectors.toList());
		} catch (EmptyResultDataAccessException e) {
			logger.warn("{}", e);
			return null;
		}
	}

	public boolean updateStaffById(String id,
								   String name,
								   String email,
								   int salary,
								   String username,
								   String password) {
		String query = "UPDATE NHANVIEN " +
				"SET MANV = '%s', HOTEN = N'%s', EMAIL = '%s', LUONG = convert(varbinary(max), concat('0x', '%s'), 1), TENDN = N'%s'";
		SecretKeySpec key = aes256.getKeyIfNullCreate("18120185", "18120185", "123");
		String salaryE;
		String passwordE;

		try {
			salaryE = utils.toHexString(aes256.encrypt(String.valueOf(salary), key));
			if (!password.equals("")) {
				passwordE = utils.toHexString(digest.hashBytes(password, "SHA1"));
				query += ", MATKHAU = convert(varbinary(max), concat('0x', '%s'), 1)";
				query = String.format(query + " WHERE MANV = '%s'", id, name, email, salaryE, username, passwordE, id);
			} else {
				query = String.format(query + " WHERE MANV = '%s'", id, name, email, salaryE, username, id);
			}
		} catch (Exception e) {
			logger.error("{}", e);
			return false;
		}
		logger.info(query);
		return jdbcTemplate.update(query) != 0;
	}

	public boolean deleteStaff(String id) {
		return jdbcTemplate.update(String.format("DELETE FROM NHANVIEN WHERE MANV = '%s'", id)) != 0;
	}
}
