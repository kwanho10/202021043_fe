// 사용자 필터링 함수
function filterUsers() {
    const filterValue = document.getElementById('user-filter').value.toLowerCase();
    // filter({'id':filterValue, 'name':filterValue, 'email':filterValue, 'status':filterValue, 'userRoles':[{'roleName':filterValue}]});
    filter(filterValue);
}

// 페이지를 로드하고 카드를 동적으로 생성하는 함수
async function filter(queryString) {
    const mainContent = document.getElementById('refresh');
    const formData = new URLSearchParams();
    formData.append("queryString", queryString);
    
    // POST 요청을 보내면서 파라미터를 전달
    const response = await fetch('user/search', {
        method: 'POST',
        body : formData
        /* headers: {
            'Content-Type': 'application/json',  // JSON 형식으로 전달
        },
        body: JSON.stringify(params),  // 파라미터를 JSON으로 변환하여 body에 추가 */
    });
    
    const isOk = response.ok;
    const data = await response.text();

    if (!isOk) {
        openModalFetch(data);  // 오류 발생 시 모달 창 호출
    } else {
    	mainContent.innerHTML = data;
    }
}

// 폼 데이터 전송 함수
async function showUserDetail(userId) {        
	const formData = new URLSearchParams();
    formData.append('userId', userId);

    // 서버 응답에 따른 메시지 표시를 위한 요소
    const serverError = document.getElementById('server-error');
    const serverSuccess = document.getElementById('server-success');
    serverError.textContent = ''; // 이전 에러 메시지 초기화
    serverSuccess.textContent = ''; // 이전 성공 메시지 초기화

    try {
        const response = await fetch('user/view', {
            method: 'POST',
            body: formData
        });

        const contentType = response.headers.get('content-type');
        
        if (response.ok) {
        	if (contentType && contentType.includes('text/html')) {
        		const data = await response.text();
				const pageId = 'user';
            	//loadPage 참조
            	const mainContent = document.querySelector('main');
            	const existingPage = document.getElementById(pageId);
            	if (existingPage)
            		existingPage.remove(); //before appending new Page

            	const newPageCard = document.createElement('div');
	            newPageCard.id = pageId;
	            newPageCard.classList.add('page-card');
	            newPageCard.innerHTML = data;
	            mainContent.appendChild(newPageCard);
	            setActivePage(pageId);
	            adjustPaddingForHome(pageId);
	            
				
				
	            // 페이지 내 script 태그 재실행 처리
	            const scripts = newPageCard.getElementsByTagName('script');
	            Array.from(scripts).forEach((script, i) => {
	                /* const scriptId = `${pageId}_script_${i}`; */
	                const scriptId = pageId + '_script_' + i;
	                console.log(pageId + '_script_' + i);
	                const existingScript = document.getElementById(scriptId);
	                if (existingScript) existingScript.remove();
	                
	                const newScript = document.createElement('script');
	                newScript.id = scriptId;
	                newScript.text = script.text;
	                document.body.appendChild(newScript);
	            });
        	} else {
        		const errorData = await response.json();
                if (errorData && errorData.message) {
                    serverError.textContent = errorData.message;
                } else {
                    serverError.textContent = '회원 가입 중 오류가 발생했습니다.';
                }
            }
        } else {
            const errorData = await response.json();
            if (errorData && errorData.message) {
                serverError.textContent = errorData.message;
            } else {
                serverError.textContent = '회원 가입 중 오류가 발생했습니다.';
            }
        }
    } catch (error) {
        console.error('에러 발생:', error);
        serverError.textContent = '서버와 통신 중 에러가 발생했습니다.';                     
    } 
}