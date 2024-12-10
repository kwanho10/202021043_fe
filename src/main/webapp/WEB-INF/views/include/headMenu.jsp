<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<input type="hidden"  id="home" value="${pageContext.request.contextPath}" >
    
<!-- 상단 네비게이션 바 -->
<nav class="navbar">
    <div class="navbar-container">
        <div class="navbar-logo">
            <a href="${pageContext.request.contextPath}/product/list">미니몰</a>
            
            
          
        </div>
        <ul class="navbar-links">
        	 <li><a href="${pageContext.request.contextPath}/product/list">상품 목록</a></li>
             <c:choose>
           		<c:when test="${empty loginUser }">
		             <li><a href="${pageContext.request.contextPath}/user/create">회원가입</a></li>
        		     <li><a href="${pageContext.request.contextPath}/user/loginPage">로그인</a></li>                               		
           		</c:when>
           		<c:otherwise>           		  
           		     <c:if test="${adminRole}">
                        <li><a href="${pageContext.request.contextPath}/product/add">상품 추가</a></li>                         
                    </c:if>            		
                     <li><a href="${pageContext.request.contextPath}/orders/list">주문 목록</a></li> 
           			 <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>	
           		</c:otherwise>
           </c:choose>
             
        </ul>
        <div class="navbar-toggle" onclick="toggleMenu()">
            <span></span>
            <span></span>
            <span></span>
        </div>
    </div>
</nav>