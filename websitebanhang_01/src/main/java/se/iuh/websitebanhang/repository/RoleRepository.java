package se.iuh.websitebanhang.repository;

import org.springframework.data.repository.CrudRepository;

import se.iuh.websitebanhang.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByTen(String ten);
}
