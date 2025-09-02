(function (w) {
  const LS_KEY = "nxv3_products";

  function read() {
    try { return JSON.parse(localStorage.getItem(LS_KEY) || "[]"); }
    catch { return []; }
  }

  function write(list) {
    localStorage.setItem(LS_KEY, JSON.stringify(list));
  }

  function nextId(list) {
    let max = 0;
    for (let i = 0; i < list.length; i++) {
      const n = parseInt(list[i].id, 10);
      if (!isNaN(n) && n > max) max = n;
    }
    return String(max + 1);
  }

  function list(filterText) {
    const txt = (filterText || "").toLowerCase().trim();
    const arr = read();
    if (!txt) return arr;
    const out = [];
    for (let i = 0; i < arr.length; i++) {
      const p = arr[i];
      const s = (String(p.id) + " " + String(p.nombre) + " " + String(p.categoria)).toLowerCase();
      if (s.includes(txt)) out.push(p);
    }
    return out;
  }

  function get(id) {
    const arr = read();
    for (let i = 0; i < arr.length; i++) if (String(arr[i].id) === String(id)) return arr[i];
    return null;
  }

  function add(data) {
    const arr = read();
    const id = nextId(arr);
    const p = {
      id: String(id),
      nombre: data && data.nombre ? String(data.nombre) : "",
      precio: Number(data && data.precio != null ? data.precio : 0),
      stock: Number(data && data.stock != null ? data.stock : 0),
      categoria: data && data.categoria ? String(data.categoria) : "",
      descripcion: data && data.descripcion ? String(data.descripcion) : "",
      activo: data && data.activo === false ? false : true,
      imagen: data && data.imagen ? String(data.imagen) : ""
    };
    arr.push(p);
    write(arr);
    return p;
  }

  function update(id, data) {
    const arr = read();
    let changed = false;
    for (let i = 0; i < arr.length; i++) {
      if (String(arr[i].id) === String(id)) {
        const curr = arr[i];
        arr[i] = {
          id: String(id),
          nombre: data && data.nombre != null ? String(data.nombre) : curr.nombre,
          precio: data && data.precio != null ? Number(data.precio) : curr.precio,
          stock: data && data.stock != null ? Number(data.stock) : curr.stock,
          categoria: data && data.categoria != null ? String(data.categoria) : curr.categoria,
          descripcion: data && data.descripcion != null ? String(data.descripcion) : curr.descripcion,
          activo: data && data.activo != null ? !!data.activo : curr.activo,
          imagen: data && data.imagen != null ? String(data.imagen) : curr.imagen
        };
        changed = true;
        break;
      }
    }
    if (changed) write(arr);
    return changed;
  }

  function remove(id) {
    const arr = read();
    const next = [];
    let removed = false;
    for (let i = 0; i < arr.length; i++) {
      if (String(arr[i].id) === String(id)) { removed = true; continue; }
      next.push(arr[i]);
    }
    if (removed) write(next);
    return removed;
  }

  w.DB = w.DB || {};
  w.DB.products = { list, get, add, update, remove };
})(window);
