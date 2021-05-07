package com.bmcsdl185.lab.diem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiemService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Diem> getStudentTranscript(String studentId, String staffId) {
		try {
			return jdbcTemplate.query(String.format("EXEC SP_SEL_DIEM '%s', N'%s'",
					studentId, staffId), new DiemMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean addScore(String studentId, String subjectId, float score, String staffId) {
		return jdbcTemplate.update(String.format("EXEC SP_INS_DIEM '%s', '%s', '%s', '%s'",
				studentId, subjectId, Float.toString(score), staffId)) != 0;
	}
}
