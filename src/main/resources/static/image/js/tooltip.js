// src/main/resources/static/js/tooltip.js
document.addEventListener('DOMContentLoaded', function() {
    const buttons = document.querySelectorAll('.btn');

    buttons.forEach(button => {
        let timeout;
        const tooltip = document.createElement('span');
        tooltip.classList.add('tooltip');
        tooltip.textContent = button.getAttribute('data-tooltip');
        document.body.appendChild(tooltip);

        button.addEventListener('mouseenter', function() {
            timeout = setTimeout(function() {
                const rect = button.getBoundingClientRect();
                tooltip.style.top = `${rect.top + window.scrollY - tooltip.offsetHeight}px`;
                tooltip.style.left = `${rect.left + window.scrollX + (button.offsetWidth / 2) - (tooltip.offsetWidth / 2)}px`;
                tooltip.style.opacity = '1';
            }, 500);
        });

        button.addEventListener('mouseleave', function() {
            clearTimeout(timeout);
            tooltip.style.opacity = '0';
        });
    });
});