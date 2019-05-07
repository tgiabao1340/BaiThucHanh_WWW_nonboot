package se.iuh.websitebanhang.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.Role;
import se.iuh.websitebanhang.model.TaiKhoan;
import se.iuh.websitebanhang.repository.KhachHangRepository;
import se.iuh.websitebanhang.repository.RoleRepository;
import se.iuh.websitebanhang.repository.TaiKhoanRepository;
import se.iuh.websitebanhang.service.TaiKhoanService;
import se.iuh.websitebanhang.service.UserLoginService;

@Controller
public class TaiKhoanController {	
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
	@Autowired
	private TaiKhoanService TaiKhoanService;
	@Autowired
	private KhachHangRepository khachHangRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserLoginService userLoginService;

	@RequestMapping(value = "/dangnhap", method = RequestMethod.GET)
	public String loginPage() {
		return "dangnhap";
	}
	@RequestMapping(value = "/dangnhap", method = RequestMethod.POST)
	public String loginProcess(@RequestParam String username,@RequestParam String password, HttpSession session) {
		TaiKhoan taikhoan = new TaiKhoan(username, password);
		if(userLoginService.checkLogin(taikhoan)) {
			session.setAttribute("taikhoan", userLoginService.getTaiKhoanLogin());
			Set<Role> listRole = userLoginService.getTaiKhoanLogin().getRoles();
			boolean isAdmin = false;
			for (Role role : listRole) {
				if(role.getTen().equalsIgnoreCase("admin")){
					isAdmin = true;
				}
			}
			session.setAttribute("isAdmin", isAdmin);
			return "redirect:/";
		}
		return loginPage();
	}
	@RequestMapping("/dangky")
	public String enterRegister(Model model) {
		KhachHang kh = new KhachHang();	
		TaiKhoan taikhoan = new TaiKhoan();
		kh.setTaiKhoan(taikhoan);
		model.addAttribute("taikhoan",taikhoan);
		return "dangky";
	}
	@RequestMapping( value = "/dangky", method = RequestMethod.POST)
	public String createNewUser(@ModelAttribute("taikhoan") TaiKhoan taiKhoan) {
		taiKhoan.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByTen("user"))));
		if(TaiKhoanService.save(taiKhoan)) {
			khachHangRepository.save(taiKhoan.getKhachHang());
			return "redirect:/";
		}
		return "dangky";


	}
	@RequestMapping(value = "/dangxuat", method = RequestMethod.GET)
	public String logoutPage(HttpSession session) {
		session.removeAttribute("taikhoan");
		session.removeAttribute("isAdmin");
		userLoginService.logout();
		return "redirect:/";
	}

	@RequestMapping(value = "/taikhoanct", method = RequestMethod.GET)
	public String currentUserName(Model model) {
		if(userLoginService.getTaiKhoanLogin()!=null) {
			model.addAttribute("taikhoanct",taiKhoanRepository.findById( userLoginService.getTaiKhoanLogin().getMaTaiKhoan()).get());
			return "taikhoan";
		}
		return "redirect:/";
	}
	@RequestMapping(value = "/update-tk", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("taikhoanct") TaiKhoan taiKhoan) {
		
		TaiKhoanService.update(taiKhoan.getMaTaiKhoan(),taiKhoan.getKhachHang());
		
		return "redirect:/taikhoanct";
	}
}
