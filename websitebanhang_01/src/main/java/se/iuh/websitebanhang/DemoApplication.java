package se.iuh.websitebanhang;



import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.Role;
import se.iuh.websitebanhang.model.SanPham;
import se.iuh.websitebanhang.model.TaiKhoan;
import se.iuh.websitebanhang.repository.KhachHangRepository;
import se.iuh.websitebanhang.repository.RoleRepository;
import se.iuh.websitebanhang.service.TaiKhoanService;

@Controller
public class DemoApplication {

	@Autowired
    private TaiKhoanService taiKhoanService;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private KhachHangRepository khachHangRepository;
	
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			TaiKhoan taiKhoan = new TaiKhoan("admin", "123456");
			KhachHang kh = new KhachHang();
			kh.setTaiKhoan(taiKhoan);
			taiKhoan.setKhachHang(kh);
			Role role_admin = new Role();
			role_admin.setId((long) 1);
			role_admin.setTen("admin");
			Role role_user = new Role();
			role_user.setId((long) 2);
			role_user.setTen("user");
			taiKhoan.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByTen("user"),roleRepository.findByTen("admin"))));		
			roleRepository.save(role_admin);
			roleRepository.save(role_user);
			//khachHangRepository.save(kh);
			
			taiKhoanService.save(taiKhoan);
		
		};
	}
}
