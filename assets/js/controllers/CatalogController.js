(() => {
  const grid = document.getElementById("catalog-grid");
  if (!grid) return;
  NB.CartService.load();
  const products = NB.ProductService.all();
  grid.innerHTML = products.map(p => `
    <div class="card">
      <div style="height:160px;background:rgba(255,255,255,.04);border-radius:10px;margin-bottom:8px"></div>
      <div class="card__title"><a href="./detalle-producto.html?id=${p.id}">${p.nombre}</a></div>
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
