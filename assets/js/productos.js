(function (w) {
  NB.mountSite();

  const $q = document.getElementById('q');
  const $grid = document.getElementById('grid');
  const $empty = document.getElementById('empty');
  const $cartCountTop = document.getElementById('cartCountTop');

  function updateTopCount(){
    const n = (w.Cart && w.Cart.count) ? w.Cart.count() : 0;
    if ($cartCountTop) $cartCountTop.textContent = String(n);
    if (w.NB && typeof w.NB.updateCartCount === 'function') w.NB.updateCartCount();
  }
  updateTopCount();
  addEventListener('storage', (ev) => {
    if (ev && ev.key === 'nxv3_cart') updateTopCount();
  });

  const CLP = new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP', maximumFractionDigits: 0 });

  function escapeHtml(s){
    return String(s).replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
  }

  function card(p){
    const canBuy = !!p.activo && Number(p.stock) > 0;
    const img = p.imagen ? `<img class="p-img" src="${escapeHtml(p.imagen)}" alt="${escapeHtml(p.nombre)}" onerror="this.style.display='none'">`
                         : `<div class="p-img placeholder">Sin imagen</div>`;
    return `
      <article class="card" data-id="${p.id}">
        <a class="thumb" href="./detalle-producto.html?id=${encodeURIComponent(p.id)}" aria-label="${escapeHtml(p.nombre)}">
          ${img}
        </a>
        <div class="p-body">
          <h2 class="p-name">${escapeHtml(p.nombre || '')}</h2>
          <div class="p-meta">
            <span class="p-price">${CLP.format(Number(p.precio || 0))}</span>
            <span class="p-stock ${Number(p.stock)>0 ? 'ok' : 'out'}">${Number(p.stock)||0} en stock</span>
          </div>
          <div class="p-actions">
            <button class="btn add" ${canBuy ? '' : 'disabled'}>${canBuy ? 'Añadir al carrito' : 'Sin stock'}</button>
            <a class="btn ghost" href="./detalle-producto.html?id=${encodeURIComponent(p.id)}">Ver</a>
          </div>
        </div>
      </article>
    `;
  }

  function render(term){
    const data = w.DB && w.DB.products ? w.DB.products.list(term) : [];
    const has = data.length > 0;
    $empty.style.display = has ? 'none' : '';
    $grid.innerHTML = has ? data.map(card).join('') : '';
  }

  document.addEventListener('click', (e) => {
    const btn = e.target.closest('.card .btn.add');
    if (!btn) return;
    const card = btn.closest('.card');
    if (!card) return;
    const id = card.getAttribute('data-id');
    if (!id) return;

    if (w.Cart && typeof w.Cart.add === 'function') {
      w.Cart.add(id, 1);
      updateTopCount();

      const prev = btn.textContent;
      btn.textContent = 'Añadido ✓';
      btn.disabled = false;
      setTimeout(() => { btn.textContent = prev; }, 900);
    }
  });

  $q.addEventListener('input', () => render($q.value.trim()));

  render('');
})(window);