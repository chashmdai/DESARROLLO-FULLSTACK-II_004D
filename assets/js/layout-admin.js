(() => {
  const wrap = document.getElementById("admin-wrapper");
  if (!wrap) return;

  let path = location.pathname.split("/").pop();
  if (!path || path === "/") path = "index.html";

  const aside = document.createElement("aside");
  aside.className = "aside";
  aside.innerHTML = `
    <h3 style="margin-top:0">NexByte · Admin</h3>
    <nav>
      <a href="index.html" ${path==="index.html"?'class="active"':''}>Dashboard</a>
      <a href="productos-listado.html" ${path==="productos-listado.html"?'class="active"':''}>Productos</a>
      <a href="producto-nuevo.html" ${path==="producto-nuevo.html"?'class="active"':''}>Nuevo producto</a>
    </nav>
  `;

  const main = document.createElement("main");
  main.className = "main";
  while (wrap.firstChild) main.appendChild(wrap.firstChild);

  const shell = document.createElement("div");
  shell.className = "wrapper";
  shell.append(aside, main);
  wrap.replaceWith(shell);
})();
