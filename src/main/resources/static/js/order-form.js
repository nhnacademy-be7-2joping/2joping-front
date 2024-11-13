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

const bookUnitPrices = document.querySelectorAll('.product-container .card .cost-value');
const bookQuantities = document.querySelectorAll('.product-container .card input[type=number]');

/* 주문 수량에 따른 가격 처리 */
bookQuantities.forEach((item) => {
    item.addEventListener('change', () => {
        // 도서의 총 가격 계산
        let bookCost = 0;
        bookQuantities.forEach((item, idx) => {
            const quantity = Number(item.value);
            const unitPrice = Number(bookUnitPrices[idx].textContent);
            bookCost += quantity * unitPrice;
        });

        // 배송 정책 기준 가격을 초과하면 배송비 조정 (e.g. 도서 30000 원 초과 시 배송비 무료)
        const deliveryCost = document.getElementById('delivery-cost');
        let applicableShippingFee = 0;
        for (let p of shipmentPolicies) {
            if (bookCost < p.minOrderAmount) {
                break;
            }

            applicableShippingFee = p.shippingFee;
        }

        deliveryCost.textContent = applicableShippingFee;
        document.getElementById('book-cost').textContent = bookCost;
        updateTotalCost();
    });
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

function updateTotalCost() {
    // 도서 금액, 배송비, 할인 기준으로 최종 가격 적용
    const bookCost = Number(document.getElementById("book-cost").textContent);
    const deliveryCost = Number(document.getElementById("delivery-cost").textContent);
    const discountCost = Number(document.getElementById("discount-cost").textContent);
    const cost = (bookCost + deliveryCost - discountCost);
    const totalCostElement = document.getElementById("total-cost");
    totalCostElement.textContent = cost;
}