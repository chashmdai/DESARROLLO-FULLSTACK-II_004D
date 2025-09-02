(() => {
  const table = document.getElementById("cart-table"); if (!table) return;
  const tbody = table.querySelector("tbody");
  const totalEl = document.getElementById("grand-total");

  NB.CartService.load();

  const render = () => {
    const items = NB.CartService.items();
    if (items.length === 0) {
      tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;color:var(--muted)">Tu carrito está vacío.</td></tr>`;
      totalEl.textContent = NB.format.price(0);
      return;
    }
    tbody.innerHTML = items.map(({product, qty}) => `
      <tr data-id="${product.id}">
        <td>${product.nombre}</td>
        <td><input class="form-control" type="number" min="0" value="${qty}" style="max-width:90px"></td>
        <td>${NB.format.price(product.precio)}</td>
        <td>${NB.format.price(product.precio * qty)}</td>
        <td><button class="btn" data-act="rm">Quitar</button></td>
      </tr>`).join("");
    totalEl.textContent = NB.format.price(NB.CartService.total());
  };

  tbody.addEventListener("input", e => {
    const row = e.target.closest("tr[data-id]"); if (!row) return;
    const id = Number(row.dataset.id);
    if (e.target.type === "number") {
      NB.CartService.updateQty(id, parseInt(e.target.value, 10)||0);
      render();
    }
  });
  tbody.addEventListener("click", e => {
    const btn = e.target.closest("[data-act='rm']"); if (!btn) return;
    const row = btn.closest("tr[data-id]"); const id = Number(row.dataset.id);
    NB.CartService.remove(id); render();
  });
  document.getElementById("clear").onclick = () => { NB.CartService.clear(); render(); };

  render();
})();
