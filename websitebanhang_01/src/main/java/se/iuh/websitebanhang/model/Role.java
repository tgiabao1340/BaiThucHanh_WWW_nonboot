package se.iuh.websitebanhang.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8908493656175044529L;

	@Id
	private Long id;

	private String ten;

	@ManyToMany(mappedBy = "roles")
	private Set<TaiKhoan> taikhoan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Set<TaiKhoan> getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(Set<TaiKhoan> taikhoan) {
		this.taikhoan = taikhoan;
	}
}