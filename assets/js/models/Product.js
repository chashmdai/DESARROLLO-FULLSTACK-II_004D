window.NB = window.NB || {};
NB.Product = class {
  constructor({id,nombre,precio,descripcion,imagenes=[],stock=0,categoria}){
    Object.assign(this,{id,nombre,precio,descripcion,imagenes,stock,categoria});
  }
};