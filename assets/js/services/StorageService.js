window.NB = window.NB || {};
NB.StorageService = {
  get(key, fallback){ try{ const v = JSON.parse(localStorage.getItem(key)); return v==null?fallback:v; }catch(e){ return fallback; } },
  set(key, value){ localStorage.setItem(key, JSON.stringify(value)); },
  remove(key){ localStorage.removeItem(key); }
};
