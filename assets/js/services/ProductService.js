window.NB = window.NB || {};
NB.ProductService = {
  all(){ return NB.state.products; },
  byId(id){ id=String(id); return NB.state.products.find(p=>String(p.id)===id); },
  search(q){ q=String(q||"").toLowerCase(); return NB.state.products.filter(p=>p.nombre.toLowerCase().includes(q)||p.categoria.toLowerCase().includes(q)); },
  categories(){ return [...new Set(NB.state.products.map(p=>p.categoria))]; }
};