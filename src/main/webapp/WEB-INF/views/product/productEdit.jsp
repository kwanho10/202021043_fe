<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>   
    <%@ include file="../include/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <title>상품 수정</title>
</head>
<body>

	<%@ include file="../include/headMenu.jsp"%>
	
	
    <div class="container">
        <h2>상품 수정</h2>
        <form action="${pageContext.request.contextPath}/product/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}">
            <div class="form-group">
                <label for="name">상품명:</label>
                <input type="text" id="name" name="name" class="form-control" value="${product.name}" required>
            </div>
            <div class="form-group">
                <label for="category">카테고리:</label>               
				<select id="category" name="category" class="form-control" required>
                	<option value="의류" ${product.category eq '의류' ? 'selected':''}>의류</option>
                	<option value="전자제품" ${product.category eq '전자제품' ? 'selected':''}>전자제품</option>
                	<option value="화장품" ${product.category eq '화장품' ? 'selected':''}>화장품</option>
                </select>                
            </div>
            
            <div class="form-group">
                <label for="quantity">수량:개</label>
                <input type="number" id="quantity" name="quantity" class="form-control" value="${product.quantity}" required>
            </div>
                        
            <div class="form-group">
                <label for="price">가격:원</label>
                <input type="number" id="price" name="price" class="form-control" value="${product.price}" required>
            </div>
			
           <div class="form-group">
                <label for="price">내용</label>
                <textarea id="itemDetail" name="itemDetail"  rows="10"  style="width: 100%">${product.itemDetail}</textarea>               
           </div>
           
            <div class="form-group">
		        <label for="imgFile">이미지 파일:</label>		        
		        <input type="file" id="imgFile" name="imgFile" class="form-control"  accept="image/*" />
		    </div>
           
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
            
            
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">수정</button>
                 <a href="${pageContext.request.contextPath}/product/detail/${product.id}" class="btn btn-secondary">이전화면</a>
                <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary">목록으로 돌아가기</a>
            </div>
        </form>
    </div>
</body>
</html>
