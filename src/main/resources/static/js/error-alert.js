document.addEventListener("DOMContentLoaded", function () {
    const errorContainer = document.getElementById("error-container");
    const errorCode = errorContainer.getAttribute("data-error-code");
    const errorMessage = errorContainer.getAttribute("data-error-message");

    if (errorCode && errorMessage) {
        alert(`${errorMessage}`);
    }
});