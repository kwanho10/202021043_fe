



// 검색 기능
async function searchProducts(query) {
    try {
        const home = document.getElementById("home").value;
        const category = document.getElementById("category-select").value;
        const response = await fetch(`${home}/product/search?query=${encodeURIComponent(query)}&category=${category}`);
        if (!response.ok) throw new Error("검색 요청 실패");

        const products = await response.json(); // JSON 데이터를 파싱
        const productList = document.getElementById("product-list"); // <div> 태그 ID
        productList.innerHTML = ""; // 기존 내용을 초기화

        products.forEach(product => {
            const card = document.createElement("div");
            card.className = "product-card";

            // 이미지 URL 확인
            const imageUrl = product.imgUrl.startsWith("http")
                ? product.imgUrl
                : `${home}/resources/${product.imgUrl}`;

            card.innerHTML = `
                <img src="${imageUrl}" alt="${product.name}" class="product-image" />
                <h3>${product.name}</h3>
                <p class="product-category">${product.category}</p>
                <p class="product-price">${product.price}원</p>
                <p class="product-quantity">수량: ${product.quantity}개</p>
                ${loginUser ? `
                    <button class="btn btn-edit" 
                            onclick="purchaseModal('${product.id}', '${product.name}', '${product.category}', '${product.quantity}')">
                        구매
                    </button>
                ` : ""}
            `;

            productList.appendChild(card);
        });
    } catch (error) {
        console.error("검색 중 오류:", error);
    }
}



// 검색 버튼 클릭 시 실행
document.getElementById("search-btn").addEventListener("click", () => {
    const query = document.getElementById("search-input").value;
    searchProducts(query);
});



function filterProducts(event) {
    // Enter 키를 눌렀을 때만 동작
    if (event.key === "Enter" || event.code === "Enter") {
        console.log("Enter key detected!");
        const query = document.getElementById("search-input").value;
        searchProducts(query);
    }
}









// 구매 모달 열기
function purchaseModal(id, name, category, quantity) {
    document.getElementById("modal-product-name").textContent = name;
    document.getElementById("modal-product-category").textContent = `카테고리: ${category}`;
    document.getElementById("modal-product-quantity").textContent = `상품: ${quantity}개`;
    document.getElementById("purchase-modal").style.display = "flex";

	document.getElementById("purchase-quantity").value="1";
	
    // 구매 완료 버튼에 현재 상품 ID 저장
    document.getElementById("purchase-modal").dataset.productId = id;
}

// 모달 닫기
function closeModal() {
    document.getElementById("purchase-modal").style.display = "none";
}

// 구매 완료 처리
function completePurchase() {
	const home= document.getElementById("home").value;
    const productId = document.getElementById("purchase-modal").dataset.productId;
    const purchaseQuantity = document.getElementById("purchase-quantity").value;

    if (!purchaseQuantity || purchaseQuantity <= 0) {
        alert("구매 수량을 입력하세요.");
        return;
    }

    // 구매 요청 API 호출
    fetch(`${home}/product/purchase`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ productId, quantity: purchaseQuantity })
    })
        .then(response => {
            if (response.ok) {
                alert("구매가 완료되었습니다.");
                closeModal();
                // 재검색으로 최신 상품 상태 업데이트
                const query = document.getElementById("search-input").value;
                searchProducts(query);
            } else {
                alert("구매에 실패했습니다. 상품 수량을 확인해 주세요.");
            }
        })
        .catch(error => console.error("Error completing purchase:", error));
}














