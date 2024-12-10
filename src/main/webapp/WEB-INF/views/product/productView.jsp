<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>    
    <%@ include file="../include/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <title>상품 상세</title>
</head>
<body>
<%@ include file="../include/headMenu.jsp"%>
    <div class="container">
        <h2>상품 상세</h2>
        <div class="product-detail">
            <p><strong>상품명:</strong> ${product.name}</p>            
            <p><strong>카테고리:</strong> ${product.category}</p>
            <p><strong>수량:</strong> ${product.quantity}개</p>            
            <p><strong>가격:</strong> ${product.price}원</p>
  			
  			<p><strong>내용:</strong>   				
  				<p style="white-space: pre-line;">
				    ${product.itemDetail}
				</p>
  			</p>                      
                                            
			<p><strong>이미지:</strong> 
			
				
			  	 <c:choose>		       
			        <c:when test="${fn:startsWith(product.imgUrl, 'http')}">
			            <img src="${product.imgUrl}" alt="${product.name}" class="product-image"  style="width: 250px" />
			        </c:when>		      
		         <c:otherwise>
		            <img src="${pageContext.request.contextPath}/resources/${product.imgUrl}" alt="${product.name}" class="product-image" style="width: 250px"  />
		         </c:otherwise>
		    </c:choose>
					    
			</p>
        </div>
        
        


        <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary">목록으로 돌아가기</a>
        
        
       <c:if test="${fn:contains(roles, '관리자') or fn:contains(roles, '판매자')}">
		 <button class="btn btn-edit" onclick="location.href='${pageContext.request.contextPath}/product/edit/${product.id}'">수정</button>
       	  <button class="btn btn-delete" onclick="deleteProduct('${product.id}')">삭제</button>                   
       </c:if>            		                
    </div>
    
       
    
       
    <script>
 	// 상품 삭제 AJAX 호출
	function deleteProduct(productId) {
	    if (confirm("정말 삭제하시겠습니까?")) {
	        fetch('${pageContext.request.contextPath}/product/delete/' + productId, {
	            method: 'DELETE',
	        })
	        .then(response => {
	            if (response.ok) {
	                alert("삭제 완료");
	                location.href="${pageContext.request.contextPath}/product/list";
	            } else {
	                alert("삭제 실패");
	            }
	        })
	        .catch(error => {
	            alert("삭제 중 오류가 발생했습니다.");
	            console.error(error);
	        });
	    }
	}
    </script> 
    
    
    
    
    
    
    
    
</body>
</html>
