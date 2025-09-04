(function (w) {
  const NB = w.NB || (w.NB = {});
  const LS_CART = "nxv3_cart";

  function readCart() {
    try { return JSON.parse(localStorage.getItem(LS_CART) || "[]"); }
    catch { return []; }
  }
  function cartSize(arr) {
    let s = 0;
    for (let i = 0; i < arr.length; i++) s += Number(arr[i].qty || 0);
    return s;
  }

  NB.updateCartBadge = function () {
    const badge = document.getElementById("nbCart");
    if (!badge) return;
    badge.textContent = String(cartSize(readCart()));
  };

  function buildFooterCats() {
    try {
      const list = (w.DB && w.DB.products && w.DB.products.list) ? w.DB.products.list() : [];
      const map = {};
      list.forEach(p => {
        const c = (p.categoria || "Otros").trim();
        map[c] = (map[c] || 0) + 1;
      });
      const cats = Object.entries(map).sort((a,b)=>b[1]-a[1]).slice(0,3);
      if (!cats.length) return '';
      return cats.map(([c]) => `<a href="productos.html">${escapeHtml(c)}</a>`).join(" | ");
    } catch { return ''; }
  }
  function escapeHtml(s){ return String(s??'').replace(/[&<>"']/g,m=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;' }[m])); }

  NB.mountSite = function () {
    const $h = document.querySelector(".site-header");
    if ($h) {
      $h.innerHTML = `
        <div class="container nb-bar">
          <a class="nb-brand" href="index.html"><span class="nb-logo">▣</span> <span>Nexbyte</span></a>
          <nav class="nb-nav">
            <a href="index.html">Home</a>
            <a href="productos.html">Productos</a>
            <a href="nosotros.html">Nosotros</a>
            <a href="blogs.html">Blogs</a>
            <a href="contacto.html">Contacto</a>
          </nav>
          <div class="nb-actions">
            <a href="login.html" class="nb-link">Iniciar sesión</a>
            <span class="sep">|</span>
            <a href="registro.html" class="nb-link">Registrar usuario</a>
            <a href="carrito.html" class="nb-cart" aria-label="Carrito">🛒 <span id="nbCart" class="pill">0</span></a>
          </div>
        </div>
      `;
    }

    // Footer
    const $f = document.querySelector(".site-footer");
    if ($f) {
      $f.innerHTML = `
        <div class="container nb-footer">
          <div class="nb-pay">
            <span class="muted">Site Name</span>
            <div class="nb-pay-icons" aria-hidden="true">
              <span class="pay pay-visa">💳</span>
              <span class="pay pay-mc">💳</span>
              <span class="pay pay-am">💳</span>
            </div>
          </div>
          <div class="nb-cats">
            ${buildFooterCats() || '<a href="productos.html">Category X</a> | <a href="productos.html">Category Y</a> | <a href="productos.html">Category Z</a>'}
          </div>
          <form class="nb-news" onsubmit="event.preventDefault(); alert('¡Gracias! (demo)');">
            <label class="muted" for="newsEmail">Únete al newsletter</label>
            <div class="nb-news-row">
              <input id="newsEmail" type="email" placeholder="Enter Email" maxlength="100" />
              <button type="submit" class="btn">Suscribirse</button>
            </div>
          </form>
        </div>
      `;
    }

    NB.updateCartBadge();
    w.addEventListener("nx:cart-changed", NB.updateCartBadge);
    w.addEventListener("nx:products-seeded", () => {
      const $f2 = document.querySelector(".nb-cats");
      if ($f2) $f2.innerHTML = buildFooterCats();
    });
  };
})(window);