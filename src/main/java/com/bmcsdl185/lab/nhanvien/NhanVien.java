package com.bmcsdl185.lab.nhanvien;

import com.bmcsdl185.lab.user.User;

public class NhanVien extends User {
	private String email;
	private int salary;
	private String salaryE;
	private String publicKey;

	public NhanVien() {}

	public NhanVien(String id, String name, String email, int salary, String username, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.salary = salary;
		this.username = username;
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

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPublicKey() { return this.publicKey; }

	@Override
	public String toString() {
		return "NhanVien{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", salaryE=" + salaryE +
				", username='" + username + '\'' +
				", passwordE='" + passwordE + '\'' +
				'}';
	}
}
