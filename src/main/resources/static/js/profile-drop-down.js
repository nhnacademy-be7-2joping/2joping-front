
document.addEventListener("DOMContentLoaded", function() {
    const profileContainer = document.querySelector(".user-profile-container");
    const dropdownMenu = document.querySelector(".profile-dropdown-menu");

    profileContainer.addEventListener("click", function(event) {
        event.stopPropagation(); // 클릭 이벤트 전파 중지
        dropdownMenu.classList.toggle("hidden");
    });

    // 화면의 다른 곳을 클릭하면 메뉴 닫기
    document.addEventListener("click", function() {
        dropdownMenu.classList.add("hidden");
    });
});