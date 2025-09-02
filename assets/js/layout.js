(() => {
  const LINKS = [
    { href: "index.html",     label: "Home" },
    { href: "productos.html", label: "Productos" },
    { href: "#",              label: "Nosotros" },
    { href: "#",              label: "Blogs" },
    { href: "#",              label: "Contacto" }
  ];
  let path = location.pathname.split("/").pop();
  if (!path || path === "/") path = "index.html";
  const isActive = (href) => href === path || (href === "productos.html" && path === "detalle-producto.html");

  const header = document.createElement("header");
  header.innerHTML = `
    <div class="nav container">
      <div class="nav__left"><a class="nav__brand" href="index.html">NexByte</a></div>
      <nav class="nav__center">
        ${LINKS.map(l => `<a href="${l.href}" ${isActive(l.href)?'class="active"':''}>${l.label}</a>`).join("")}
      </nav>
      <div class="nav__right" style="display:flex;gap:12px;align-items:center">
        <a href="#" style="opacity:.9">Iniciar sesión</a>
        <a href="registro.html" style="opacity:.9">Registrar</a>
        <a class="badge" href="carrito.html" id="cart-badge">🛒 <span>0</span></a>
      </div>
    </div>`;
  document.body.prepend(header);

  const year = new Date().getFullYear();
  const footer = document.createElement("footer");
  footer.innerHTML = `
    <div class="container" style="display:flex;flex-direction:column;gap:12px">
      <div>© ${year} NexByte — Demo académica · <a href="index.html">Inicio</a> · <a href="productos.html">Productos</a></div>
      <div id="footer-cats" style="font-size:.95rem;opacity:.9"></div>
      <form id="newsletter-form" style="display:flex;gap:8px;justify-content:flex-end;align-items:center">
        <input class="form-control" type="email" name="email" placeholder="Ingresa tu correo" required style="max-width:260px">
        <button class="btn" type="submit">Suscribirse</button>
      </form>
    </div>`;
  document.body.append(footer);

  const readCartCount = () => {
    try{
      const arr = JSON.parse(localStorage.getItem("nb.cart") || "[]");
      return Array.isArray(arr) ? arr.reduce((a,b)=>a+(parseInt(b.qty,10)||0),0) : 0;
    }catch{ return 0; }
  };
  const updateBadge = () => {
    const s = header.querySelector("#cart-badge span");
    if (s) s.textContent = readCartCount();
  };
  updateBadge();
  window.addEventListener("nb:cart:changed", updateBadge);
  window.addEventListener("storage", e => { if (e.key === "nb.cart") updateBadge(); });

  (() => {
    const catsEl = document.getElementById("footer-cats");
    if (!catsEl) return;
    if (window.NB && NB.ProductService && typeof NB.ProductService.categories === "function") {
      const cats = NB.ProductService.categories().slice(0,3);
      catsEl.innerHTML = cats.map((c,i)=>`Categoría ${String.fromCharCode(88+i)} (${c})`).join(" · ");
    } else {
      catsEl.textContent = "Categoría X · Categoría Y · Categoría Z";
    }
  })();

  (() => {
    const form = document.getElementById("newsletter-form");
    if (!form) return;
    form.addEventListener("submit", (e)=>{
      e.preventDefault();
      const email = form.email.value.trim();
      const ok = (window.NB?.validators?.email)
        ? NB.validators.email(email)
        : /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
      if (!ok) { alert("Ingresa un correo válido."); return; }
      alert("¡Gracias por suscribirte!");
      form.reset();
    });
  })();
})();