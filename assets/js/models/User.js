window.NB = window.NB || {};
NB.User = class {
  constructor({id,nombre,email,telefono,region,comuna}){
    Object.assign(this,{id,nombre,email,telefono,region,comuna});
  }
};
