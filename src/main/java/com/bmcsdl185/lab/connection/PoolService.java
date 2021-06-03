package com.bmcsdl185.lab.connection;

import com.bmcsdl185.lab.nhanvien.NhanVien;
import com.bmcsdl185.lab.sinhvien.SinhVien;
import com.bmcsdl185.lab.user.User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class PoolService {
	private static Set<NhanVien> staffLoggedIns = new LinkedHashSet<>();
	private static Set<SinhVien> studentsloggedIns = new LinkedHashSet<>();

//	public Boolean isLoggedIn(String id) {
//		Object[] nhanVienArray = loggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
//		NhanVien nhanVien = nhanVienArray.length != 0 ? (NhanVien) nhanVienArray[0] : null;
//		return nhanVien != null;
//	}

	public void newLogin(User user) {
		if (user.getClass().getSimpleName().equals("NhanVien")) {
			staffLoggedIns.add((NhanVien) user);
		} else if (user.getClass().getSimpleName().equals("SinhVien")) {
			studentsloggedIns.add((SinhVien) user);
		}
	}

	public Boolean isLoggedIn(String id) {
//		Object[] staffArray = staffLoggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
//		NhanVien nhanVien = staffArray.length != 0 ? (NhanVien) staffArray[0] : null;
//
//		Object[] studentArray = studentsloggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
//		SinhVien sinhVien = studentArray.length != 0 ? (SinhVien) studentArray[0] : null;
//		return !(nhanVien == null && sinhVien == null);
		return !(this.getUser(NhanVien.class, id) == null && this.getUser(SinhVien.class, id) == null);
	}

	public void logout(String id) {
		staffLoggedIns.removeIf(user -> user.getId().equals(id));
		studentsloggedIns.removeIf(user -> user.getId().equals(id));
	}

	public User getUser(Class type, String id) {
		switch (type.getSimpleName()) {
			case "NhanVien": {
				Object[] staffArray = staffLoggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
				return staffArray.length != 0 ? (NhanVien) staffArray[0] : null;
			}
			case "SinhVien": {
				Object[] studentArray = studentsloggedIns.stream().filter(sv -> sv.getId().equals(id)).toArray();
				return studentArray.length != 0 ? (SinhVien) studentArray[0] : null;
			}
			default:
				return null;
		}
	}
}
