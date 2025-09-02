(() => {
  const wrap = document.getElementById("detail");
  if (!wrap) return;
  NB.CartService.load();
  const id = new URLSearchParams(location.search).get("id");
  const p = NB.ProductService.byId(id);
  if (!p) { wrap.innerHTML = "<div class='container'><p>Producto no encontrado.</p></div>"; return; }
  wrap.innerHTML = `
    <section class="container">
      <div class="grid cols-2">
        <div class="card"><div style="height:260px;background:rgba(255,255,255,.04);border-radius:10px"></div></div>
        <div>
          <h1 class="detail__title">${p.nombre}</h1>
          <p>${p.descripcion||""}</p>
          <div class="detail__price">${NB.format.price(p.precio)}</div>
          <div class="detail__qty">
            <label for="qty">Cantidad</label>
            <input id="qty" type="number" min="1" value="1" class="form-control">
          </div>
          <button id="add" class="btn">Añadir al carrito</button>
        </div>
      </div>
    </section>
  `;
  document.getElementById("add").onclick = () => {
    const q = Math.max(1, parseInt(document.getElementById("qty").value||"1",10));
    NB.CartService.add(p.id, q);
  };
})();
