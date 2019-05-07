package se.iuh.websitebanhang.service;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.TaiKhoan;
import se.iuh.websitebanhang.repository.KhachHangRepository;
import se.iuh.websitebanhang.repository.TaiKhoanRepository;

@Service("taiKhoanService")
public class TaiKhoanServiceImpl implements TaiKhoanService {
	
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
	@Autowired
	private KhachHangRepository khachHangRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public boolean save(TaiKhoan taikhoan) {
		if(taiKhoanRepository.findByTenTaiKhoan(taikhoan.getTenTaiKhoan()) == null) {
			taikhoan.setMatKhau(bCryptPasswordEncoder.encode(taikhoan.getMatKhau()));
			taiKhoanRepository.save(taikhoan);
			return true;
		}
		return false;
	}
	
	@Override
	public TaiKhoan findByTen(String ten) {
		return taiKhoanRepository.findByTenTaiKhoan(ten);
	}
	
	@Override
	@Transactional
	public void update(TaiKhoan taiKhoan) {
		Optional<TaiKhoan> tk = taiKhoanRepository.findById(taiKhoan.getMaTaiKhoan());
		System.out.println(taiKhoan.getKhachHang());
		if(tk.isPresent()) {
			taiKhoan.getKhachHang().setMaKhachHang(tk.get().getMaTaiKhoan());
			tk.get().setKhachHang(taiKhoan.getKhachHang());
			taiKhoanRepository.save(tk.get());
		}
		//khachHangRepository.save(taiKhoan.getKhachHang());
		//taiKhoanRepository.save(taiKhoan);
	}
}
