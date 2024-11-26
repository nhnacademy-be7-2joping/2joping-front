// 상위 카테고리가 변경될 때 중위 카테고리 로드
document.getElementById('topCategory').addEventListener('change', function () {
    const topCategoryId = this.value;
    fetch(`/admin/api/categories/${topCategoryId}/children`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch middle categories");
            }
            return response.json();
        })
        .then(data => {
            const middleCategory = document.getElementById('middleCategory');
            middleCategory.innerHTML = '<option value="" disabled selected>중위 카테고리를 선택하세요</option>';
            data.forEach(category => {
                middleCategory.innerHTML += `<option value="${category.categoryId}">${category.name}</option>`;
            });

            // 중위 카테고리 변경 시 하위 카테고리 초기화
            document.getElementById('bottomCategory').innerHTML = '<option value="" disabled selected>하위 카테고리를 선택하세요</option>';
        })
        .catch(error => console.error("Error fetching middle categories:", error));
});

// 중위 카테고리가 변경될 때 하위 카테고리 로드
document.getElementById('middleCategory').addEventListener('change', function () {
    const middleCategoryId = this.value;
    fetch(`/admin/api/categories/${middleCategoryId}/children`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch bottom categories");
            }
            return response.json();
        })
        .then(data => {
            const bottomCategory = document.getElementById('bottomCategory');
            bottomCategory.innerHTML = '<option value="" disabled selected>하위 카테고리를 선택하세요</option>';
            data.forEach(category => {
                bottomCategory.innerHTML += `<option value="${category.categoryId}">${category.name}</option>`;
            });
        })
        .catch(error => console.error("Error fetching bottom categories:", error));
});

// 기여자 추가 기능
document.getElementById("addContributorButton").addEventListener("click", function () {
    const select = document.getElementById("existingContributors");
    const selectedContributors = document.getElementById("selectedContributors");
    const contributorListInput = document.getElementById("contributorList");

    // 기존 값 파싱
    let contributors = contributorListInput.value ? JSON.parse(contributorListInput.value) : [];

    // 선택된 기여자를 화면에 표시하고 배열에 추가
    Array.from(select.selectedOptions).forEach(option => {
        const [name, role] = option.value.split('|');
        const contributorKey = `${name}|${role}`;

        // 중복 추가 방지
        if (!contributors.some(contributor => contributor.name === name && contributor.role === role)) {
            contributors.push({ name, role });

            // 선택된 항목을 화면에 표시
            const div = document.createElement("div");
            div.classList.add("selected-contributor-item");
            div.setAttribute("data-contributor-key", contributorKey);

            const span = document.createElement("span");
            span.textContent = `[${role}] ${name}`;

            // 삭제 버튼 추가
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "삭제";
            deleteButton.addEventListener("click", function () {
                // 배열에서 기여자 제거
                contributors = contributors.filter(contributor => `${contributor.name}|${contributor.role}` !== contributorKey);
                contributorListInput.value = JSON.stringify(contributors);

                // UI에서 항목 제거
                div.remove();
            });

            div.appendChild(span);
            div.appendChild(deleteButton);
            selectedContributors.appendChild(div);
        }
    });

    // 숨겨진 input 필드에 기여자 JSON 저장
    contributorListInput.value = JSON.stringify(contributors);

    // 선택 초기화
    select.selectedIndex = -1;
});

// 선택된 태그 관리
let selectedTags = [];

function addTag(tagElement) {
    const tagName = tagElement.getAttribute('data-tag-name');

    // 이미 선택된 태그인지 확인
    if (!selectedTags.includes(tagName)) {
        selectedTags.push(tagName);

        // 선택된 태그 UI 추가
        const selectedTagsContainer = document.getElementById('selectedTags');
        const newTagElement = document.createElement('span');
        newTagElement.classList.add('tag-item');
        newTagElement.textContent = tagName;
        newTagElement.setAttribute('data-tag-name', tagName);
        newTagElement.onclick = function () {
            removeTag(newTagElement);
        };
        selectedTagsContainer.appendChild(newTagElement);

        // hidden input에 태그 값 업데이트
        updateTagList();
    }
}

function removeTag(tagElement) {
    const tagName = tagElement.getAttribute('data-tag-name');
    selectedTags = selectedTags.filter(tag => tag !== tagName);

    // 선택된 태그 UI에서 제거
    tagElement.remove();

    // hidden input에 태그 값 업데이트
    updateTagList();
}

function updateTagList() {
    const tagListInput = document.getElementById('tagList');
    tagListInput.value = selectedTags.join(",");
}

document.getElementById("bookModifyForm").addEventListener("submit", function (event) {
    const giftWrappable = document.querySelector('input[name="giftWrappable"]:checked');
    const isActive = document.querySelector('input[name="isActive"]:checked');

    if (!giftWrappable || !isActive) {
        alert("모든 값을 선택해야 합니다.");
        event.preventDefault(); // 제출 중단
    }
});

// 폼 제출 시 입력 검증
document.getElementById('bookRegisterForm').addEventListener('submit', function (event) {
    const retailPrice = document.getElementById('retailPrice').value;
    const sellingPrice = document.getElementById('sellingPrice').value;
    const remainQuantity = document.getElementById('remainQuantity').value;

    if (retailPrice < 0 || sellingPrice < 0 || remainQuantity < 0) {
        alert("모든 숫자 입력은 음수일 수 없습니다.");
        event.preventDefault();
    }
});
