package se.iuh.websitebanhang.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.iuh.websitebanhang.model.NhaSanXuat;
import se.iuh.websitebanhang.model.SanPham;
import se.iuh.websitebanhang.repository.SanPhamRepository;

@Controller
public class AdminController {
	
	@Autowired
	private SanPhamRepository sanPhamRepository;
	
	@RequestMapping(value = "/quanly/sanpham")
	public String listSanPham(Model model,
			@RequestParam(name="page",required = false, defaultValue = "1") Optional<Integer> page ,
			@RequestParam(name="size",required = false, defaultValue = "12") Integer size,
			@RequestParam(name="sort",required = false, defaultValue = "ASC") String sort
			) {
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		// Page nó đếm từ 0  - > end - Nên phải trừ giá trị hiện tại xuống 1 để khớp với cái Pageable
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);
		Page<SanPham> pageSanPham = sanPhamRepository.findSanPhams(pageable);
		int totalPage = pageSanPham.getTotalPages();
		if(totalPage>0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		SanPham sanphamnew = new SanPham();
		//TEST
		Random rd = new Random();
		int maSp = rd.nextInt();
		//----
		sanphamnew.setMaSanPham(""+maSp);
		sanphamnew.setNhaSanXuat(new NhaSanXuat("nsx"+maSp,"abc","odaudo"));
		model.addAttribute("sanphamnew", sanphamnew);
		model.addAttribute("listSanPham", sanPhamRepository.findSanPhams(pageable));
		return "quanly-sanpham";
	}
	@PostMapping("/quanly/sanpham")
	public String addSanPham(@ModelAttribute(name = "sanpham") SanPham sanPham) {
		if(sanPhamRepository.save(sanPham).equals(sanPham)) {
			return "test-result";
		}
		return null;
	}
	
}
