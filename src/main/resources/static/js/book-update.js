const editor = new tui.Editor({
    el: document.querySelector('#descriptionEditor'),
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    height: '300px',
});

function setEditorContent(event) {
    const descriptionContent = editor.getHTML();
    document.getElementById('description').value = descriptionContent;

    return true;
}

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("bookModifyForm");
    const checkboxes = document.querySelectorAll("input[type='checkbox']");
    const topCategoryElement = document.getElementById("topCategory");
    const middleCategoryElement = document.getElementById("middleCategory");
    const bottomCategoryElement = document.getElementById("bottomCategory");
    const categoryData = document.getElementById("category-data");
    const selectedTopCategoryId = categoryData.getAttribute("data-top-category-id");
    const selectedMiddleCategoryId = categoryData.getAttribute("data-middle-category-id");
    const selectedBottomCategoryId = categoryData.getAttribute("data-bottom-category-id");
    const contributorListInput = document.getElementById("contributorList");
    let contributors = [];

    // 체크박스 초기화
    form.addEventListener("submit", function () {
        checkboxes.forEach(checkbox => {
            if (!checkbox.checked) {
                checkbox.setAttribute("value", "false");
                checkbox.setAttribute("checked", true);
            }
        });
    });

    // 카테고리 초기화
    if (selectedTopCategoryId) {
        topCategoryElement.value = selectedTopCategoryId;

        // 중위 카테고리 로드
        loadCategories(`/admin/api/categories/${selectedTopCategoryId}/children`, middleCategoryElement, selectedMiddleCategoryId, function () {
            if (selectedMiddleCategoryId) {
                loadCategories(`/admin/api/categories/${selectedMiddleCategoryId}/children`, bottomCategoryElement, selectedBottomCategoryId);
            }
        });
    }

    // 상위 카테고리 변경 시 중위 카테고리 로드
    topCategoryElement.addEventListener("change", function () {
        const topCategoryId = this.value;

        loadCategories(`/admin/api/categories/${topCategoryId}/children`, middleCategoryElement, null, function () {
            bottomCategoryElement.innerHTML = '<option value="" disabled selected>하위 카테고리를 선택하세요</option>';
        });
    });

    // 중위 카테고리 변경 시 하위 카테고리 로드
    middleCategoryElement.addEventListener("change", function () {
        const middleCategoryId = this.value;
        loadCategories(`/admin/api/categories/${middleCategoryId}/children`, bottomCategoryElement);
    });

    // 카테고리 로드 함수
    function loadCategories(url, element, selectedCategoryId = null, callback = null) {
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch categories");
                }
                return response.json();
            })
            .then(data => {
                element.innerHTML = '<option value="" disabled selected>카테고리를 선택하세요</option>';
                data.forEach(category => {
                    const selected = category.categoryId == selectedCategoryId ? "selected" : "";
                    element.innerHTML += `<option value="${category.categoryId}" ${selected}>${category.name}</option>`;
                });

                if (callback) callback();
            })
            .catch(error => console.error("Error fetching categories:", error));
    }

    // 기여자 초기화
    document.querySelectorAll("#existingContributorsList .selected-contributor-item").forEach(item => {
        const name = item.getAttribute("data-contributor-key").split("|")[0];
        const role = item.getAttribute("data-contributor-key").split("|")[1];
        contributors.push({ name, role });
    });

    contributorListInput.value = JSON.stringify(contributors);

    // 기여자 삭제 기능
    document.querySelectorAll("#existingContributorsList button").forEach(button => {
        button.addEventListener("click", function () {
            const name = button.parentElement.getAttribute("data-contributor-key").split("|")[0];
            const role = button.parentElement.getAttribute("data-contributor-key").split("|")[1];

            contributors = contributors.filter(contributor => !(contributor.name === name && contributor.role === role));

            button.parentElement.remove();

            contributorListInput.value = JSON.stringify(contributors);
        });
    });

    // 기여자 추가 기능
    document.getElementById("addContributorButton").addEventListener("click", function () {
        const select = document.getElementById("existingContributors");
        Array.from(select.selectedOptions).forEach(option => {
            const [name, role] = option.value.split("|");

            if (!contributors.some(contributor => contributor.name === name && contributor.role === role)) {
                contributors.push({ name, role });

                const selectedContributors = document.getElementById("selectedContributors");
                const div = document.createElement("div");
                div.classList.add("selected-contributor-item");
                div.setAttribute("data-contributor-key", `${name}|${role}`);

                const span = document.createElement("span");
                span.textContent = `[${role}] ${name}`;

                const deleteButton = document.createElement("button");
                deleteButton.textContent = "삭제";
                deleteButton.classList.add("btn", "btn-danger", "btn-sm");
                deleteButton.addEventListener("click", function () {
                    contributors = contributors.filter(contributor => !(contributor.name === name && contributor.role === role));
                    div.remove();
                    contributorListInput.value = JSON.stringify(contributors);
                });

                div.appendChild(span);
                div.appendChild(deleteButton);
                selectedContributors.appendChild(div);
            }
        });

        contributorListInput.value = JSON.stringify(contributors);

        select.selectedIndex = -1;
    });
});

// 선택된 태그를 배열로 관리
let selectedTags = [];

// 태그 추가 함수
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

// 태그 제거 함수
function removeTag(tagElement) {
    const tagName = tagElement.getAttribute('data-tag-name');
    selectedTags = selectedTags.filter(tag => tag !== tagName);

    // 선택된 태그 UI에서 제거
    tagElement.remove();

    // hidden input에 태그 값 업데이트
    updateTagList();
}

// hidden input 업데이트 함수
function updateTagList() {
    const tagListInput = document.getElementById('tagList');
    tagListInput.value = selectedTags.join(","); // 콤마로 구분된 문자열로 전달
}

// 초기화 함수
document.addEventListener('DOMContentLoaded', function () {
    const selectedTagsContainer = document.getElementById('selectedTags');

    // 초기에는 비어 있게 설정
    selectedTagsContainer.innerHTML = '';

    // 숨겨진 input 초기화
    updateTagList();
});


// 폼 데이터 유효성 검사
document.getElementById("bookModifyForm").addEventListener("submit", function (event) {
    const retailPrice = document.getElementById("retailPrice").value;
    const sellingPrice = document.getElementById("sellingPrice").value;
    const remainQuantity = document.getElementById("remainQuantity").value;

    if (retailPrice < 0 || sellingPrice < 0 || remainQuantity < 0) {
        alert("모든 숫자 입력은 음수일 수 없습니다.");
        event.preventDefault();
    }
});