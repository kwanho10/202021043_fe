// setActivePage 함수 정의
function setActivePage(pageName) {
    // 모든 페이지 링크에서 'active' 클래스 제거
    const links = document.querySelectorAll('.page-link');
    links.forEach(link => link.classList.remove('active'));

    // 전달된 페이지 이름에 해당하는 링크에 'active' 클래스 추가
    const activeLink = document.querySelector(`.page-link[data-page="${pageName}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    } 

    // 홈 페이지 활성화 여부에 따라 패딩 조정
    adjustPaddingForHome(pageName === 'home');

    // 로그 출력 (디버깅용)
    console.log(`Active page set to: ${pageName}`);
}



function adjustPaddingForHome(isHomeActive) {
    const mainContent = document.querySelector('.main-content');

    if (mainContent) {
        if (isHomeActive) {
            mainContent.style.paddingTop = '50px';
            console.log('Padding adjusted for Home page.');
        } else {
            mainContent.style.paddingTop = '20px';
            console.log('Default padding restored.');
        }
    } else {
        console.warn('.main-content element not found.');
    }
}



