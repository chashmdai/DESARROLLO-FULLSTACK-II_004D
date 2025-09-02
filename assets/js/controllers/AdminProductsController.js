(() => {
  const tbody = document.querySelector("#admin-products tbody");
  if (!tbody) return;

  const render = () => {
    const data = NB.ProductService.all();
    tbody.innerHTML = data.map(p => `
      <tr data-id="${p.id}">
        <td>${p.id}</td>
        <td>${p.nombre}</td>
        <td>${NB.format.price(p.precio)}</td>
        <td>${p.stock||0}</td>
        <td>${p.categoria||""}</td>
        <td style="text-align:right">
          <a class="btn" href="producto-nuevo.html?id=${p.id}">Editar</a>
          <button class="btn" data-act="del">Eliminar</button>
        </td>
      </tr>
    `).join("");
  };

  tbody.addEventListener("click", e => {
    const btn = e.target.closest("button[data-act='del']");
    if (!btn) return;
    const row = btn.closest("tr[data-id]");
    const id = Number(row.dataset.id);
    const i = NB.state.products.findIndex(p => p.id === id);
    if (i >= 0) {
      NB.state.products.splice(i, 1);
      render();
    }
  });

  render();
})();
