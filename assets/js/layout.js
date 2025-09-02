(() => {
  const LINKS = [
    { href: "index.html",     label: "Home" },
    { href: "productos.html", label: "Productos" },
    { href: "registro.html",  label: "Registro" }
  ];

  let path = location.pathname.split("/").pop();
  if (!path || path === "/") path = "index.html";

  const isActive = (href) =>
    href === path || (href === "productos.html" && path === "detalle-producto.html");

  const header = document.createElement("header");
  header.innerHTML = `
    <div class="nav container">
      <div class="nav__left">
        <a class="nav__brand" href="index.html">NexByte</a>
      </div>
      <nav class="nav__center">
        ${LINKS.map(l => `
          <a href="${l.href}" ${isActive(l.href) ? 'class="active"' : ""}>${l.label}</a>
        `).join("")}
      </nav>
      <div class="nav__right">
        <a class="badge" href="productos.html" id="cart-badge">🛒 <span>0</span></a>
      </div>
    </div>`;
  document.body.prepend(header);

  const footer = document.createElement("footer");
  footer.innerHTML = `
    <div class="container">
      © ${new Date().getFullYear()} NexByte — Demo académica ·
      <a href="index.html">Inicio</a> ·
      <a href="productos.html">Productos</a>
    </div>`;
  document.body.append(footer);

  const readCartCount = () => {
    try {
      const arr = JSON.parse(localStorage.getItem("nb.cart") || "[]");
      if (!Array.isArray(arr)) return 0;
      return arr.reduce((acc, it) => acc + (parseInt(it.qty, 10) || 1), 0);
    } catch {
      return 0;
    }
  };

  const updateBadge = () => {
    const span = header.querySelector("#cart-badge span");
    if (span) span.textContent = readCartCount();
  };

  updateBadge();
  window.addEventListener("nb:cart:changed", updateBadge);
  window.addEventListener("storage", (e) => {
    if (e.key === "nb.cart") updateBadge();
  });
})();
