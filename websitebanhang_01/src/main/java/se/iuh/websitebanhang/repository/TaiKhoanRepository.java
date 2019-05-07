package se.iuh.websitebanhang.repository;
import org.springframework.data.repository.CrudRepository;

import se.iuh.websitebanhang.model.TaiKhoan;

public interface TaiKhoanRepository extends CrudRepository<TaiKhoan, String>{
	TaiKhoan findByTenTaiKhoan(String ten);
}
