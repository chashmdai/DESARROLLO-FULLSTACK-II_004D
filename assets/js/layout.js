(function (w) {
  const NB = w.NB || {};

  function getCartCount() {
    try { return (w.Cart && typeof w.Cart.count === "function") ? w.Cart.count() : 0; }
    catch { return 0; }
  }

  function updateCartCount() {
    const el = document.getElementById("cartCount");
    if (!el) return;
    el.textContent = String(getCartCount());
  }

  function mountSite() {
    const $header = document.querySelector("header.site-header");
    if (!$header) return;

    $header.innerHTML = `
      <div class="container topbar">
        <a class="brand" href="../tienda/index.html">Nexbyte</a>
        <button id="navToggle" aria-label="Abrir menú" aria-expanded="false">☰</button>
        <nav id="navMenu" class="nav">
          <a href="../tienda/index.html">Inicio</a>
          <a href="../tienda/productos.html">Productos</a>
          <a href="../tienda/carrito.html">Carrito <span id="cartCount" class="badge">0</span></a>
          <a href="../tienda/login.html">Login</a>
          <a href="../tienda/registro.html">Registro</a>
        </nav>
      </div>
    `;

    const $toggle = document.getElementById("navToggle");
    const $menu = document.getElementById("navMenu");
    if ($toggle && $menu) {
      $toggle.addEventListener("click", () => {
        const open = !$menu.classList.contains("open");
        $menu.classList.toggle("open", open);
        $toggle.setAttribute("aria-expanded", open ? "true" : "false");
      });
      $menu.addEventListener("click", (e) => {
        const a = e.target.closest("a");
        if (!a) return;
        $menu.classList.remove("open");
        $toggle.setAttribute("aria-expanded", "false");
      });
    }

    try {
      const here = location.pathname.split("/").pop();
      document.querySelectorAll("#navMenu a").forEach(a => {
        const href = a.getAttribute("href") || "";
        if (href.endsWith(here)) a.classList.add("active");
      });
    } catch(_) {}

    updateCartCount();

    addEventListener("storage", (ev) => {
      if (ev && ev.key === "nxv3_cart") updateCartCount();
    });
  }

  NB.mountSite = mountSite;
  NB.updateCartCount = updateCartCount;

  w.NB = NB;
})(window);