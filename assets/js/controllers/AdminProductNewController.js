(() => {
  const form = document.getElementById("product-form");
  if (!form) return;

  const qs = n => form.querySelector(`[name="${n}"]`);
  const getValues = () => ({
    nombre: qs("nombre").value.trim(),
    precio: parseInt(qs("precio").value,10)||0,
    stock: parseInt(qs("stock").value,10)||0,
    categoria: qs("categoria").value.trim(),
    descripcion: qs("descripcion").value.trim()
  });

  const id = Number(new URLSearchParams(location.search).get("id") || 0);
  if (id) {
    const p = NB.ProductService.byId(id);
    if (p) {
      qs("nombre").value = p.nombre||"";
      qs("precio").value = p.precio||0;
      qs("stock").value = p.stock||0;
      qs("categoria").value = p.categoria||"";
      qs("descripcion").value = p.descripcion||"";
    }
  }

  form.addEventListener("submit", e => {
    e.preventDefault();
    const v = getValues();
    if (!v.nombre || v.precio<=0) { alert("Nombre y precio son obligatorios."); return; }

    if (id) {
      const p = NB.ProductService.byId(id);
      if (p) Object.assign(p, v);
      alert("Producto actualizado.");
    } else {
      const nextId = NB.state.products.length ? Math.max(...NB.state.products.map(x=>x.id))+1 : 1;
      NB.state.products.push({ id: nextId, imagenes:[""], ...v });
      alert("Producto creado.");
      form.reset();
    }
  });
})();
