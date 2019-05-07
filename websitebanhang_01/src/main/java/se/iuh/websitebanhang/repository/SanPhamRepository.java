package se.iuh.websitebanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.iuh.websitebanhang.model.SanPham;


public interface SanPhamRepository extends CrudRepository<SanPham, String> {
	@Query("SELECT s FROM SanPham s")
	Page<SanPham> findSanPhams(Pageable pageable); 
}
