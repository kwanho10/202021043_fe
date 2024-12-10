package kr.ac.kku.cs.wp.kwanho10.mart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
    // 카테고리가 전체인 경우 키워드만 검색
	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByNameContainingOrCategoryContaining(String name, String category);
	
	   // 키워드와 카테고리로 검색
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, String category);


	
}