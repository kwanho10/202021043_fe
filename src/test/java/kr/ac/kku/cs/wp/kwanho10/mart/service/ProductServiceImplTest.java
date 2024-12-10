package kr.ac.kku.cs.wp.kwanho10.mart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.OrderItem;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;
import kr.ac.kku.cs.wp.kwanho10.mart.mapper.ProductMapper;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.ProductRepository;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.OrderItemRepository;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.OrderRepository;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.repository.UserRepository;

import java.util.Optional;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseProduct_Success() {
        // Given
        Long productId = 1L;
        String userId = "kku_1000";
        int quantity = 2;

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");
        mockProduct.setQuantity(10);
        mockProduct.setPrice(500L);

        User mockUser = new User();
        mockUser.setId(userId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(userRepository.findByUserId(userId)).thenReturn(mockUser);

        // When
        boolean result = productService.purchaseProduct(productId, userId, quantity);

        // Then
        assertTrue(result);
        assertEquals(8, mockProduct.getQuantity()); // 상품가 10에서 8로 감소
        verify(orderRepository, times(1)).save(any(Order.class)); // 주문이 저장되었는지 확인
        verify(orderItemRepository, times(1)).save(any(OrderItem.class)); // 주문 항목이 저장되었는지 확인
    }

    @Test
    void testPurchaseProduct_Failure_InsufficientStock() {
        // Given
        Long productId = 1L;
        String userId = "kku_1000";
        int quantity = 20;

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");
        mockProduct.setQuantity(10);
        mockProduct.setPrice(500L);

        User mockUser = new User();
        mockUser.setId(userId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(userRepository.findByUserId(userId)).thenReturn(mockUser);

        // When
        boolean result = productService.purchaseProduct(productId, userId, quantity);

        // Then
        assertFalse(result);
        assertEquals(10, mockProduct.getQuantity()); // 상품가 변경되지 않음
        verify(orderRepository, times(0)).save(any(Order.class)); // 주문이 저장되지 않아야 함
        verify(orderItemRepository, times(0)).save(any(OrderItem.class)); // 주문 항목도 저장되지 않아야 함
    }

    @Test
    void testPurchaseProduct_Failure_ProductNotFound() {
        // Given
        Long productId = 1L;
        String userId = "kku_1000";
        int quantity = 2;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productService.purchaseProduct(productId, userId, quantity));

        assertEquals("상품을 찾을 수 없습니다.", exception.getMessage());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testPurchaseProduct_Failure_UserNotFound() {
        // Given
        Long productId = 1L;
        String userId = "non_existent_user";
        int quantity = 2;

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");
        mockProduct.setQuantity(10);
        mockProduct.setPrice(500L);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(userRepository.findByUserId(userId)).thenReturn(null);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productService.purchaseProduct(productId, userId, quantity));

        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}
