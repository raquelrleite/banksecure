/**
 * Funcionalidade de Menu Hamburger para Mobile
 */

export function inicializarMenuHamburger() {
    const hamburgerBtn = document.querySelector('#hamburger-btn');
    const sidebar = document.querySelector('.sidebar');
    const mainContent = document.querySelector('.main-content');

    if (!hamburgerBtn || !sidebar) {
        return; // Elementos não encontrados
    }

    // Menu hamburger functionality
    hamburgerBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        sidebar.classList.toggle('open');
        hamburgerBtn.classList.toggle('active');
    });

    // Fechar menu ao clicar em um item
    document.querySelectorAll('.sidebar .nav-item').forEach(item => {
        item.addEventListener('click', () => {
            sidebar.classList.remove('open');
            hamburgerBtn.classList.remove('active');
        });
    });

    // Fechar menu ao clicar fora
    document.addEventListener('click', (e) => {
        if (!sidebar.contains(e.target) && !hamburgerBtn.contains(e.target)) {
            if (sidebar.classList.contains('open')) {
                sidebar.classList.remove('open');
                hamburgerBtn.classList.remove('active');
            }
        }
    });

    // Fechar menu ao pressionar ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && sidebar.classList.contains('open')) {
            sidebar.classList.remove('open');
            hamburgerBtn.classList.remove('active');
        }
    });
}

