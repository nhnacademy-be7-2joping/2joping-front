function toggleDropdown(event) {
    event.stopPropagation(); // 클릭 이벤트가 부모로 전파되지 않도록 방지
    const dropdownContent = event.currentTarget.nextElementSibling;
    dropdownContent.classList.toggle('show');
}

// 클릭한 곳이 드롭다운 외부라면 닫기
document.addEventListener('click', function () {
    const dropdowns = document.querySelectorAll('.dropdown-content');
    dropdowns.forEach(dropdown => dropdown.classList.remove('show'));
});