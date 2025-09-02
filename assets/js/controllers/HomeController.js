(() => {
  const el = document.getElementById("featured");
  if (!el) return;
  const items = NB.ProductService.all().slice(0, 4);
  el.innerHTML = items.map(p => `
    <div class="card">
      <div style="height:140px;background:rgba(255,255,255,.04);border-radius:10px;margin-bottom:8px"></div>
      <div class="card__title">${p.nombre}</div>
      <div class="card__price">${NB.format.price(p.precio)}</div>
      <a class="btn" href="./detalle-producto.html?id=${p.id}">Ver</a>
    </div>
  `).join("");
})();
