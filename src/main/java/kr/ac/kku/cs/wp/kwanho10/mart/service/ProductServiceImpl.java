package kr.ac.kku.cs.wp.kwanho10.mart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.OrderItem;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;
import kr.ac.kku.cs.wp.kwanho10.mart.mapper.ProductMapper;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.OrderItemRepository;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.OrderRepository;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.ProductRepository;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.mapper.UserMapper;
import kr.ac.kku.cs.wp.kwanho10.user.repository.UserRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private ProductMapper productMapper;  
    
    @Autowired
    private UserMapper userMapper;
    
    
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)  
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductDTO productDTO, UserDTO userDTO) {
    	
        Product product = productMapper.toEntity(productDTO);          
        User user=userRepository.findById(userDTO.getId()).orElseThrow(EntityNotFoundException::new);
        product.setUser(user);
        productRepository.save(product);
    }
    

    @Override
    public void updateProduct(ProductDTO productDTO, UserDTO userDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        User user=userRepository.findById(userDTO.getId()).orElseThrow(EntityNotFoundException::new);
        product.setUser(user);
        
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setItemDetail(productDTO.getItemDetail());
        if(StringUtils.hasText(productDTO.getImgUrl())) {
        	product.setImgUrl(productDTO.getImgUrl());	
        }        
    }
    
    
    
    
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> searchProducts(String keyword, String category) {
        if (category == null || category.equalsIgnoreCase("all")) {
            return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toList());
        }
        return productRepository.findByNameContainingIgnoreCaseAndCategory(keyword, category).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    
    
    @Transactional(readOnly = true)
    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return productMapper.toDto(product);  // 엔티티를 DTO로 변환
    }

    

    
    @Transactional
    public boolean purchaseProduct(Long productId, String userId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        
        User user = userRepository.findByUserId(userId);

        if (product.getQuantity() < quantity) {
            return false; // 상품 부족
        }

        // 상품 상품 차감
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // 주문 생성
        Order order = new Order(user);
        orderRepository.save(order);

        // 주문 항목 추가
        OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice() * quantity);
        orderItemRepository.save(orderItem);

        return true;
    }



	

    
    
}
