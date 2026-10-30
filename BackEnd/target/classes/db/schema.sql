-- SmartPass Piura - Modelo fisico (PostgreSQL). Script idempotente.
CREATE TABLE IF NOT EXISTS perfil (
    id_perfil   SERIAL PRIMARY KEY,
    nom_perfil  VARCHAR(45) NOT NULL,
    estado      SMALLINT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS ruta (
    id_ruta   SERIAL PRIMARY KEY,
    nom_ruta  VARCHAR(60) NOT NULL,
    origen    VARCHAR(60) NOT NULL,
    destino   VARCHAR(60) NOT NULL,
    estado    SMALLINT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS unidad (
    id_unidad   SERIAL PRIMARY KEY,
    id_ruta     INTEGER NOT NULL REFERENCES ruta (id_ruta),
    placa       VARCHAR(10) NOT NULL UNIQUE,
    nom_unidad  VARCHAR(45) NOT NULL,
    capacidad   SMALLINT NOT NULL,
    estado      SMALLINT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS empleado (
    id_empleado       SERIAL PRIMARY KEY,
    id_perfil         INTEGER NOT NULL REFERENCES perfil (id_perfil),
    nom               VARCHAR(55) NOT NULL,
    ape_paterno       VARCHAR(45) NOT NULL,
    ape_materno       VARCHAR(45) NOT NULL,
    dni               VARCHAR(8) NOT NULL UNIQUE,
    clave             VARCHAR(150) NOT NULL,
    fec_nac           DATE,
    direccion         VARCHAR(150),
    estado            SMALLINT NOT NULL DEFAULT 1,
    fec_creacion      TIMESTAMP NOT NULL DEFAULT now(),
    fec_modificacion  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario        SERIAL PRIMARY KEY,
    nom_usuario       VARCHAR(45) NOT NULL,
    ape_paterno       VARCHAR(45) NOT NULL,
    ape_materno       VARCHAR(45) NOT NULL,
    dni               VARCHAR(8) NOT NULL UNIQUE,
    celular           VARCHAR(9) NOT NULL,
    clave             VARCHAR(150) NOT NULL,
    saldo_actual      NUMERIC(8,2) NOT NULL DEFAULT 0 CHECK (saldo_actual >= 0),
    codigo_qr         VARCHAR(150) UNIQUE,
    estado            SMALLINT NOT NULL DEFAULT 1,
    fec_creacion      TIMESTAMP NOT NULL DEFAULT now(),
    fec_modificacion  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recarga (
    id_recarga       SERIAL PRIMARY KEY,
    id_usuario       INTEGER NOT NULL REFERENCES usuario (id_usuario),
    monto            NUMERIC(8,2) NOT NULL CHECK (monto > 0),
    metodo_simulado  VARCHAR(30) NOT NULL,
    estado           SMALLINT NOT NULL DEFAULT 1,
    fec_recarga      TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS transaccion (
    id_transaccion    SERIAL PRIMARY KEY,
    id_usuario        INTEGER NOT NULL REFERENCES usuario (id_usuario),
    id_empleado       INTEGER NOT NULL REFERENCES empleado (id_empleado),
    id_unidad         INTEGER NOT NULL REFERENCES unidad (id_unidad),
    monto             NUMERIC(8,2) NOT NULL,
    fec_transaccion   DATE NOT NULL DEFAULT CURRENT_DATE,
    hora_transaccion  TIME NOT NULL DEFAULT CURRENT_TIME,
    estado            SMALLINT NOT NULL DEFAULT 1
);

-- Indices para las consultas del historial y de los reportes
CREATE INDEX IF NOT EXISTS idx_transaccion_usuario ON transaccion (id_usuario);
CREATE INDEX IF NOT EXISTS idx_transaccion_fecha   ON transaccion (fec_transaccion);
CREATE INDEX IF NOT EXISTS idx_recarga_usuario     ON recarga (id_usuario);
