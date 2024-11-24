function toggleAddressForm() {
    const formContainer = document.getElementById('addressFormContainer');
    formContainer.style.display = formContainer.style.display === 'none' || !formContainer.style.display ? 'block' : 'none';
}

function openPostcodeSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('postalCode').value = data.zonecode;
            document.getElementById('roadAddress').value = data.roadAddress;
        }
    }).open();
}

function enableEditMode(button) {
    const card = button.closest('.address-card');
    const inputs = card.querySelectorAll('.edit-input');
    const spans = card.querySelectorAll('.view-mode');
    const saveBtn = card.querySelector('.save-button');
    const cancelBtn = card.querySelector('.cancel-button');
    const editBtn = card.querySelector('.edit-btn');

    inputs.forEach(input => input.classList.add('active'));
    spans.forEach(span => span.style.display = 'none');

    saveBtn.style.display = 'block';
    cancelBtn.style.display = 'block';
    editBtn.style.display = 'none';
}

function cancelEditMode(button) {
    const card = button.closest('.address-card');
    const inputs = card.querySelectorAll('.edit-input');
    const spans = card.querySelectorAll('.view-mode');
    const saveBtn = card.querySelector('.save-button');
    const cancelBtn = card.querySelector('.cancel-button');
    const editBtn = card.querySelector('.edit-btn');

    // 입력값 초기화
    inputs.forEach(input => {
        const span = card.querySelector(`.view-mode[th\\:text*='${input.name}']`);
        if (span) {
            input.value = span.textContent.trim();
        }
        input.classList.remove('active');
    });

    // 뷰 모드 표시
    spans.forEach(span => span.style.display = 'block');

    // 버튼 상태 업데이트
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
    editBtn.style.display = 'block';
}
