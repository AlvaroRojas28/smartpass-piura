// Utilidades comunes del frontend (sesion JWT y llamadas a la API REST)
const SESION = 'smartpass_sesion';

function guardarSesion(datos) { localStorage.setItem(SESION, JSON.stringify(datos)); }
function obtenerSesion() { try { return JSON.parse(localStorage.getItem(SESION)); } catch (e) { return null; } }
function cerrarSesion() { localStorage.removeItem(SESION); window.location.href = '/index.html'; }

function exigirRol(rol) {
  const s = obtenerSesion();
  if (!s || s.rol !== rol) { window.location.href = '/index.html'; return null; }
  const etiqueta = document.getElementById('usuarioActual');
  if (etiqueta) etiqueta.textContent = s.nombre;
  return s;
}

async function api(ruta, opciones = {}) {
  const s = obtenerSesion();
  const cabeceras = { 'Content-Type': 'application/json' };
  if (s) cabeceras['Authorization'] = 'Bearer ' + s.token;
  const resp = await fetch(ruta, { ...opciones, headers: cabeceras });
  let cuerpo = null;
  try { cuerpo = await resp.json(); } catch (e) { /* respuesta sin cuerpo */ }
  if (resp.status === 401 && s) { cerrarSesion(); }
  if (!resp.ok) { throw new Error((cuerpo && cuerpo.error) || 'Error en la solicitud'); }
  return cuerpo;
}

function mostrarMensaje(id, texto, tipo) {
  const el = document.getElementById(id);
  el.textContent = texto;
  el.className = 'msg ' + tipo;
}

function soles(valor) { return 'S/ ' + Number(valor).toFixed(2); }
