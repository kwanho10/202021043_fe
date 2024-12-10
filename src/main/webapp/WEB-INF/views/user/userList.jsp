<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="kr.ac.kku.cs.wp.kwanho10.user.entity.User"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../include/head.jsp"%>
<title>유저목록</title>
</head>
<body>	
	<div id="container">	
	<%@ include file="../include/headMenu.jsp"%>
	
		<h2>사용자 목록</h2>

		<!-- 서버 에러 메시지 표시 -->
		<div id="server-error" class="error"></div>
		<!-- 서버 성공 메시지 표시 -->
		<div id="server-success" class="success"></div>

		<!-- 필터 입력 필드 -->
		<div class="filter-container">
			<input type="text" id="user-filter"
				placeholder="이름, 이메일, 역할 또는 아이디로 검색">
			<button onclick="filterUsers()">검색</button>
		</div>

		<div id="refresh">
			<div id="user-count" style="margin-bottom: 20px;">
				전체 :<strong>${fn:length(requestScope.users)}</strong>
			</div>

			<div class="user-card-container" id="user-list">


				<c:forEach var="user" items="${users}">
					<!-- 사용자 카드 1 (여러 역할 및 아이디 추가) -->
					<div class="user-card" data-name="홍길동"
						data-email="hong@example.com" data-role="관리자, 개발자" data-id="1001">
						
					
						<c:if test="${ user.address != null}">
							<p>
							<strong>주소:</strong>${user.address }</p>														
							<p>
						</c:if>
						
						<div class="user-info">
							<h3>${user.name}</h3>
							<p>
							<strong>이메일:</strong>${user.email }</p>
							
							<p>
								<strong>역할:</strong>
								<c:forEach var="userRole" items="${user.userRoles}">
                       		 ${userRole.role.role}&nbsp;                       		
                    	</c:forEach>
							 </p>
							 
							<p>
								<strong>사번:</strong> ${user.id }
							</p>
							<p>
								<strong>성별:</strong> ${user.status}
							</p>
							<button onclick="showUserDetail('${user.id }')">상세 보기</button>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>




<script  src="${pageContext.request.contextPath}/resources/js/common.js" ></script>
<script  src="${pageContext.request.contextPath}/resources/js/userList.js" ></script>
	
	
</body>
</html>