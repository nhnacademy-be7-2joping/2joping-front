document.addEventListener("DOMContentLoaded", () => {
    const decreaseBtn = document.getElementById("decrease-btn");
    const increaseBtn = document.getElementById("increase-btn");
    const quantityInput = document.getElementById("quantity-input");
    const totalPriceElement = document.getElementById("total-price");

    // 판매가 가져오기 (초기값)
    const sellingPrice = parseInt(totalPriceElement.textContent.replace(/[^\d]/g, ''), 10) || 0;

    // 수량 업데이트 함수
    function updateTotalPrice() {
        const quantity = parseInt(quantityInput.value, 10) || 1;
        const totalPrice = sellingPrice * quantity;
        totalPriceElement.textContent = `${totalPrice.toLocaleString()}원`;
    }

    // 수량 감소
    decreaseBtn.addEventListener("click", () => {
        const currentValue = parseInt(quantityInput.value, 10) || 1;
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
            updateTotalPrice();
        }
    });

    // 수량 증가
    increaseBtn.addEventListener("click", () => {
        const currentValue = parseInt(quantityInput.value, 10) || 1;
        quantityInput.value = currentValue + 1;
        updateTotalPrice();
    });

    // 수량 직접 입력
    quantityInput.addEventListener("input", () => {
        let currentValue = parseInt(quantityInput.value, 10);
        if (isNaN(currentValue) || currentValue < 1) {
            quantityInput.value = 1;
        }
        updateTotalPrice();
    });
});
document.addEventListener("DOMContentLoaded", () => {
    const reviewContainer = document.querySelector(".review-container");

    reviewContainer.addEventListener("wheel", (event) => {
        event.preventDefault(); // 기본 스크롤 동작 방지
        const scrollDelta = event.deltaY;
        reviewContainer.scrollLeft += scrollDelta;
    });
});