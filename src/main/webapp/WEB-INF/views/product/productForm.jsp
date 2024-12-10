<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>   
    <%@ include file="../include/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <title>상품 등록</title>
</head>
<body>

	<%@ include file="../include/headMenu.jsp"%>
	

    <div class="container">
        <h2>상품 등록</h2>
        <form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">상품명:</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>
             <div class="form-group">
                <label for="category">카테고리:</label>
                <select id="category" name="category" class="form-control" required>
			        <option value="의류">의류</option>
			        <option value="전자제품">전자제품</option>
			        <option value="화장품">화장품</option>
                </select>
            </div>
            <div class="form-group">
                <label for="quantity">수량: 개</label>
                <input type="number" id="quantity" name="quantity" class="form-control"
                min="0"
                 required>
            </div>
                
            
            <div class="form-group">
                <label for="price">가격:원</label>
                <input type="number" id="price" name="price" class="form-control" 
                min="0"
                required>
            </div>
			     
           
           <div class="form-group">
                <label for="price">내용</label>
                <textarea id="itemDetail" name="itemDetail"  rows="10"  style="width: 100%"></textarea>
                
           </div>
			
           
	       <div class="form-group">
		        <label for="imgFile">이미지 파일:</label>		        
		        <input type="file" id="imgFile" name="imgFile" class="form-control"  accept="image/*" />
		    </div>
           
           
           
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">등록</button>
                <a href="${pageContext.request.contextPath}/product/list" class="btn btn-secondary">목록으로 돌아가기</a>
            </div>
        </form>
    </div>
    
    
</body>
</html>
