/* 현재 날짜에서 7일 이후까지만 선택 가능 */
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

const today = new Date();
const sixDaysLater = new Date();
sixDaysLater.setDate(today.getDate() + 6);
const minDate = formatDate(today);
const maxDate = formatDate(sixDaysLater);

const desiredDate = document.getElementById('desired-date');
desiredDate.min = minDate;
desiredDate.max = maxDate;

/* event listener 처리 */
// 버튼 클릭 시 결제 처리
const modal = new bootstrap.Modal(document.getElementById('payment-modal'));
const btnPay = document.getElementById('btn-pay');
btnPay.addEventListener('click', () => {
    // 결제 전 주문 입력 검증
    if (!validateForm()) {
        return;
    }

    modal.show();

    // 주문 작성을 위한 form 구성
    const booksInCart = document.querySelectorAll('.product-container .card');
    let orderDto = {};

    console.log(booksInCart);
});

// 쿠폰 선택 시 처리
const couponSelect = document.getElementById('coupon');
const couponDiscount = document.getElementById('discount-cost');
couponSelect.addEventListener('change', (e) => {
    const selectedOption = couponSelect.options[couponSelect.selectedIndex];
    if (selectedOption.value === 'none') {
        couponDiscount.textContent = '0';
    } else {
        const selectedCost = Number(selectedOption.getAttribute('cost'));
        const bookCost = Number(document.getElementById('book-cost').textContent);

        switch (selectedOption.getAttribute('discount-type')) {
            case 'PERCENT':
                couponDiscount.textContent = String(bookCost * selectedCost / 100);
                break;
            case 'ACTUAL':
                couponDiscount.textContent = String(selectedCost);
                break;
        }
    }
    updateTotalPrice();
});

/* 함수 */
function validateForm() {
    if (
        document.getElementById('receiver').value === "" ||
        document.getElementById('name').value === "" ||
        document.getElementById('desired-date').value === "" ||
        document.getElementById('phone').value === "" ||
        document.getElementById('email').value === "" ||
        document.getElementById('postal_code').value === "" ||
        document.getElementById('address').value === ""
    ) {
        alert("배송 정보를 입력해주세요");
        return false;
    }

    return true;
}

function updateTotalPrice() {
    // 도서 금액, 배송비, 할인 기준으로 최종 가격 적용
    const bookCost = Number(document.getElementById("book-cost").textContent);
    const deliveryCost = Number(document.getElementById("delivery-cost").textContent);
    const discountCost = Number(document.getElementById("discount-cost").textContent);
    const cost = (bookCost + deliveryCost - discountCost);
    const totalCostElement = document.getElementById("total-cost");
    totalCostElement.textContent = cost;
}