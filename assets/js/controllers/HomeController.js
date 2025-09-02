(() => {
  const el = document.getElementById("featured");
  if (!el) return;
  const items = NB.ProductService.all().slice(0, 4);
  el.innerHTML = items.map(p => `
    <div class="card">
      <img src="${p.imagenes[0]||''}" alt="${p.nombre}">
      <div class="card__title">${p.nombre}</div>
      <div class="card__price">${NB.format.price(p.precio)}</div>
      <a class="btn" href="./detalle-producto.html?id=${p.id}">Ver</a>
    </div>
  `).join("");
})();