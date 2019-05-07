package se.iuh.websitebanhang.service;

import se.iuh.websitebanhang.model.TaiKhoan;

public interface TaiKhoanService {
	boolean save(TaiKhoan taikhoan);

    TaiKhoan findByTen(String ten);
    
    void update(TaiKhoan taiKhoan);
}
