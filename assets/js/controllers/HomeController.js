(() => {
  const featured = document.getElementById("featured");
  const cats = document.getElementById("home-cats");
  const newsForm = document.getElementById("newsletter-form");

  if (featured) {
    const items = NB.ProductService.all().slice(0, 8);
    featured.innerHTML = items.map(p => `
      <div class="card">
        <div style="height:140px;background:rgba(255,255,255,.04);border-radius:10px;margin-bottom:8px"></div>
        <div class="card__title">${p.nombre}</div>
        <div class="card__price">${NB.format.price(p.precio)}</div>
        <a class="btn" href="./detalle-producto.html?id=${p.id}">Ver</a>
      </div>
    `).join("");
  }

  if (cats) {
    const list = NB.ProductService.categories().slice(0, 3);
    cats.innerHTML = list.map((c,i) =>
      `<a href="./productos.html#${encodeURIComponent(c)}">Categoría ${String.fromCharCode(88+i)} (${c})</a>`
    ).join(" · ");
  }

  if (newsForm) {
    newsForm.addEventListener("submit", e => {
      e.preventDefault();
      const email = newsForm.email.value.trim();
      if (!NB.validators.email(email)) { alert("Ingresa un correo válido."); return; }
      alert("¡Gracias por suscribirte!"); newsForm.reset();
    });
  }
})();
