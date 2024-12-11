/* 주문 상품이 없는 경우 메인 페이지로 리다이렉트 처리 */
if (document.querySelectorAll('.product-container .card').length === 0) {
    alert('주문 상품이 없습니다.');
    window.location.href = '/';
}

/* 현재 날짜에서 7일 이후까지만 선택 가능 */
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

const today = new Date();
const defaultDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 2); // 기본 배송일은 이틀 뒤
const sixDaysLater = new Date();
sixDaysLater.setDate(today.getDate() + 6);
const minDate = formatDate(today);
const maxDate = formatDate(sixDaysLater);

const desiredDate = document.getElementById('desired-date');
desiredDate.value = formatDate(defaultDate);
desiredDate.min = minDate;
desiredDate.max = maxDate;

/* event listener 처리 */
// 초기 오브젝트
const discountCostText = document.getElementById('discount-cost');
const bookCostText = document.getElementById('book-cost');
const deliveryCostText = document.getElementById('delivery-cost');
const wrapCostText = document.getElementById('wrap-cost');
const totalCostText = document.getElementById('total-cost');

const couponSelect = document.getElementById('coupon');
const couponInfoContainer = document.getElementById("coupon-info");

const discountMap = new Map([["coupon", 0], ["point", 0]]);

// 초기 결제 금액 가져오기
updateTotalPrice();

// 버튼 클릭 시 결제 처리
const modal = new bootstrap.Modal(document.getElementById('payment-modal'));
const btnPay = document.getElementById('btn-pay');

function collectOrderData() {
    let orderObject = {};

    // local function
    const getValueFromId = (id) => {
        const element = document.getElementById(id);
        if (!element) {
            return null;
        }

        const val = element.value.trim();
        const cleanedValue = val.replace(/,/g, '');
        const isNum = !isNaN(cleanedValue) && cleanedValue !== '';

        return isNum ? Number(cleanedValue) : val;
    }

    const getTextFromId = (id) => {
        const element = document.getElementById(id);
        if (!element) {
            return null;
        }

        return element.textContent;
    };

    // 장바구니 아이템 추가
    orderObject.cartItemList = [];
    document.querySelectorAll('.product-container .card').forEach(book => {
        const bookId = book.getAttribute('book-id');
        const unitPrice = localeNumberStringToNumber(book.querySelector('.unit-price').textContent);
        const quantity = book.querySelector('input[type=number]').value;
        const cartItem = {
            "bookId": parseInt(bookId),
            "quantity": parseInt(quantity),
            "unitPrice": unitPrice
        };
        orderObject.cartItemList.push(cartItem);
    });

    // 배송 정보 가져오기
    const deliveryInfo = {
        "receiver": getValueFromId('receiver'),
        "desiredDate": getValueFromId('desired-date'),
        "postalCode": String(getValueFromId('postal-code')), // 우편번호는 string으로 받음
        "address": getValueFromId('address'),
        "detail-address": getValueFromId('detail-address'),
        "name": getValueFromId('name'),
        "phone": document.getElementById('phone').value,
        "email": getValueFromId('email'),
        "requirement": getValueFromId('requirement'),
        "deliveryPolicyId": getValueFromId('delivery-policy-id')
    };
    orderObject.deliveryInfo = deliveryInfo;

    // 포인트 정보
    const point = getValueFromId('point');
    orderObject.point = getValueFromId('point') ? point : 0;

    // 쿠폰 정보
    const couponId = couponSelect?.options[couponSelect.selectedIndex].value;
    orderObject.couponId = (!couponId || couponId === 'none') ? -1 : parseInt(couponId);

    // 포장 정보
    const wrapList = [];
    const selectedBookElement = document.querySelectorAll('.selected-pack-container .selected-book-element');
    selectedBookElement.forEach(el => {
        const bookId = parseInt(el.getAttribute('book-id'));
        const wrapSelect = el.querySelector('select');
        const wrapId = parseInt(wrapSelect.options[wrapSelect.selectedIndex].value);
        wrapList.push({
            "bookId": bookId,
            "wrapId": wrapId
        });
    });
    orderObject.wrapList = wrapList;

    // 비용 정보
    orderObject.bookCost = localeNumberStringToNumber(getTextFromId('book-cost'));
    orderObject.deliveryCost = localeNumberStringToNumber(getTextFromId('delivery-cost'));
    orderObject.wrapCost = localeNumberStringToNumber(getTextFromId('wrap-cost'));
    orderObject.totalCost = parseInt(totalCostText.textContent.replace(/,/g, ''));

    // 할인 금액 정보
    orderObject.couponDiscount = discountMap.get('coupon');
    orderObject.nonMemberPassword = document.getElementById("nonmember-password")?.value ?? "";

    console.log(orderObject);

    return orderObject;
}

btnPay.addEventListener('click', () => {
    // 결제 전 주문 입력 검증
    if (!validateForm()) {
        return;
    }

    modal.show();

    // 주문 작성을 위한 form 구성
    const orderDto = collectOrderData();

    // 주문 id 랜덤 생성
    const orderCode = generateRandomString();
    orderDto.orderCode = orderCode;

    fetch('/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(orderDto)
    })
        .then(res => {
            return res.json().then(data => {
                console.log(res.body);
                if (!res.ok) {
                    throw {status: res.status, statusText: res.statusText, data};
                }
                return data;
            });
        })
        .then(data => {
            const totalCost = data.totalCost;
            startTossPayment(orderCode, totalCost);
        })
        .catch(error => {
            if (error.data) {
                const data = error.data;
                alert('주문 처리에 문제가 생겼습니다. : ' + data.errorMessage);
                console.error('Error:', error); // 에러 처리
                location.href = '/orders/form';
            } else {
                console.error('Network or unexpected error:', error);
                alert('주문 처리에 문제가 생겼습니다.');
            }
        });
});

// 쿠폰 선택 시 처리
couponSelect?.addEventListener('change', async () => {
    const selectedOption = couponSelect.options[couponSelect.selectedIndex];
    if (selectedOption.value === 'none') {
        couponInfoContainer.style.display = 'none';
        discountMap.set('coupon', 0);
        updateDiscountCost();
    } else {
        couponInfoContainer.style.display = 'block';
        const selectedCouponId = parseInt(selectedOption.value);

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

// 포장 선택시 처리
const selectWraps = document.querySelectorAll('.selected-book-element select');
selectWraps.forEach((el) => {
    el.addEventListener('change', sumWrapCost);
})

const pointApplyBtn = document.querySelector('.point-container button');
pointApplyBtn?.addEventListener('click', () => {
    let currentPoint = parseInt(document.getElementById('current-point').textContent);
    let pointInput = document.querySelector('.point-container input[type=number]');
    let inputPointValue = parseInt(pointInput.value);
    discountMap.set('point', inputPointValue); // 포인트 값 초기화

    if (inputPointValue > currentPoint) {
        alert("보유한 포인트를 초과했습니다. 다시 입력해주세요.");
        pointInput.value = 0;
    } else if (inputPointValue < 0) {
        alert('포인트 값이 음수일 수는 없습니다.');
        pointInput.value = 0;
    } else {
        const totalDiscount = getTotalDiscount();
        const bookCost = parseInt(bookCostText.textContent);
        const deliveryCost = parseInt(deliveryCostText.textContent);
        const wrapCost = parseInt(wrapCostText.textContent);
        const costSum = bookCost + deliveryCost + wrapCost;

        discountMap.set('point', totalDiscount > costSum ? costSum - discountMap.get('coupon') : inputPointValue);
        updateDiscountCost();
        updateTotalPrice();
    }
});

// 요청 사항 글자수 처리
const requirementTextArea = document.getElementById('requirement');
requirementTextArea.addEventListener('input', (e) => {
    const charCount = document.getElementById('char-count');
    charCount.textContent = e.target.value.length;
});

// 전화번호 넣을 때 자동으로 - 추가
const phoneInput = document.getElementById('phone');
phoneInput?.addEventListener('input', (e) => {
    const SEOUL_PHONE_REGEX = /(\d{2})(\d{1,3})/;
    const DEFAULT_PHONE_PARTIAL_REGEX = /(\d{3})(\d{1,4})/
    const DEFAULT_PHONE_REGEX = /(\d{2})(\d{3,4})(\d{1,4})/
    let value = e.target.value.replace(/[^0-9]/g, '');

    if (value.startsWith('02')) {
        // 서울 지역번호 (02) 형식
        if (value.length > 2 && value.length <= 5) {
            value = value.replace(SEOUL_PHONE_REGEX, '$1-$2');
        } else if (value.length > 5) {
            value = value.replace(DEFAULT_PHONE_REGEX, '$1-$2-$3');
        }
    } else {
        // 일반 휴대전화/지역번호 형식
        if (value.length > 3 && value.length <= 7) {
            value = value.replace(DEFAULT_PHONE_PARTIAL_REGEX, '$1-$2');
        } else if (value.length > 7) {
            value = value.replace(DEFAULT_PHONE_REGEX, '$1-$2-$3');
        }
    }

    e.target.value = value;
});

/* 함수 */
function validateForm() {
    if (
        document.getElementById('receiver').value === "" ||
        document.getElementById('name').value === "" ||
        document.getElementById('desired-date').value === "" ||
        document.getElementById('phone').value === "" ||
        document.getElementById('email').value === "" ||
        document.getElementById('postal-code').value === "" ||
        document.getElementById('address').value === ""
    ) {
        alert("배송 정보를 입력해주세요");
        return false;
    }

    const nonMemberPasswordText = document.querySelector('input[name=nonmember-password]');
    if (nonMemberPasswordText && nonMemberPasswordText.value === "") {
        alert('비회원 비밀번호를 입력해주세요.')
        return false;
    }

    return true;
}

function updateDiscountCost() {
    discountCostText.textContent = getTotalDiscount();
}

function getTotalDiscount() {
    let total = 0;
    discountMap.forEach((v, k) => total += v);
    return total;
}

function updateTotalPrice() {
    // 도서 금액, 배송비, 할인 기준으로 최종 가격 적용
    const bookCost = parseInt(document.getElementById("book-cost").textContent);
    const deliveryCost = parseInt(document.getElementById("delivery-cost").textContent);
    const discountCost = parseInt(discountCostText.textContent);
    const wrapCost = parseInt(document.getElementById("wrap-cost").textContent);
    const cost = (bookCost + wrapCost + deliveryCost - discountCost);
    const totalCostElement = document.getElementById("total-cost");
    totalCostElement.textContent = cost.toLocaleString('ko-KR');
}

const PERCENT_TYPE = 'PERCENT';
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

    setCouponDiscountCost(discountType, discountValue, maxDiscount, usageLimit);
}

const setCouponDiscountCost = (discountType, discountValue, maxDiscount, usageLimit) => {
    const bookCost = localeNumberStringToNumber(document.getElementById('book-cost').textContent);
    let discountedCost = discountType === PERCENT_TYPE ? discountValue * bookCost / 100 : discountValue;

    if (usageLimit && bookCost < usageLimit) {
        return;
    }

    if (maxDiscount && maxDiscount < discountedCost) {
        discountedCost = maxDiscount;
    }

    discountMap.set('coupon', discountedCost);
    updateDiscountCost();
};

function sumWrapCost() {
    const wrapCosts = document.querySelectorAll('.selected-pack-container select');
    const wrapCostText = document.getElementById('wrap-cost');
    let totalWrapCost = 0;
    wrapCosts.forEach((el) => {
        const selectedValue = el.options[el.selectedIndex].value;
        if (selectedValue !== 'none') {
            totalWrapCost += parseInt(wraps.find(w => w.wrapId === parseInt(selectedValue)).wrapPrice);
        }
    });
    wrapCostText.textContent = totalWrapCost;
    updateTotalPrice();
}

function generateRandomString() {
    return window.btoa(Math.random()).slice(0, 25);
}

function localeNumberStringToNumber(localeNumber) {
    return parseInt(localeNumber.replace(/,/g, ''));
}
