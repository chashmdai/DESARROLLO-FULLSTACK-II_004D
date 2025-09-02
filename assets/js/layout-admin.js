// NexByte Admin Layout — header + aside + toggles
// Clases esperadas por el CSS: a-open (móvil) / a-collapsed (desktop)
(function (global) {
  const AX = global.AX || {};

  AX.mountLayout = function () {
    const $header = document.getElementById("admin-header");
    const $aside  = document.getElementById("admin-aside");
    if (!$header || !$aside) return;

    // Header
    $header.innerHTML = `
      <div class="a-topbar container">
        <button class="a-burger" aria-label="Abrir menú" aria-expanded="false">☰</button>
        <div class="a-brand">
          <span class="i">🧩</span><span>Admin Nexbyte</span>
        </div>
        <div class="a-actions">
          <button class="a-icon" title="Notificaciones">🔔</button>
          <button class="a-icon" title="Perfil">👤</button>
          <button class="a-icon a-collapse" title="Colapsar menú">⇤</button>
        </div>
      </div>
    `;

    // Aside
    $aside.innerHTML = `
      <nav class="a-nav">
        <a class="a-link" href="index.html"><span class="i">🏠</span><span>Dashboard</span></a>

        <details>
          <summary><span class="i">🛒</span><span>Productos</span></summary>
          <a class="a-link" href="productos-listado.html">Listado</a>
          <a class="a-link" href="producto-nuevo.html">Nuevo</a>
        </details>

        <details>
          <summary><span class="i">🙍</span><span>Usuarios</span></summary>
          <a class="a-link" href="usuarios-listado.html">Listado</a>
          <a class="a-link" href="usuario-nuevo.html">Nuevo</a>
        </details>

        <a class="a-link" href="#"><span class="i">📦</span><span>Inventario</span></a>
        <a class="a-link" href="#"><span class="i">📈</span><span>Reportes</span></a>
      </nav>
    `;

    // Overlay móvil (si no existe)
    let $dim = document.querySelector(".a-dim");
    if (!$dim) {
      $dim = document.createElement("div");
      $dim.className = "a-dim";
      document.body.appendChild($dim);
    }

    // Comportamiento
    const $burger   = $header.querySelector(".a-burger");
    const $collapse = $header.querySelector(".a-collapse");
    const mq = matchMedia("(max-width:1024px)");

    const isMobile = () => mq.matches;

    function closeMobile() {
      document.body.classList.remove("a-open");
      $burger.setAttribute("aria-expanded", "false");
    }

    function toggleMenu() {
      if (isMobile()) {
        const open = document.body.classList.toggle("a-open");
        $burger.setAttribute("aria-expanded", open ? "true" : "false");
      } else {
        document.body.classList.toggle("a-collapsed");
      }
    }

    $burger.addEventListener("click", toggleMenu);
    $collapse.addEventListener("click", () => document.body.classList.toggle("a-collapsed"));
    $dim.addEventListener("click", closeMobile);
    addEventListener("keyup", e => { if (e.key === "Escape") closeMobile(); });
    mq.addEventListener("change", closeMobile);

    // Link activo
    try {
      const here = location.pathname.split("/").pop() || "index.html";
      $aside.querySelectorAll("a.a-link").forEach(a => {
        if (a.getAttribute("href") === here) {
          a.classList.add("active");
          const det = a.closest("details");
          if (det) det.open = true;
        }
      });
    } catch (_) {}
  };

  global.AX = AX;
})(window);
