package se.iuh.websitebanhang.service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import se.iuh.websitebanhang.model.TaiKhoan;

@Service("UserLoginService")
@Scope(value = 	WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class UserLoginServiceImpl  implements UserLoginService {

	@Autowired
	private TaiKhoanService taiKhoanService;
	
	private BCryptPasswordEncoder byBCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	private TaiKhoan taikhoandangnhap;
	
	@Override
	public boolean checkLogin(TaiKhoan taikhoan) {
		TaiKhoan tk = taiKhoanService.findByTen(taikhoan.getTenTaiKhoan());
		if(tk!=null) {
			if(byBCryptPasswordEncoder.matches(taikhoan.getMatKhau(), tk.getMatKhau())) {
				setTaiKhoanLogin(tk);
				return true;
			}
		}
		return false;
	}

	@Override
	public TaiKhoan getTaiKhoanLogin() {
		return this.taikhoandangnhap;
	}
	
	@Override
	public void setTaiKhoanLogin(TaiKhoan taiKhoan) {
		
		this.taikhoandangnhap = taiKhoan;
	}

	@Override
	public void logout() {
		taikhoandangnhap = null;
	}
	
}
