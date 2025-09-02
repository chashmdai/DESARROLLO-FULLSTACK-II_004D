(function (w) {
  const LS_KEY = "nxv3_cart";

  function read() {
    try { return JSON.parse(localStorage.getItem(LS_KEY) || "[]"); }
    catch { return []; }
  }

  function write(arr) {
    localStorage.setItem(LS_KEY, JSON.stringify(arr));
  }

  function items() {
    return read();
  }

  function getQty(id) {
    const arr = read();
    const it = arr.find(x => String(x.id) === String(id));
    return it ? Number(it.qty) : 0;
  }

  function add(id, qty = 1) {
    const n = Number(qty);
    if (!Number.isFinite(n) || n <= 0) return getQty(id);

    const arr = read();
    const sid = String(id);
    const found = arr.find(x => String(x.id) === sid);

    if (found) found.qty = Number(found.qty || 0) + n;
    else arr.push({ id: sid, qty: n });

    write(arr);
    return getQty(id);
  }

  function update(id, qty) {
    const n = Number(qty);
    if (!Number.isFinite(n) || n < 0) return false;

    const arr = read();
    const sid = String(id);
    const i = arr.findIndex(x => String(x.id) === sid);
    if (i === -1) return false;

    if (n === 0) arr.splice(i, 1);
    else arr[i].qty = n;

    write(arr);
    return true;
  }

  function remove(id) {
    const arr = read();
    const next = arr.filter(x => String(x.id) !== String(id));
    const changed = next.length !== arr.length;
    if (changed) write(next);
    return changed;
  }

  function clear() { write([]); }

  function mergeProducts() {
    const cart = read();
    const products = (w.DB && w.DB.products && w.DB.products._read)
      ? w.DB.products._read()
      : [];
    return cart.map(it => {
      const p = products.find(pr => String(pr.id) === String(it.id)) || null;
      return { id: String(it.id), qty: Number(it.qty) || 0, product: p };
    });
  }

  function count() {
    return read().reduce((s, it) => s + (Number(it.qty) || 0), 0);
  }

  function subtotal() {
    const merged = mergeProducts();
    let total = 0;
    for (let i = 0; i < merged.length; i++) {
      const row = merged[i];
      const price = row.product ? Number(row.product.precio || 0) : 0;
      total += price * (row.qty || 0);
    }
    return total;
  }

  w.Cart = { items, add, update, remove, clear, getQty, mergeProducts, count, subtotal };
})(window);