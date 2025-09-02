window.NB = window.NB || {};
NB.state = { cart: [], products: [] };
NB.emit = (name, detail) => window.dispatchEvent(new CustomEvent(name, { detail }));