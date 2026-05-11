import React, { useState } from 'react';
import CatalogoCajas from './components/CatalogoCajas';
import Carrito from './components/Carrito';
import './App.css';

// Patron Observer: el estado del carrito es compartido y observado por ambos componentes
function App() {
  const [productosEnCarrito, setProductosEnCarrito] = useState([]);
  const [vistaActiva, setVistaActiva] = useState('catalogo');

  const agregarAlCarrito = (producto) => {
    setProductosEnCarrito(prev => {
      const existente = prev.find(item => item.id === producto.id);
      if (existente) {
        return prev.map(item =>
          item.id === producto.id
            ? { ...item, cantidad: item.cantidad + 1 }
            : item
        );
      }
      return [...prev, { ...producto, cantidad: 1 }];
    });
  };

  const eliminarDelCarrito = (productoId) => {
    setProductosEnCarrito(prev => prev.filter(item => item.id !== productoId));
  };

  const totalProductos = productosEnCarrito.reduce((acc, item) => acc + item.cantidad, 0);

  return (
    <div className="app">
      <header className="header">
        <div className="header-contenido">
          <div className="header-marca">
            <span className="marca-icono">G</span>
            <div>
              <h1 className="marca-nombre">GreenBite</h1>
              <p className="marca-slogan">Alimentacion sustentable por suscripcion</p>
            </div>
          </div>
          <nav className="header-nav">
            <button
              className={`nav-btn ${vistaActiva === 'catalogo' ? 'activo' : ''}`}
              onClick={() => setVistaActiva('catalogo')}
            >
              Cajas
            </button>
            <button
              className={`nav-btn ${vistaActiva === 'carrito' ? 'activo' : ''}`}
              onClick={() => setVistaActiva('carrito')}
            >
              Carrito
              {totalProductos > 0 && (
                <span className="carrito-badge">{totalProductos}</span>
              )}
            </button>
          </nav>
        </div>
      </header>

      <main className="main">
        {vistaActiva === 'catalogo' ? (
          <CatalogoCajas onAgregarAlCarrito={agregarAlCarrito} />
        ) : (
          <Carrito
            items={productosEnCarrito}
            onEliminar={eliminarDelCarrito}
            onVolverAlCatalogo={() => setVistaActiva('catalogo')}
          />
        )}
      </main>

      <footer className="footer">
        <p>GreenBite &copy; 2025 — Plataforma de alimentos organicos con impacto social</p>
      </footer>
    </div>
  );
}

export default App;
