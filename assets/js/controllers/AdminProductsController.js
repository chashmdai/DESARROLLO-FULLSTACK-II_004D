(() => {
  const table = document.getElementById("admin-products");
  if (!table) return;
  const tbody = table.querySelector("tbody");
  const rows = NB.ProductService.all().map(p => `
    <tr>
      <td>${p.id}</td>
      <td>${p.nombre}</td>
      <td>${NB.format.price(p.precio)}</td>
      <td>
        <a href="./producto-nuevo.html?id=${p.id}">Editar</a>
      </td>
    </tr>
  `).join("");
  tbody.innerHTML = rows;
})();
