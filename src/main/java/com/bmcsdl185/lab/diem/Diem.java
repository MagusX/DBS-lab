package com.bmcsdl185.lab.diem;

public class Diem {
	private String studentId;
	private String subjectId;
	private float score;

	public Diem() {}

	public Diem(String studentId, String subjectId, float score) {
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = score;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Diem{" +
				"studentId='" + studentId + '\'' +
				", subjectId='" + subjectId + '\'' +
				", score=" + score +
				'}';
	}
}
