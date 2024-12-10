package kr.ac.kku.cs.wp.kwanho10.mart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.mart.repository.OrderRepository;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.repository.UserRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository; 
    
    @Override
    public Page<Order> getOrdersWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> listOrders(UserDTO userDTO, Pageable pageable) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        
        return orderRepository.findByUser(user, pageable); // 페이징 적용된 주문 목록 반환
    }
    
    
    
    
}