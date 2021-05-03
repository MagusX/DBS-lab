package com.bmcsdl185.lab.user;

import com.bmcsdl185.lab.nhanvien.NhanVien;
import com.bmcsdl185.lab.sinhvien.SinhVien;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = NhanVien.class, name = "NhanVien"),
		@JsonSubTypes.Type(value = SinhVien.class, name = "SinhVien")
})
public abstract class User {
	protected String id;
	protected String name;
	protected String username;
	protected String password;
	protected String passwordE;

	public User() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordE() {
		return passwordE;
	}

	public void setPasswordE(String passwordE) {
		this.passwordE = passwordE;
	}
}
