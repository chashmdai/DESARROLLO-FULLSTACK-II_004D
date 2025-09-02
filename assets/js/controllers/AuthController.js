(() => {
  const form = document.getElementById("register-form");
  if (!form) return;

  const msg = (id, t) => { const el = document.getElementById(id); if (el) el.textContent = t||""; };
  const bad = (el, on) => el.classList.toggle("is-invalid", !!on);

  const validators = {
    nombre: v => NB.validators.min(v, 3) ? "" : "Mínimo 3 caracteres.",
    email: v => NB.validators.email(v) ? "" : "Correo inválido.",
    email2: (v, all) => v === all.email ? "" : "Debe coincidir con el correo.",
    pass: v => NB.validators.min(v, 6) ? "" : "Mínimo 6 caracteres.",
    pass2: (v, all) => v === all.pass ? "" : "Debe coincidir con la contraseña.",
    telefono: v => v.trim()==="" || /^\+?56\s?9\s?\d{4}\s?\d{4}$/.test(v) ? "" : "Formato: +56 9 XXXX XXXX"
  };

  const values = () => ({
    nombre: form.nombre.value.trim(),
    email: form.email.value.trim(),
    email2: form.email2.value.trim(),
    pass: form.pass.value,
    pass2: form.pass2.value,
    telefono: form.telefono.value.trim()
  });

  const validateField = name => {
    const all = values();
    const err = validators[name](all[name], all);
    bad(form[name], !!err);
    msg("hint-"+name, err);
    return !err;
  };

  ["nombre","email","email2","pass","pass2","telefono"].forEach(n => {
    form[n].addEventListener("input", () => validateField(n));
    form[n].addEventListener("blur", () => validateField(n));
  });

  form.addEventListener("submit", e => {
    e.preventDefault();
    const ok = ["nombre","email","email2","pass","pass2","telefono"].every(validateField);
    if (!ok) { const first = form.querySelector(".is-invalid"); if (first) first.focus(); return; }
    const payload = values();
    const users = JSON.parse(localStorage.getItem("nb.users") || "[]");
    users.push({ id: Date.now(), ...payload });
    localStorage.setItem("nb.users", JSON.stringify(users));
    localStorage.setItem("nb.lastUser", JSON.stringify(payload));
    alert("Registro correcto (demo).");
    form.reset();
  });
})();
