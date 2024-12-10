<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <title>주문 목록</title>
    <style>
        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        /* 페이징 버튼 스타일 */
        .pagination {
            display: inline-block;
            margin-top: 20px;
        }

        .pagination a {
            padding: 8px 16px;
            margin: 0 4px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #007bff;
        }

        .pagination a.disabled {
            color: #ccc;
        }

        .pagination a:hover {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <%@ include file="../include/headMenu.jsp" %>

    <div class="container">
        <h1>주문 목록</h1>
        <table>
            <thead>
                <tr>
                    <th>주문 ID</th>
                    <th>구매자</th>
                    <th>주문 날짜</th>
                    <th>주문 상품 수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders.content}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.user.name}</td>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>${order.orderItems.size()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 페이징 -->
        <div class="pagination">
            <c:if test="${orders.totalPages > 1}">
                <a href="?page=0&size=${orders.size}" class="${orders.hasPrevious() ? '' : 'disabled'}">처음</a>
                <a href="?page=${orders.number - 1}&size=${orders.size}" class="${orders.hasPrevious() ? '' : 'disabled'}">이전</a>
                <a href="?page=${orders.number + 1}&size=${orders.size}" class="${orders.hasNext() ? '' : 'disabled'}">다음</a>
                <a href="?page=${orders.totalPages - 1}&size=${orders.size}" class="${orders.hasNext() ? '' : 'disabled'}">마지막</a>
            </c:if>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/product.js"></script>
</body>
</html>
