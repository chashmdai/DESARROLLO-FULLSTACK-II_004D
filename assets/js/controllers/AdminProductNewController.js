(() => {
  const form = document.getElementById("product-form");
  if (!form) return;
  const params = new URLSearchParams(location.search);
  const id = params.get("id");
  if (id) {
    const p = NB.ProductService.byId(id);
    if (p) {
      form.nombre.value = p.nombre;
      form.precio.value = p.precio;
    }
  }
  form.addEventListener("submit", e => {
    e.preventDefault();
    const nombre = form.nombre.value.trim();
    const precio = parseFloat(form.precio.value);
    let ok = true;
    if (!NB.validators.required(nombre)) { form.nombre.classList.add("is-invalid"); ok = false; } else form.nombre.classList.remove("is-invalid");
    if (!NB.validators.number(precio) || precio <= 0) { form.precio.classList.add("is-invalid"); ok = false; } else form.precio.classList.remove("is-invalid");
    if (!ok) return;
    alert("Producto guardado (demo).");
  });
})();