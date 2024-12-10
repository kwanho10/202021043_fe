<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib  prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>    
    <%@ include file="../include/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <title>상품 관리</title>
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <%@ include file="../include/headMenu.jsp"%>

    <div class="container">
        <h2>상품</h2>
        
        
			
			<!-- 변경된 검색 필터 -->
			<div class="filter-container">
			    <select id="category-select" onchange="filterByCategory()" style="width: 20%">
			        <option value="all">전체</option>
			        <option value="의류">의류</option>
			        <option value="전자제품">전자제품</option>
			        <option value="화장품">화장품</option>
			    </select>
			    <input type="search" id="search-input" placeholder="상품명, 카테고리로 검색" onkeyup="filterProducts(event)">
			    <button id="search-btn" onclick="filterProducts()" style="width:150px">검색</button>
			</div>

		 <!-- 상품 목록 -->
		<div id="product-list" class="product-grid">
		    <c:forEach var="product" items="${products}">
		        <div class="product-card">
		         	<a href="${pageContext.request.contextPath}/product/detail/${product.id}">
		            	
		            	 <c:choose>					       
					        <c:when test="${fn:startsWith(product.imgUrl, 'http')}">
					            <img src="${product.imgUrl}" alt="${product.name}" class="product-image" />
					        </c:when>
					        <c:otherwise>
					            <img src="${pageContext.request.contextPath}/resources/${product.imgUrl}" alt="${product.name}" class="product-image" />
					        </c:otherwise>
					    </c:choose>
		            </a>
		            
		            <h3>
		            <a href="${pageContext.request.contextPath}/product/detail/${product.id}">${product.name}</a>    
		            </h3>
		            <p class="product-category">${product.category}</p>
		            <p class="product-price">${product.price}원</p>
		            <p class="product-quantity">수량: ${product.quantity}개</p>
		            <c:if test="${not empty loginUser}">
		                <button class="btn btn-edit" 
		                    onclick="purchaseModal('${product.id}', '${product.name}', '${product.category}', '${product.quantity}')">
		                    구매
		                </button>
		            </c:if>
		        </div>
		    </c:forEach>
		</div>

				<!-- 페이징 버튼 -->
				<div class="pagination">
				    <c:forEach var="page" begin="1" end="${totalPages}">
				        <button class="page-btn" onclick="navigatePage(${page})">${page}</button>
				    </c:forEach>
				</div>

		<!-- 구매 모달 -->
		<div id="purchase-modal" style="display:none;">
		    <div class="modal-content">
		        <h3 id="modal-product-name">상품명</h3>		        
		        <p id="modal-product-category">카테고리</p>
		        <p id="modal-product-quantity">상품</p>
		        <input type="number" id="purchase-quantity" min="1" value="1"  placeholder="구매 수량">
		        <button onclick="completePurchase()">구매 완료</button>
		        <button onclick="closeModal()">닫기</button>
		    </div>
		</div>


    </div>
    
	<script>
	    const adminRole = ${adminRole ? true : false}; // adminRole을 JavaScript로 전달
	    const loginUser = ${not empty loginUser ? true : false}; // loginUser 존재 여부 확인
	</script>


    <script src="${pageContext.request.contextPath}/resources/js/product.js"></script>





</body>
</html>
