package kr.ac.kku.cs.wp.kwanho10.mart.service;

import java.util.List;

import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;

public interface ProductService {
	
	// 모든 상품 조회
	public List<ProductDTO> getAllProducts();

	//상품 추가
    public void addProduct(ProductDTO product, UserDTO userDTO);

    //상품 수정
	public void updateProduct(ProductDTO productDTO, UserDTO userDTO);
	
	// 상품 삭제
    public void deleteProduct(Long id);	
    
    // 상품 검색
	public List<ProductDTO> searchProducts(String keyword, String category);
	    
	// 특정 상품 조회
    public ProductDTO getProductById(Long id);

	public boolean purchaseProduct(Long productId, String userId ,int quantity);
    
		
}