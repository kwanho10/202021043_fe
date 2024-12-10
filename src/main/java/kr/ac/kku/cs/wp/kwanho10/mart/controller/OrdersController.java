package kr.ac.kku.cs.wp.kwanho10.mart.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.mart.service.OrderService;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;


    
    @GetMapping("/list")
    public String listOrders(Model model, HttpSession session,
                             @RequestParam(defaultValue = "0", name = "page") int page,
                             @RequestParam(defaultValue = "10", name = "size") int size) {

        Object sessionObject = session.getAttribute("loginUser");
        if (sessionObject == null) return "redirect:/user/loginPage";
        UserDTO userDTO = (UserDTO) sessionObject;

        // Pageable 객체 수동 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("id")));

        // 페이징된 주문 목록을 가져와서 모델에 추가
        Page<Order> orders = orderService.listOrders(userDTO, pageable);

        model.addAttribute("orders", orders);
        return "orders/ordersList"; // 뷰에 페이징 처리된 주문 목록 전달
    }
    
    
    
    
}

