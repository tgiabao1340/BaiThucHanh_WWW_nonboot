package se.iuh.websitebanhang.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import se.iuh.websitebanhang.model.ChiTietHoaDon;
import se.iuh.websitebanhang.model.HoaDon;
import se.iuh.websitebanhang.model.KhachHang;
import se.iuh.websitebanhang.model.SanPham;
import se.iuh.websitebanhang.repository.HoaDonRepository;
import se.iuh.websitebanhang.repository.SanPhamRepository;

@Service("GioHangService")
@Scope(value = 	WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class GioHangServiceImpl implements GioHangService {
	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Autowired
	private HoaDonRepository hoaDonRepository;

	private Map<SanPham, Integer> danhSachSanPham = new HashMap<>();
	
	@Override
	@Transactional
	public void themSanPham(SanPham sanpham) {
		// TODO Auto-generated method stub
		if(danhSachSanPham.containsKey(sanpham)) {
			danhSachSanPham.replace(sanpham, danhSachSanPham.get(sanpham)+1);
		}
		else {
			danhSachSanPham.put(sanpham, 1);
		}
	}

	@Override
	@Transactional
	public void xoaSanPham(SanPham sanpham) {
		// TODO Auto-generated method stub
		if(danhSachSanPham.containsKey(sanpham)) {
			if(danhSachSanPham.get(sanpham) > 1 )
				danhSachSanPham.replace(sanpham, danhSachSanPham.get(sanpham)-1);
			else 
				danhSachSanPham.remove(sanpham);
		}

	}

	@Override
	@Transactional
	public Map<SanPham, Integer> getDanhSachSanPham() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableMap(danhSachSanPham);
	}

	@Override
	@Transactional
	public void thanhToan(KhachHang khachHang) {
		HoaDon hd = new HoaDon();
		List<ChiTietHoaDon> listct = new ArrayList<>();
		hd.setNgayLap(LocalDate.now());
		SanPham sanpham;
		for (Map.Entry<SanPham, Integer> entry : danhSachSanPham.entrySet()) {
			Optional<SanPham> temp = sanPhamRepository.findById(entry.getKey().getMaSanPham());
			sanpham = temp.get();
			if(sanpham.getSoLuongTon()<entry.getValue()) {
				
				return;
			}
			ChiTietHoaDon cthd = new ChiTietHoaDon(sanpham.getDonGia(),entry.getValue(),sanpham);
			cthd.setHoaDon(hd);
			listct.add(cthd);

			entry.getKey().setSoLuongTon(sanpham.getSoLuongTon()-entry.getValue());
		}
		hd.setDssp(listct);
		hd.setKhachHang(khachHang);
		hoaDonRepository.save(hd);
		sanPhamRepository.saveAll(danhSachSanPham.keySet());
		danhSachSanPham.clear();
	}
	
	@Override
	@Transactional
	public BigDecimal getTongTien() {
		BigDecimal tongTien = new BigDecimal(0);
		for (Map.Entry<SanPham, Integer> entry : danhSachSanPham.entrySet()) {
			tongTien.add(BigDecimal.valueOf(entry.getKey().getDonGia()));
		}
		return tongTien;
	}

}
