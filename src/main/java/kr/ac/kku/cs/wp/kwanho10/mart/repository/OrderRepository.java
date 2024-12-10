package kr.ac.kku.cs.wp.kwanho10.mart.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;


public interface OrderRepository extends JpaRepository<Order, Long> {
	
	 // 페이징 처리된 주문 목록을 반환하는 메서드
	@EntityGraph(attributePaths = "orderItems")
	Page<Order> findByUser(User user, Pageable pageable);
    
}
