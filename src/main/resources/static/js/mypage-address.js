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
function openEditPostcodeSearch(inputElement) {
    new daum.Postcode({
        oncomplete: function(data) {
            // 우편번호와 주소 정보를 가져와서 입력 필드에 채움
            const postalCodeInput = inputElement.closest('.address-content').querySelector('input[name="postalCode"]');
            const roadAddressInput = inputElement.closest('.address-content').parentElement.querySelector('input[name="roadAddress"]');

            if (postalCodeInput) {
                postalCodeInput.value = data.zonecode; // 우편번호 설정
            }
            if (roadAddressInput) {
                roadAddressInput.value = data.roadAddress; // 도로명 주소 설정
            }
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


function deleteAddress(memberAddressId) {
    // 사용자에게 확인 대화상자 표시
    if (confirm('이 주소를 삭제하시겠습니까?')) {
        fetch(`/members/address/` + memberAddressId, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // 페이지 새로고침
                    window.location.reload();
                } else {
                    alert('삭제 실패');
                }
            })
            .catch(error => {
                console.error('삭제 중 에러 발생:', error);
                alert('삭제 중 문제가 발생했습니다.');
            });
    }
}