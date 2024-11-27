document.addEventListener('DOMContentLoaded', function () {
    const tooltip = document.createElement('div');
    tooltip.classList.add('tooltip');
    document.body.appendChild(tooltip);

    document.querySelectorAll('.truncate-text').forEach(element => {
        element.addEventListener('mouseover', function (event) {
            const text = element.getAttribute('data-tooltip');
            tooltip.textContent = text;
            tooltip.style.display = 'block';
            const rect = element.getBoundingClientRect();
            tooltip.style.left = `${rect.left + window.scrollX}px`;
            tooltip.style.top = `${rect.bottom + window.scrollY}px`;
        });

        element.addEventListener('mouseout', function () {
            tooltip.style.display = 'none';
        });
    });
});