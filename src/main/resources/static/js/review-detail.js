// /**
//  * 별점 렌더링 함수
//  * @param rating 평점 값
//  */
// function renderStars(rating) {
//     const stars = document.querySelectorAll('.stars .star');
//     stars.forEach((star, index) => {
//         star.classList.toggle('selected', index < rating); // 선택된 별만 노란색으로 변경
//     });
// }
//
// // 페이지 로드 시 별점 렌더링
// document.addEventListener('DOMContentLoaded', function () {
//     const ratingElement = document.getElementById('rating-stars');
//     const ratingValue = parseInt(ratingElement.getAttribute('data-rating'), 10) || 0; // 평점값 가져오기
//     renderStars(ratingValue);
// });

/**
 * 별점 렌더링 함수
 * @param {HTMLElement} container - 별점 컨테이너
 * @param {number} rating - 평점 값
 */
function renderStars(container, rating) {
    const stars = container.querySelectorAll('.star');
    stars.forEach((star, index) => {
        star.classList.toggle('selected', index < rating); // 선택된 별만 노란색으로 변경
    });
}

document.addEventListener('DOMContentLoaded', function () {
    // 모든 '.stars' 컨테이너를 선택
    const starContainers = document.querySelectorAll('.stars');
    starContainers.forEach(container => {
        // 각 컨테이너의 data-rating 값 가져오기
        const rating = parseInt(container.getAttribute('data-rating'), 10) || 0;
        const stars = container.querySelectorAll('.star');
        // 평점 값에 따라 별을 색칠
        stars.forEach((star, index) => {
            star.classList.toggle('selected', index < rating);
        });
    });
});
