package kr.ac.kku.cs.wp.kwanho10.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kku.cs.wp.kwanho10.mart.entity.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}