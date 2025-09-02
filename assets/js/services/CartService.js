window.NB = window.NB || {};
NB.CartService = {
  key:"nb.cart",
  load(){ NB.state.cart = NB.StorageService.get(this.key, []); return NB.state.cart; },
  save(){ NB.StorageService.set(this.key, NB.state.cart); NB.emit("nb:cart:changed"); return NB.state.cart; },
  add(productId, qty=1){
    qty = parseInt(qty,10)||1;
    const it = NB.state.cart.find(i=>String(i.productId)===String(productId));
    if(it){ it.qty = (parseInt(it.qty,10)||0) + qty; } else { NB.state.cart.push({productId, qty}); }
    return this.save();
  },
  remove(productId){
    NB.state.cart = NB.state.cart.filter(i=>String(i.productId)!==String(productId));
    return this.save();
  },
  updateQty(productId, qty){
    qty = parseInt(qty,10)||0;
    const it = NB.state.cart.find(i=>String(i.productId)===String(productId));
    if(!it) return this.save();
    if(qty<=0) return this.remove(productId);
    it.qty = qty;
    return this.save();
  },
  clear(){ NB.state.cart = []; return this.save(); },
  count(){ return NB.state.cart.reduce((a,b)=>a+(parseInt(b.qty,10)||0),0); },
  total(){ return NB.state.cart.reduce((s,i)=>{ const p=NB.ProductService.byId(i.productId); return s+((p?.precio||0)*(parseInt(i.qty,10)||0)); },0); },
  items(){ return NB.state.cart.map(i=>({ product: NB.ProductService.byId(i.productId), qty: parseInt(i.qty,10)||0 })); }
};
