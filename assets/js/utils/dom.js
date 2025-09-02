window.NB = window.NB || {};
NB.dom = {
  qs:(s,r=document)=>r.querySelector(s),
  qsa:(s,r=document)=>Array.from(r.querySelectorAll(s)),
  el:(tag,attrs={},children=[])=>{
    const e=document.createElement(tag);
    Object.entries(attrs).forEach(([k,v])=>{
      if(k==="style"&&v&&typeof v==="object") Object.assign(e.style,v);
      else if(k==="dataset"&&v&&typeof v==="object") Object.assign(e.dataset,v);
      else if(k==="on"&&v&&typeof v==="object") Object.entries(v).forEach(([ev,fn])=>e.addEventListener(ev,fn));
      else if(k in e) e[k]=v; else e.setAttribute(k,v);
    });
    (Array.isArray(children)?children:[children]).forEach(c=>{
      if(c==null) return;
      if(typeof c==="string") e.insertAdjacentHTML("beforeend",c); else e.appendChild(c);
    });
    return e;
  },
  html:(e,h)=>{ if(h===undefined) return e.innerHTML; e.innerHTML=h; return e; },
  on:(e,t,fn,o)=>{ e.addEventListener(t,fn,o); return ()=>e.removeEventListener(t,fn,o); },
  delegate:(root,sel,type,handler)=>{
    return NB.dom.on(root,type,(ev)=>{
      const m=ev.target.closest(sel);
      if(m&&root.contains(m)) handler(ev,m);
    });
  },
  append:(p,...ns)=>{ ns.forEach(n=>p.appendChild(n)); return p; },
  empty:(e)=>{ e.innerHTML=""; return e; },
  serializeForm:(form)=>{
    const data=new FormData(form); const o={};
    for(const [k,v] of data.entries()){
      const val=typeof v==="string"?v.trim():v;
      if(o[k]!==undefined){ if(Array.isArray(o[k])) o[k].push(val); else o[k]=[o[k],val]; }
      else o[k]=val;
    }
    return o;
  },
  setInvalid:(el,bad)=>{ el.classList.toggle("is-invalid",!!bad); return el; }
};
