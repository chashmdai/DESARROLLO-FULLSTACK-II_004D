window.NB = window.NB || {};
NB.CartItem = class {
  constructor(productId, qty=1){
    this.productId = productId;
    this.qty = qty;
  }
};
