package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.connection.PoolService;
import com.bmcsdl185.lab.encrypt.AES256;
import com.bmcsdl185.lab.encrypt.Digest;
import com.bmcsdl185.lab.encrypt.RSA512;
import com.bmcsdl185.lab.encrypt.Utils;
import com.bmcsdl185.lab.user.User;
import com.bmcsdl185.lab.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienService {
	Logger logger = LoggerFactory.getLogger(NhanVienService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RSA512 rsa512;
	@Autowired
	private Digest digest;
	@Autowired
	private Utils utils;
	@Autowired
	private PoolService poolService;

	public NhanVien getLoginInfo(String id, String password) {
		try {
			String passwordE = utils.toHexString(digest.hashBytes(password, "SHA1"));
			String sql = String.format("SELECT MANV, HOTEN, EMAIL, LUONG, PUBKEY " +
										"FROM NHANVIEN WHERE MANV = N'%s' AND MATKHAU = 0x%s",
					id, passwordE);
			logger.info(sql);
			return (NhanVien) jdbcTemplate.queryForObject(sql, new UserMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addStaff(String id, String name, String email, int salary, String username, String password) {
		try {
			KeyPair pair = rsa512.createKeyPair(password, id);
			byte[] publicKey = pair.getPublic().getEncoded();

			String salaryE = utils.toHexString(rsa512.encrypt(publicKey, String.valueOf(salary)));
			String passwordE = utils.toHexString(digest.hashBytes(password, "SHA1"));

			int rows = jdbcTemplate.update(String.format("EXEC SP_INS_PUBLIC_ENCRYPT_NHANVIEN '%s','%s','%s','%s','%s','%s','%s'",
					id, name, email, salaryE, username, passwordE, utils.toHexString(publicKey)));
			logger.info(String.format("EXEC SP_INS_PUBLIC_ENCRYPT_NHANVIEN '%s','%s','%s','%s','%s','%s','%s'",
					id, name, email, salaryE, username, passwordE, utils.toHexString(publicKey)));
			return rows != 0;
		} catch (Exception e) {
			logger.warn("{}", e);
			return false;
		}
	}

	public List<NhanVien> getStaffList(NhanVien loggedIn) {
		try {
			return jdbcTemplate.query("EXEC SP_SEL_ENCRYPT_NHANVIEN", new UserMapper())
					.stream()
					.map(user -> {
						NhanVien staff = (NhanVien) user;
						if (staff.getId().equals(loggedIn.getId())) {
							try {
								KeyPair pair = rsa512.createKeyPair(loggedIn.getPassword(), loggedIn.getId());
								byte[] privateKey = pair.getPrivate().getEncoded();
								String salaryE = staff.getSalaryE();
								if (salaryE != null) {
									staff.setSalary(Integer.parseInt(new String(rsa512.decrypt(privateKey, "0x" + salaryE))));
								}
							} catch (Exception e) {
								logger.warn("{}", e);
							}
						}
						return staff;
					})
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
								   String username) {
		try {
			NhanVien staff = (NhanVien) poolService.getUser(NhanVien.class, id);
			String salaryE = utils.toHexString(rsa512.encrypt(utils.toByteArray(staff.getPublicKey()), String.valueOf(salary)));
			return jdbcTemplate.update(String.format("UPDATE NHANVIEN " +
					"SET MANV = '%s', HOTEN = N'%s', EMAIL = '%s', LUONG = convert(varbinary(max), concat('0x', '%s'), 1), TENDN = N'%s' " +
					"WHERE MANV = '%s'", id, name, email, salaryE, username, id)) != 0;
		} catch (Exception e) {
			logger.error("{}", e);
			return false;
		}
	}

	public boolean deleteStaff(String id) {
		return jdbcTemplate.update(String.format("DELETE FROM NHANVIEN WHERE MANV = '%s'", id)) != 0;
	}
}
