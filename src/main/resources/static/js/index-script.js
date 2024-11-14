document.addEventListener("DOMContentLoaded", function() {
    // 슬라이더 1 관리
    let currentIndex1 = 0;
    const slides1 = document.querySelectorAll('.slide-container-1 img');
    const totalSlides1 = slides1.length;
    const slideContainer1 = document.querySelector('.slide-container-1');

    function showNextSlide1() {
        currentIndex1 = (currentIndex1 + 1) % totalSlides1;
        slideContainer1.style.transform = `translateX(-${currentIndex1 * 100}%)`;
    }

    setInterval(showNextSlide1, 3000); // 슬라이더 1: 3초마다 전환

    // 슬라이더 2 관리
    let currentIndex2 = 0;
    const slides2 = document.querySelectorAll('.slide-container-2 img');
    const totalSlides2 = slides2.length;
    const slideContainer2 = document.querySelector('.slide-container-2');

    function showNextSlide2() {
        currentIndex2 = (currentIndex2 + 1) % totalSlides2;
        slideContainer2.style.transform = `translateX(-${currentIndex2 * 100}%)`;
    }

    setInterval(showNextSlide2, 4000); // 슬라이더 2: 4초마다 전환
});
