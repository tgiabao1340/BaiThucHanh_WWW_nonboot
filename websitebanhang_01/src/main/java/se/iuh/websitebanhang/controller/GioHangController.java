package se.iuh.websitebanhang.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.TaiKhoan;
import se.iuh.websitebanhang.repository.KhachHangRepository;
import se.iuh.websitebanhang.repository.SanPhamRepository;
import se.iuh.websitebanhang.service.GioHangService;
import se.iuh.websitebanhang.service.TaiKhoanService;
import se.iuh.websitebanhang.service.UserLoginService;


@Controller
public class GioHangController {

	@Autowired
	private GioHangService gioHangService;

	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Autowired
	private KhachHangRepository khachHangRepository;
	
	@Autowired
	private TaiKhoanService taiKhoanService;
	@Autowired
	private UserLoginService userLoginService;
	
	@GetMapping("/giohang")
	public String xemGioHang(Model model) {
		model.addAttribute("giohang",gioHangService.getDanhSachSanPham());
		model.addAttribute("tongtien",gioHangService.getTongTien());
		return "giohang";
	}
	@GetMapping("/giohang/themsanpham/{maSanPham}")
	public String themSanPham(@PathVariable("maSanPham") String maSanPham) {
		sanPhamRepository.findById(maSanPham).ifPresent(gioHangService::themSanPham);
		return "redirect:/giohang";
	}
	@GetMapping("/giohang/xoasanpham/{maSanPham}")
	public String xoaSanPham(@PathVariable("maSanPham") String maSanPham) {
		sanPhamRepository.findById(maSanPham).ifPresent(gioHangService::xoaSanPham);
		return "redirect:/giohang";
	}
	@GetMapping("/giohang/thanhtoan")
	public String thanhtoan() {
		TaiKhoan taiKhoan = userLoginService.getTaiKhoanLogin();
		if(taiKhoan != null) {
			Optional<KhachHang> khachHang = khachHangRepository.findById(taiKhoan.getMaTaiKhoan());
			if(khachHang.isPresent()) {
				gioHangService.thanhToan(khachHang.get());
				return "redirect:/";
			}
		}
		return "redirect:/giohang";
	}
}
