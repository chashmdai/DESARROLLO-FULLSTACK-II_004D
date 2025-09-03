(function(){
  const LS = "nxv3_contact_msgs";
  const ALLOWED_DOMAINS = /(duoc\.cl|profesor\.duoc\.cl|gmail\.com)$/i;

  function read(){ try{ return JSON.parse(localStorage.getItem(LS)||"[]"); }catch{ return []; } }
  function write(a){ localStorage.setItem(LS, JSON.stringify(a)); }
  function emailOk(s){
    if (!s || s.length>100) return false;
    const m=s.trim().toLowerCase();
    if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(m)) return false;
    return ALLOWED_DOMAINS.test(m);
  }
  function setErr(id,msg){ const el=document.getElementById(id); if(el) el.textContent=msg||""; }

  document.getElementById("formContacto").addEventListener("submit",(e)=>{
    e.preventDefault();
    setErr("err-cNombre",""); setErr("err-cCorreo",""); setErr("err-cMsg","");
    document.getElementById("cOk").style.display="none";

    const nombre=document.getElementById("cNombre").value.trim();
    const correo=document.getElementById("cCorreo").value.trim().toLowerCase();
    const msg=document.getElementById("cMsg").value.trim();

    let ok=true;
    if(!nombre || nombre.length>100){ setErr("err-cNombre","Nombre requerido (máx. 100)."); ok=false; }
    if(!emailOk(correo)){ setErr("err-cCorreo","Solo se permite @duoc.cl, @profesor.duoc.cl o @gmail.com (máx. 100)."); ok=false; }
    if(!msg || msg.length>500){ setErr("err-cMsg","Comentario requerido (máx. 500)."); ok=false; }
    if(!ok) return;

    const arr=read();
    arr.push({ nombre, correo, msg, ts:Date.now() });
    write(arr);

    document.getElementById("cOk").style.display="";
    e.target.reset();
  });
})();
