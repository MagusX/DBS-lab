package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.user.User;

public class NhanVien extends User {
	private String email;
	private int salary;
	private String salaryE;

	public NhanVien() {}

	public NhanVien(String id, String name, String email, int salary, String dnName, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.salary = salary;
		this.dnName = dnName;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSalary() {
		return salary;
	}

	public String getSalaryE() {
		return salaryE;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public void setSalaryE(String salaryE) {
		this.salaryE = salaryE;
	}

	@Override
	public String toString() {
		return "NhanVien{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", salaryE=" + salaryE +
				", dnName='" + dnName + '\'' +
				", passwordE='" + passwordE + '\'' +
				'}';
	}
}
