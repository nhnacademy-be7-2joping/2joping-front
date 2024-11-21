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
const couponInfoContainer = document.getElementById("coupon-info");
couponSelect.addEventListener('change', async () => {
    const selectedOption = couponSelect.options[couponSelect.selectedIndex];
    if (selectedOption.value === 'none') {
        couponInfoContainer.style.display = 'none';
        couponDiscount.textContent = '0';
    } else {
        couponInfoContainer.style.display = 'block';
        const selectedCouponId = Number(selectedOption.value);
        const bookCost = Number(document.getElementById('book-cost').textContent);

        const res = await fetch(`/api/coupons/${selectedCouponId}`)
        if (!res.ok) {
            alert(res.message);
            return;
        }

        let data = await res.json();
        console.log(data);
        showCouponInfo(data);
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
    totalCostElement.textContent = cost.toLocaleString('ko-KR');
}

const PERCENT_TYPE = 'PERCENT';
const ACTUAL_TYPE = 'ACTUAL';
const showCouponInfo = (data) => {
    const {
        couponUsageId, detail, discountType, discountValue, invalidTime, maxDiscount, name, usageLimit
    } = data;
    const couponNameText = document.getElementById('coupon-name');
    const discountValueText = document.getElementById('discount-value');
    const discountTypeText = document.getElementById('discount-type');
    const usageLimitText = document.getElementById('usage-limit');
    const maxDiscountText = document.getElementById('max-discount');

    couponNameText.textContent = name;
    discountValueText.textContent = discountValue;
    discountTypeText.textContent = (discountType === PERCENT_TYPE) ? '%' : '원';
    usageLimitText.textContent = usageLimit;
    maxDiscountText.textContent = maxDiscount;

    setDiscountCost(discountType, discountValue, maxDiscount, usageLimit);
}

const setDiscountCost = (discountType, discountValue, maxDiscount, usageLimit) => {
    const discountedCostText = document.getElementById("discount-cost");
    const totalCost = Number(document.getElementById("total-cost").textContent.replace(',', ''));
    let discountedCost = discountType === PERCENT_TYPE ? discountValue * totalCost / 100 : discountValue;

    if (usageLimit && totalCost < usageLimit) {
        return;
    }

    if (maxDiscount && maxDiscount < discountedCost) {
        discountedCost = maxDiscount;
    }

    discountedCostText.textContent = discountedCost;
};