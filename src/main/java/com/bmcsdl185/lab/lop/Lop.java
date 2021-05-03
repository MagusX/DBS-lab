package com.bmcsdl185.lab.lop;

public class Lop {
	private String classId;
	private String className;
	private String staffId;

	public Lop() { }

	public Lop(String classId, String className, String staffId) {
		this.classId = classId;
		this.className = className;
		this.staffId = staffId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "Lop{" +
				"classId='" + classId + '\'' +
				", className='" + className + '\'' +
				", staffId='" + staffId + '\'' +
				'}';
	}
}
