package se.iuh.websitebanhang.service;

import java.math.BigDecimal;
import java.util.Map;

import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.SanPham;

public interface GioHangService {
	void themSanPham(SanPham sanpham);

    void xoaSanPham(SanPham sanpham);

    Map<SanPham, Integer> getDanhSachSanPham();

    void thanhToan(KhachHang khachHang);

    BigDecimal getTongTien();
}
