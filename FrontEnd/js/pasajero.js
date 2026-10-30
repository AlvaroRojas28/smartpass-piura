exigirRol('PASAJERO');

function celda(texto) { const td = document.createElement('td'); td.textContent = texto; return td; }

async function cargarSaldoYHistorial() {
  const h = await api('/api/pasajero/historial');
  document.getElementById('saldo').textContent = soles(h.saldoActual);
  const cuerpo = document.getElementById('historial');
  cuerpo.replaceChildren();
  h.transacciones.forEach(t => {
    const tr = document.createElement('tr');
    [t.fecha, t.hora, t.unidad, t.ruta, soles(t.monto)].forEach(v => tr.appendChild(celda(v)));
    cuerpo.appendChild(tr);
  });
}

async function mostrarQR() {
  const r = await api('/api/pasajero/qr');
  const caja = document.getElementById('qr');
  caja.replaceChildren();
  new QRCode(caja, { text: r.codigoQR, width: 200, height: 200 });
  document.getElementById('saldo').textContent = soles(r.saldoActual);
}

document.getElementById('btnRecargar').onclick = async () => {
  try {
    const r = await api('/api/pasajero/recargas', { method: 'POST', body: JSON.stringify({
      monto: document.getElementById('monto').value,
      metodoSimulado: document.getElementById('metodo').value }) });
    mostrarMensaje('msgRecarga', r.mensaje + '. Saldo actual: ' + soles(r.saldoActual), 'ok');
    await mostrarQR();
  } catch (e) { mostrarMensaje('msgRecarga', e.message, 'error'); }
};
document.getElementById('btnQR').onclick = () => mostrarQR().catch(e => alert(e.message));

cargarSaldoYHistorial().then(mostrarQR).catch(e => alert(e.message));
