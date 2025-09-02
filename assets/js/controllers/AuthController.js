(() => {
  const form = document.getElementById("register-form");
  if (!form) return;
  const setInvalid = (el, bad) => el.classList.toggle("is-invalid", bad);
  form.addEventListener("submit", e => {
    e.preventDefault();
    const { nombre, email, email2, pass, pass2 } = form;
    let ok = true;
    setInvalid(nombre, !NB.validators.min(nombre.value, 3)); ok = ok && NB.validators.min(nombre.value, 3);
    setInvalid(email, !NB.validators.email(email.value)); ok = ok && NB.validators.email(email.value);
    setInvalid(email2, email2.value !== email.value); ok = ok && email2.value === email.value;
    setInvalid(pass, !NB.validators.min(pass.value, 6)); ok = ok && NB.validators.min(pass.value, 6);
    setInvalid(pass2, pass2.value !== pass.value); ok = ok && pass2.value === pass.value;
    if (!ok) return;
    alert("Registro correcto (demo).");
  });
})();