package com.bmcsdl185.lab.connection;

import com.bmcsdl185.lab.nhanvien.NhanVien;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class PoolSerive {
	private static Set<NhanVien> loggedIns = new LinkedHashSet<>();

	public Set<NhanVien> getLoggedIns() {
		return loggedIns;
	}

	public void setLoggedIns(Set<NhanVien> loggedIns) {
		PoolSerive.loggedIns = loggedIns;
	}

	public void newLogin(NhanVien nhanVien) {
		loggedIns.add(nhanVien);
	}

	public Boolean isLoggedIn(String id) {
		Object[] nhanVienArray = loggedIns.stream().filter(nv -> nv.getId().equals(id)).toArray();
		NhanVien nhanVien = nhanVienArray.length != 0 ? (NhanVien) nhanVienArray[0] : null;
		return nhanVien != null;
	}
}
