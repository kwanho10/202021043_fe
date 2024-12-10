package kr.ac.kku.cs.wp.kwanho10.mart.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;
import kr.ac.kku.cs.wp.kwanho10.mart.dto.PurchaseRequest;
import kr.ac.kku.cs.wp.kwanho10.mart.service.ProductService;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpSession session;
    
   
    
    private boolean checkAdmin() {
    	Object  adminRole =session.getAttribute("adminRole");
    	if(adminRole==null)return false;
    	
    	if(!(boolean)adminRole)return false;
    	
    	return true;
    }
    
    
    
    
    // 상품 목록 조회
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/productList";
    }
    
    

    // 상품 추가 폼
    @GetMapping("/add")
    public String addProductForm(HttpSession session) {
    	if(!checkAdmin())return "redirect:/";
    	
        return "product/productForm";
    }

    // 상품 추가 처리
    @PostMapping("/add")
    public String addProduct(ProductDTO productDTO,  Model model, HttpServletRequest request) { 
    	if(!checkAdmin())return "redirect:/";
    	String uploadDir =request.getServletContext().getRealPath("/")+"resources/uploads";
    	UserDTO userDTO =(UserDTO)request.getSession().getAttribute("loginUser");
    	
    	if (!productDTO.getImgFile().isEmpty()) {
    		 // 저장 경로 및 파일 이름 설정
	            String fileName = System.currentTimeMillis() + "_" + productDTO.getImgFile().getOriginalFilename();
	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs(); // 디렉토리 생성
	            }
	
	            File destination = new File(uploadPath, fileName);
	            try {
	            	productDTO.getImgFile().transferTo(destination); // 파일 저장
	            	productDTO.setImgUrl("/uploads/" + fileName);
	                model.addAttribute("imgUrl", "/uploads/" + fileName);
	            } catch (IOException e) {
	                e.printStackTrace();
	                model.addAttribute("error", "이미지 업로드 실패");
	            }
        }
    	
        productService.addProduct(productDTO, userDTO);
        return "redirect:/product/list";
    }

    
    // 상세 페이지
    @GetMapping("/detail/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
    	ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/productView";
    }
    
    
    
    // 상품 수정 폼
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
    	if(!checkAdmin())return "redirect:/";
    	
    	ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/productEdit";
    }
    
    

    // 상품 수정 처리
    @PostMapping("/edit")
    public String editProduct(ProductDTO productDTO , Model model, HttpServletRequest request) {
    	if(!checkAdmin())return "redirect:/";
    	UserDTO userDTO =(UserDTO)request.getSession().getAttribute("loginUser");
    	
    	String uploadDir =request.getServletContext().getRealPath("/")+"resources/uploads";    	
    	if (!productDTO.getImgFile().isEmpty()) {
    		
	            String fileName = System.currentTimeMillis() + "_" + productDTO.getImgFile().getOriginalFilename();
	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs(); // 디렉토리 생성
	            }
	
	            File destination = new File(uploadPath, fileName);
	            try {
	            	productDTO.getImgFile().transferTo(destination); // 파일 저장
	            	productDTO.setImgUrl("/uploads/" + fileName);	    
	            } catch (IOException e) {
	                e.printStackTrace();
	                model.addAttribute("error", "이미지 업로드 실패");
	            }
       }    	
        productService.updateProduct(productDTO,userDTO);
        return "redirect:/product/list";
    }
    
    
    
    

    // 상품 삭제
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteProduct(@PathVariable("id") Long id) {    	
    	if(!checkAdmin()) {
    		 throw new IllegalArgumentException("권한이 없습니다.");
    	}
    	
        productService.deleteProduct(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    
    
    @GetMapping("/search")
    @ResponseBody
    public List<ProductDTO> searchProducts(@RequestParam("query") String query, 
    		@RequestParam(value = "category", required = false, defaultValue = "all") String category) {
        // 검색 결과 반환
        return productService.searchProducts(query, category);
    }

    
    
    
    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<?> purchaseProduct(@RequestBody PurchaseRequest purchaseRequest, HttpSession session) {
    	Object loginUser=session.getAttribute("loginUser");
    	if(loginUser==null) {
    		 return ResponseEntity.badRequest().body("권한 오류");
    	}
    	
    	UserDTO user=(UserDTO)loginUser;
    	
        try {
            boolean success = productService.purchaseProduct(purchaseRequest.getProductId(),user.getId(), purchaseRequest.getQuantity());
            if (success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("상품 부족");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구매 처리 중 오류 발생");
        }
    }

    
    
    
    
}
