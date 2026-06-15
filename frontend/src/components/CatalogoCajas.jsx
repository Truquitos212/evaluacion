import React, { useState, useEffect } from 'react';

// Patron Observer: este componente reacciona a cambios del estado del padre (App)
function CatalogoCajas({ onAgregarAlCarrito }) {
  const [cajas, setCajas] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/productos')
      .then(res => {
        if (!res.ok) throw new Error('Error al cargar las cajas');
        return res.json();
      })
      .then(datos => {
        setCajas(datos);
        setCargando(false);
      })
      .catch(() => {
        setError('No se pudo conectar con el servidor. Mostrando datos de ejemplo.');
        // Datos de respaldo para desarrollo local
        setCajas([
          { id: 1, nombre: 'Caja Verduras de Temporada', descripcion: 'Seleccion semanal de verduras organicas certificadas de agricultores locales de la Region Metropolitana.', precio: 12990, stock: 20, categoria: 'Verduras' },
          { id: 2, nombre: 'Caja Frutas Organicas', descripcion: 'Mix de frutas de estacion sin pesticidas. Incluye manzanas, peras, citricos y frutos del bosque segun disponibilidad.', precio: 10990, stock: 15, categoria: 'Frutas' },
          { id: 3, nombre: 'Caja Mixta Familiar', descripcion: 'La opcion mas completa. Verduras, frutas y legumbres para una familia de 4 personas durante toda la semana.', precio: 19990, stock: 10, categoria: 'Mixta' },
          { id: 4, nombre: 'Caja Legumbres y Granos', descripcion: 'Lentejas, garbanzos, quínoa y arroz integral de produccion agroecologica. Ideal para complementar la dieta.', precio: 8990, stock: 0, categoria: 'Despensa' },
        ]);
        setCargando(false);
      });
  }, []);

  if (cargando) {
    return (
      <div className="cargando">
        <div className="spinner"></div>
        <p>Cargando cajas disponibles...</p>
      </div>
    );
  }

  return (
    <section>
      <h2 className="catalogo-titulo">Cajas disponibles</h2>
      <p className="catalogo-subtitulo">
        Suscripcion semanal — entrega cada lunes en tu domicilio
      </p>

      {error && (
        <div className="alerta alerta-error" style={{ marginBottom: '1.5rem' }}>
          {error}
        </div>
      )}

      <div className="catalogo-grid">
        {cajas.map(caja => (
          <div key={caja.id} className={`caja-card ${caja.stock === 0 ? 'sin-stock' : ''}`}>
            <div className="caja-info">
              {caja.categoria && (
                <span className="caja-etiqueta">{caja.categoria}</span>
              )}
              <h3 className="caja-nombre">{caja.nombre}</h3>
              <p className="caja-descripcion">{caja.descripcion}</p>
              <p className={`caja-stock ${caja.stock === 0 ? 'agotado' : ''}`}>
                {caja.stock === 0
                  ? 'Sin stock disponible'
                  : `${caja.stock} unidades disponibles`}
              </p>
            </div>

            <div className="caja-accion">
              <div className="caja-precio">
                ${caja.precio?.toLocaleString('es-CL')}
                <span> / semana</span>
              </div>
              <button
                className="btn-agregar"
                onClick={() => onAgregarAlCarrito(caja)}
                disabled={caja.stock === 0}
              >
                {caja.stock === 0 ? 'Agotado' : 'Agregar'}
              </button>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

export default CatalogoCajas;
