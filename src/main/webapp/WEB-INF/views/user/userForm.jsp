<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/userForm.css">
<title>회원 가입</title>
</head>
<body>	

<!-- 로딩 오버레이 -->
<div id="loading-overlay" class="loading-overlay">
	<div class="spinner"></div>
</div>

<%@ include file="../include/headMenu.jsp"%>	
<main class="container main-content"  >	
	<h2>회원 가입</h2>

	<!-- 서버 에러 메시지 표시 -->
	<div id="server-error" class="error"></div>
	<!-- 서버 성공 메시지 표시 -->
	<div id="server-success" class="success"></div>


	<form id="user-form" enctype="multipart/form-data" method="post"
		action="${pageContext.request.contextPath}/user/create">
		<!-- ID 필드 (자동 생성) -->
		<div class="form-group">
			<label for="id">ID:</label> 
			<input type="text" id="id" name="id" readonly="readonly" 
			placeholder="자동 생성됨" style="background: #ddd"
			/>
		</div>

		<!-- 이름 필드 -->
		<div class="form-group">
			<label for="name">이름:</label> <input type="text" id="name"
				name="name" required />
			<div id="name-error" class="error"></div>
		</div>

		<!-- 이메일 필드 -->
		<div class="form-group">
			<label for="email">이메일:</label> <input type="email" id="email"
				name="email" required />
			<div id="email-error" class="error"></div>
		</div>

		<!-- 비밀번호 필드 -->
		<div class="form-group password-field">
			<label for="password">비밀번호:</label> 
			<input type="password" id="password" name="password" required /> <span
				id="toggle-password" class="toggle-password"
				onclick="togglePasswordVisibility()">👁</span>
			<div id="password-error" class="error"></div>
		</div>

		<!-- 상태 필드 (라디오 버튼) -->
		<div class="form-group">
			<label for="status">성별:</label> <select id="status" name="status"
				required>
				<option value="">선택해주세요</option>
				<option value="남성">남성</option>
				<option value="여성">여성</option>
			</select>
			<div id="status-error" class="error"></div>
		</div>

		<!-- 주소 필드 -->
		<div class="form-group">
			<label for="address">주소:</label>
			<div class="custom-file-input">
				<input type="text" id="address" name="address" />
			</div>
	
		</div>

			 <!-- 역할 선택 -->
		    <div class="form-group">
		        <label>역할 선택 (최대 2개):</label>
		        <div class="roles-group">
		            <label><input type="checkbox" name="roles" value="1000" > 관리자</label>
		            <label><input type="checkbox" name="roles" value="1001" > 판매자</label>
		            <label><input type="checkbox" name="roles" value="1002" > 구매자</label>
		        </div>
		    </div>


		<button type="submit" >회원 가입</button>
	</form>

</main>



<script  src="${pageContext.request.contextPath}/resources/js/common.js" ></script>

<script>		
 		document.getElementById('user-form').addEventListener('submit', async (event) => {
		    event.preventDefault();
		    // 모든 에러 필드를 초기화
		    const errorFields = document.querySelectorAll('.error');
		    errorFields.forEach(errorField => {
		        errorField.innerText = '';
		    });
	      
	        document.getElementById('password-error').innerText = '';
	        document.getElementById('server-error').innerText = '';
		    
		    const form = document.getElementById('user-form');
		    const formData = new FormData(form);
		
		    // 역할 데이터를 userRoles로 변환
		    const roles = formData.getAll('roles');
		    const userRoles = roles.map((roleId, index) => ({ roleId }));
		
		    // JSON 데이터 구성
		    const jsonData = {
		        name: formData.get('name'),
		        email: formData.get('email'),
		        password: formData.get('password'),
		        status: formData.get('status'),
		        address: formData.get('address'),
		        userRoles,
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
				console.log("responseData  :",responseData);
				
		        if (!response.ok) {
		        	const resErrorData=responseData[0];
		        	document.getElementById(resErrorData.field+'-error').innerText=resErrorData.defaultMessage;	
		        }
		        	
		        if(responseData.result==="success"){
		        	alert("회원가입을 축하 합니다.");
		        	location.href="loginPage";
		        }
		        
		    } catch (error) {
		        console.error("요청 처리 중 오류 발생:", error);		        
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
