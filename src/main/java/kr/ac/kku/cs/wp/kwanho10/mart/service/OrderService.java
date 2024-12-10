package kr.ac.kku.cs.wp.kwanho10.mart.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;

public interface OrderService {
   
	Page<Order> getOrdersWithPagination(Pageable pageable); // 페이징 처리된 주문 조회

	Page<Order> listOrders(UserDTO userDTO,  Pageable pageable);
}