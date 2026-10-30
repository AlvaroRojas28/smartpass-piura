-- Datos de prueba del piloto (no usar en produccion)
INSERT INTO perfil (id_perfil, nom_perfil) VALUES (1, 'Administrador'), (2, 'Cobrador')
ON CONFLICT (id_perfil) DO NOTHING;

INSERT INTO ruta (id_ruta, nom_ruta, origen, destino) VALUES (1, 'Ruta Piloto 01', 'Castilla', 'Centro de Piura')
ON CONFLICT (id_ruta) DO NOTHING;

INSERT INTO unidad (id_unidad, id_ruta, placa, nom_unidad, capacidad) VALUES (1, 1, 'ABC-123', 'Unidad 01', 30)
ON CONFLICT (id_unidad) DO NOTHING;

-- Claves de prueba: Admin123 (administrador) y Cobrador123 (cobrador)
INSERT INTO empleado (id_empleado, id_perfil, nom, ape_paterno, ape_materno, dni, clave) VALUES
    (1, 1, 'Administrador', 'Demo', 'Sol de Piura', '10000001', '$2b$10$bMQ1Ap8qmExDdKXebmZJv.AC0Bj05JejncRp2iYOuhvJifWkJBioi'),
    (2, 2, 'Cobrador', 'Demo', 'Sol de Piura', '10000002', '$2b$10$mqrw/qF1j1oiCpR7cLBtn.36MuItoGazeoX6jCjcqEkNlWvLYzgnO')
ON CONFLICT (id_empleado) DO NOTHING;

SELECT setval(pg_get_serial_sequence('perfil', 'id_perfil'), (SELECT MAX(id_perfil) FROM perfil));
SELECT setval(pg_get_serial_sequence('ruta', 'id_ruta'), (SELECT MAX(id_ruta) FROM ruta));
SELECT setval(pg_get_serial_sequence('unidad', 'id_unidad'), (SELECT MAX(id_unidad) FROM unidad));
SELECT setval(pg_get_serial_sequence('empleado', 'id_empleado'), (SELECT MAX(id_empleado) FROM empleado));
