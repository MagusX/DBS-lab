package com.bmcsdl185.lab.diem;

import com.bmcsdl185.lab.encrypt.RSA512;
import com.bmcsdl185.lab.encrypt.Utils;
import com.bmcsdl185.lab.nhanvien.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiemService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RSA512 rsa512;
	@Autowired
	private Utils utils;

	public List<Diem> getStudentTranscript(String studentId, NhanVien loggedIn) {
		try {
			KeyPair pair = rsa512.createKeyPair(loggedIn.getPassword(), loggedIn.getId());
			byte[] privateKey = pair.getPrivate().getEncoded();
			return jdbcTemplate.query(String.format("SELECT * FROM BANGDIEM WHERE MASV = '%s'",
					studentId), new DiemMapper())
					.stream()
					.map(transcript -> {
						String scoreE = transcript.getScoreE();
						if (scoreE != null) {
							try {
								transcript.setScore(Float.parseFloat(new String(rsa512.decrypt(privateKey, "0x" + scoreE))));
							} catch (Exception e) {
								transcript.setScore(0);
							}
						}
						return transcript;
					})
					.collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addScore(String studentId, String subjectId, float score, String staffPublicKey) {
		try {
			String scoreE = utils.toHexString(rsa512.encrypt(utils.toByteArray(staffPublicKey), String.valueOf(score)));
			return jdbcTemplate.update(String.format("INSERT INTO BANGDIEM " +
							"VALUES ('%s','%s', convert(varbinary(max), concat('0x', '%s'), 1))",
					studentId, subjectId, scoreE)) != 0;
		} catch (Exception e) {
			return false;
		}
	}
}
