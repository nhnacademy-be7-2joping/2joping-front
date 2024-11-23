function setRating(rating) {
    const stars = document.querySelectorAll('.star');
    document.getElementById('ratingValue').value = rating; // 숨겨진 필드에 값 설정
    stars.forEach((star, index) => {
        star.classList.toggle('selected', index < rating); // 별 색 변경
    });
}