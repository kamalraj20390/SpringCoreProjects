/**
 * VSMS – app.js
 * Lightweight, dependency-free helpers.
 */
document.addEventListener('DOMContentLoaded', function () {

    // ── Delete / cancel confirmation ───────────────────────────
    window.confirmDelete = function (label) {
        return confirm('Are you sure you want to delete this ' + (label || 'item') + '? This cannot be undone.');
    };

    // ── Auto-dismiss alerts after 5 s ─────────────────────────
    document.querySelectorAll('.alert-dismissible').forEach(function (el) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(el);
            if (bsAlert) bsAlert.close();
        }, 5000);
    });

    // ── Active nav link highlight ──────────────────────────────
    const path = window.location.pathname;
    document.querySelectorAll('.navbar-nav .nav-link').forEach(function (link) {
        const href = link.getAttribute('href');
        if (href && href !== '/' && path.startsWith(href)) {
            link.classList.add('active', 'fw-semibold');
        } else if (href === '/' && path === '/') {
            link.classList.add('active', 'fw-semibold');
        }
    });

    // ── Datetime-local: pre-fill current date/time if empty ───
    document.querySelectorAll('input[type="datetime-local"]').forEach(function (el) {
        if (!el.value) {
            const now = new Date();
            now.setSeconds(0, 0);
            el.value = now.toISOString().slice(0, 16);
        }
    });
});
