package se.iuh.websitebanhang.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import se.iuh.websitebanhang.model.TaiKhoan;
import se.iuh.websitebanhang.repository.TaiKhoanRepository;

@Service("taiKhoanService")
public class TaiKhoanServiceImpl implements TaiKhoanService {
	
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
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
}
