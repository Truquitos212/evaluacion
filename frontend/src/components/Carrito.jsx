import React, { useState } from 'react';

// Patron Observer: este componente observa el estado de items del padre
function Carrito({ items, onEliminar, onVolverAlCatalogo }) {
  const [procesando, setProcesando] = useState(false);
  const [resultado, setResultado] = useState(null);

  const totalCajas = items.reduce((acc, item) => acc + item.cantidad, 0);
  const totalPrecio = items.reduce((acc, item) => acc + item.precio * item.cantidad, 0);

  const finalizarCompra = async () => {
    setProcesando(true);
    setResultado(null);

    const compra = {
      items: items.map(item => ({
        productoId: item.id,
        cantidad: item.cantidad,
        precioUnitario: item.precio,
      })),
      total: totalPrecio,
      estado: 'PENDIENTE',
    };

    try {
      const respuesta = await fetch('http://localhost:8080/api/compras', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(compra),
      });

      if (!respuesta.ok) throw new Error('Error al procesar la compra');

      const datos = await respuesta.json();
      setResultado({
        tipo: 'exito',
        mensaje: `Suscripcion registrada con exito. N° de pedido: ${datos.id || '---'}`,
      });
    } catch {
      setResultado({
        tipo: 'error',
        mensaje: 'No se pudo conectar con el servidor. Intenta nuevamente.',
      });
    } finally {
      setProcesando(false);
    }
  };

  return (
    <div className="carrito-contenedor">
      <div className="carrito-cabecera">
        <h2 className="carrito-titulo">Tu carrito</h2>
        <button className="btn-volver" onClick={onVolverAlCatalogo}>
          Volver al catalogo
        </button>
      </div>

      {items.length === 0 ? (
        <div className="carrito-vacio">
          <p>No hay cajas en tu carrito todavia.</p>
          <button className="btn-agregar" onClick={onVolverAlCatalogo}>
            Ver cajas disponibles
          </button>
        </div>
      ) : (
        <>
          <div className="carrito-items">
            {items.map(item => (
              <div key={item.id} className="carrito-item">
                <div className="item-info">
                  <span className="item-nombre">{item.nombre}</span>
                  <span className="item-detalle">
                    {item.cantidad} x ${item.precio?.toLocaleString('es-CL')} / semana
                  </span>
                </div>
                <div className="item-accion">
                  <span className="item-subtotal">
                    ${(item.precio * item.cantidad).toLocaleString('es-CL')}
                  </span>
                  <button
                    className="btn-eliminar"
                    onClick={() => onEliminar(item.id)}
                    title="Eliminar del carrito"
                  >
                    x
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="carrito-resumen">
            <div className="resumen-fila">
              <span>Cajas seleccionadas</span>
              <span>{totalCajas}</span>
            </div>
            <div className="resumen-fila">
              <span>Frecuencia</span>
              <span>Semanal</span>
            </div>
            <div className="resumen-total">
              <span className="resumen-total-label">Total semanal</span>
              <span className="resumen-total-precio">
                ${totalPrecio.toLocaleString('es-CL')}
              </span>
            </div>

            {resultado && (
              <div className={`alerta alerta-${resultado.tipo}`} style={{ marginTop: '1rem' }}>
                {resultado.mensaje}
              </div>
            )}

            <button
              className="btn-comprar"
              onClick={finalizarCompra}
              disabled={procesando}
            >
              {procesando ? 'Procesando...' : 'Confirmar suscripcion'}
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default Carrito;
