exigirRol('ADMINISTRADOR');

function llenarTabla(id, filas, columnas, ultimaEsMonto) {
  const cuerpo = document.getElementById(id);
  cuerpo.replaceChildren();
  filas.forEach(f => {
    const tr = document.createElement('tr');
    columnas.forEach((c, i) => {
      const td = document.createElement('td');
      td.textContent = (ultimaEsMonto && i === columnas.length - 1) ? soles(f[c]) : f[c];
      tr.appendChild(td);
    });
    cuerpo.appendChild(tr);
  });
}

async function cargarFiltros() {
  const unidades = await api('/api/catalogos/unidades');
  const rutas = await api('/api/catalogos/rutas');
  unidades.forEach(u => document.getElementById('unidad').add(new Option(u.nomUnidad, u.idUnidad)));
  rutas.forEach(r => document.getElementById('ruta').add(new Option(r.nomRuta, r.idRuta)));
  const hoy = new Date().toISOString().slice(0, 10);
  const hace7 = new Date(Date.now() - 6 * 86400000).toISOString().slice(0, 10);
  document.getElementById('fechaFin').value = hoy;
  document.getElementById('fechaInicio').value = hace7;
}

async function generarReporte() {
  const p = new URLSearchParams();
  ['fechaInicio', 'fechaFin', 'unidad', 'ruta'].forEach(id => {
    const v = document.getElementById(id).value;
    if (v) p.append(id === 'unidad' ? 'idUnidad' : id === 'ruta' ? 'idRuta' : id, v);
  });
  try {
    const r = await api('/api/admin/reportes/recaudacion?' + p.toString());
    document.getElementById('total').textContent = soles(r.recaudacionTotal);
    document.getElementById('transacciones').textContent = r.totalTransacciones;
    document.getElementById('pasajeros').textContent = r.totalPasajeros;
    llenarTabla('porDia', r.porDia, ['fecha', 'transacciones', 'monto'], true);
    llenarTabla('porUnidad', r.porUnidad, ['unidad', 'placa', 'transacciones', 'monto'], true);
    llenarTabla('porRuta', r.porRuta, ['ruta', 'transacciones', 'monto'], true);
    document.getElementById('msg').className = 'msg';
  } catch (e) { mostrarMensaje('msg', e.message, 'error'); }
}

document.getElementById('btnGenerar').onclick = generarReporte;
cargarFiltros().then(generarReporte).catch(e => alert(e.message));
