window.NB = window.NB || {};
NB.validators = {
  required:v=>String(v||"").trim().length>0,
  min:(v,n)=>String(v||"").trim().length>=Number(n||0),
  email:v=>/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(v||"").trim()),
  number:v=>!isNaN(parseFloat(v)) && isFinite(v)
};
