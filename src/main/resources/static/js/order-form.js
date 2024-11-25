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

// 버튼 클릭 시 결제 처리
const btnPay = document.getElementById('btn-pay');
btnPay.addEventListener('click', () => {
    // 주문 작성을 위한 form 구성
    const booksInCart = document.querySelectorAll('.product-container .card');
    let orderDto = {};

    console.log(booksInCart);
});
