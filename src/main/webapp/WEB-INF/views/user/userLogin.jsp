<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/userForm.css">
<title>로그인</title>
</head>
<body>

	<!-- 로딩 오버레이 -->
	<div id="loading-overlay" class="loading-overlay">
		<div class="spinner"></div>
	</div>

	<%@ include file="../include/headMenu.jsp"%>
	<main class="container main-content">
		<h2>로그인</h2>

		<!-- 서버 에러 메시지 표시 -->
		<div id="server-error" class="error"></div>
		<!-- 서버 성공 메시지 표시 -->
		<div id="server-success" class="success"></div>



		<form id="user-form" method="post" action="${pageContext.request.contextPath}/user/login">
			
			<!-- 이메일 필드 -->
			<div class="form-group">
				<label for="email">이메일:</label> 
				<input type="email" id="email" name="email" required />
				<div id="email-error" class="error"></div>
			</div>

			<!-- 비밀번호 필드 -->
			<div class="form-group password-field">
				<label for="password">비밀번호:</label> <input type="password"
					id="password" name="password" required /> <span
					id="toggle-password" class="toggle-password"
					onclick="togglePasswordVisibility()">👁</span>
				<div id="password-error" class="error"></div>
			</div>
		
			<button type="submit">로그인</button>
		</form>

	</main>



	<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>

	<script>		

	document.getElementById('user-form').addEventListener('submit', async (event) => {
	    event.preventDefault();
	    // 모든 에러 필드를 초기화
	    const errorFields = document.querySelectorAll('.error');
	    errorFields.forEach(errorField => {
	        errorField.innerText = '';
	    });
	    
	    const form = document.getElementById('user-form');
	    const formData = new FormData(form);

	    const jsonData = {
	        email: formData.get('email'),
	        password: formData.get('password'),
	    };

	    showLoading();
	    disableForm();

	    try {
	        const response = await fetch(form.action, {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(jsonData),
	        });

	        const responseData = await response.json();

	        // 모든 에러 메시지 초기화
	        document.getElementById('email-error').innerText = '';
	        document.getElementById('password-error').innerText = '';
	        document.getElementById('server-error').innerText = '';

	        if (!response.ok) {
	            if (Array.isArray(responseData)) {
	                // 유효성 검사 오류 표시
	                responseData.forEach((error) => {
	                    const errorField = document.getElementById(error.field+"-error");
	                    if (errorField) {
	                        errorField.innerText = error.defaultMessage;
	                    }
	                });
	            } else {
	                // 서버 반환 에러 표시
	                document.getElementById('server-error').innerText = responseData.message || '알 수 없는 오류가 발생했습니다.';
	            }
	            return;
	        }

	        if (responseData.result === 'success') {
	            location.href = "${pageContext.request.contextPath}/product/list";
	        } else {
	            document.getElementById('server-error').innerText = responseData.message || '로그인에 실패했습니다.';
	        }
	    } catch (error) {
	        console.error("요청 처리 중 오류 발생:", error);
	        document.getElementById('server-error').innerText = "서버와의 통신 중 오류가 발생했습니다.";
	    } finally {
	        hideLoading();
	        enableForm();
	    }
	});


    // 로딩 오버레이 표시 함수
    function showLoading() {
        const overlay = document.getElementById('loading-overlay');
        if (overlay){
        	overlay.style.display = 'flex';  
        }
        console.log(" 로딩 오버레이 표시 함수");
    }
    

    // 로딩 오버레이 숨김 함수
    function hideLoading() {
        const overlay = document.getElementById('loading-overlay');
        if (overlay){
        	overlay.style.display = 'none';	
        }                
        console.log(" 로로딩 오버레이 숨김 함수");
    }

    // 폼 비활성화 함수
    function disableForm() {
        const form = document.getElementById('user-form');
        if (form) {
        	const elements = form.elements;
            for (let i = 0; i < elements.length; i++) {
                elements[i].disabled = true;
            }
        }
        
    }

    // 폼 활성화 함수
    function enableForm() {
    	const form = document.getElementById('user-form');
        if (form) {
        	const elements = form.elements;
	        for (let i = 0; i < elements.length; i++) {
	            elements[i].disabled = false;
	        }
        }
    }

    // 비밀번호 표시 토글 기능
    function togglePasswordVisibility() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.getElementById('toggle-password');
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.innerText = '🙈'; // 눈 가린 원숭이 아이콘
        } else {
            passwordInput.type = 'password';
            toggleIcon.innerText = '👁'; // 눈 아이콘
        }
    }

    // 이미지 미리보기 함수
    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function(){
            const output = document.getElementById('image-preview');
            output.style.display = 'block';	
            output.src = reader.result;
        };
        if (event.target.files[0]) {
            reader.readAsDataURL(event.target.files[0]);
        }
    }
    
</script>


</body>
</html>
