(() => {
  const grid = document.getElementById("catalog-grid");
  if (!grid) return;
  NB.CartService.load();
  const products = NB.ProductService.all();
  grid.innerHTML = products.map(p => `
    <div class="card">
      <img src="${p.imagenes[0]||''}" alt="${p.nombre}">
      <div class="card__title">${p.nombre}</div>
      <div class="card__price">${NB.format.price(p.precio)}</div>
      <button class="btn" data-id="${p.id}">Añadir</button>
    </div>
  `).join("");
  grid.addEventListener("click", e => {
    const b = e.target.closest("button[data-id]");
    if (!b) return;
    NB.CartService.add(Number(b.dataset.id), 1);
  });
})();