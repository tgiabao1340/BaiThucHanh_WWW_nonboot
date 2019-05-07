package se.iuh.websitebanhang.service;

import se.iuh.websitebanhang.model.TaiKhoan;

public interface UserLoginService {
	public boolean checkLogin(TaiKhoan taikhoan);
	public void logout();
	public TaiKhoan getTaiKhoanLogin();
	public void setTaiKhoanLogin(TaiKhoan taiKhoan);
}
