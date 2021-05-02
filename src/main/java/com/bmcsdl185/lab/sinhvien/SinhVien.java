package com.bmcsdl185.lab.sinhvien;

import com.bmcsdl185.lab.user.User;

import java.util.Date;

public class SinhVien extends User {
	private Date dob;
	private String address;
	private String classId;

	public SinhVien() {}

	public SinhVien(String id, String name, Date dob, String address, String classId, String dnName, String password) {
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.classId = classId;
		this.dnName = dnName;
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "SinhVien{" +
				"dob=" + dob +
				", address='" + address + '\'' +
				", classId='" + classId + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", dnName='" + dnName + '\'' +
				", password='" + password + '\'' +
				", passwordE='" + passwordE + '\'' +
				'}';
	}
}
