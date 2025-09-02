window.NB = window.NB || {};
NB.format = {
  price(v){
    try{
      return new Intl.NumberFormat("es-CL",{style:"currency",currency:"CLP",maximumFractionDigits:0}).format(Number(v)||0);
    }catch(e){
      return "$"+(Number(v)||0);
    }
  }
};
