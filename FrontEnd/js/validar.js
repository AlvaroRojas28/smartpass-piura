exigirRol('COBRADOR');

let lector = null;
let bloqueado = false;   // evita cobrar dos veces el mismo escaneo

async function cargarUnidades() {
  const unidades = await api('/api/catalogos/unidades');
  const sel = document.getElementById('unidad');
  unidades.forEach(u => {
    const op = document.createElement('option');
    op.value = u.idUnidad;
    op.textContent = u.nomUnidad + ' (' + u.placa + ') · ' + u.nomRuta;
    sel.appendChild(op);
  });
}

async function validarCodigo(codigo) {
  if (bloqueado || !codigo) return;
  bloqueado = true;
  const caja = document.getElementById('resultado');
  try {
    const r = await api('/api/pagos/validar', { method: 'POST', body: JSON.stringify({
      codigoQR: codigo, idUnidad: document.getElementById('unidad').value }) });
    caja.textContent = r.aprobado ? 'Pago aprobado' : r.mensaje;
    caja.className = 'resultado ' + (r.aprobado ? 'ok' : 'error');
    document.getElementById('detalle').textContent = r.aprobado
      ? r.pasajero + ' · Pasaje ' + soles(r.monto) + ' · Saldo restante ' + soles(r.saldoRestante) : '';
  } catch (e) {
    caja.textContent = e.message;
    caja.className = 'resultado error';
  }
  setTimeout(() => { bloqueado = false; }, 2500);
}

document.getElementById('btnCamara').onclick = async () => {
  try {
    if (!lector) lector = new Html5Qrcode('lector');
    await lector.start({ facingMode: 'environment' }, { fps: 10, qrbox: 220 }, texto => validarCodigo(texto));
  } catch (e) { alert('No se pudo activar la cámara: ' + e); }
};
document.getElementById('btnManual').onclick = () => {
  validarCodigo(document.getElementById('codigoManual').value.trim());
};

cargarUnidades().catch(e => alert(e.message));
