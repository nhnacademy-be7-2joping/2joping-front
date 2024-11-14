document.addEventListener("scroll", function () {
    const navbar = document.getElementById("navbar");
    if (window.scrollY > 5) {  // 약간의 여유값을 추가하여 확실히 최상단인지 확인
        navbar.classList.add("with-border");
    } else {
        navbar.classList.remove("with-border");
    }
});