# SmartPass Piura

Sistema piloto web de pago digital para el transporte público urbano de la Empresa de Transporte Sol de Piura.

## Organización del proyecto

```text
SmartPass-Piura/
├── BackEnd/
│   ├── src/main/java/pe/smartpass/
│   │   ├── config/          # Configuración de Spring
│   │   ├── controller/      # Endpoints REST
│   │   ├── dao/             # Acceso a datos
│   │   ├── exception/       # Manejo de excepciones
│   │   ├── model/           # Entidades y modelos
│   │   ├── security/        # JWT e interceptores
│   │   └── service/         # Lógica de negocio
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── src/test/java/       # Pruebas unitarias
│   └── pom.xml
├── FrontEnd/
│   ├── css/                 # Hojas de estilo
│   ├── js/                  # JavaScript y consumo de API
│   ├── index.html           # Inicio de sesión
│   ├── registro.html        # Registro de pasajero
│   ├── pasajero.html        # Portal del pasajero
│   ├── validar.html         # Validación QR
│   └── admin.html           # Panel administrativo
├── Base de Datos/
│   ├── schema.sql           # Estructura de tablas
│   └── data.sql             # Datos iniciales / demostración
└── Documentacion/
    └── diagramas/           # PlantUML y exportaciones PNG
```

## Tecnologías

- **Backend:** Java 17, Spring Boot 3, Spring JDBC, Maven y JWT.
- **Frontend:** HTML5, CSS3 y JavaScript.
- **Base de datos:** PostgreSQL 14 o superior.

El frontend y los scripts SQL se mantienen en carpetas independientes. Durante la compilación, Maven los incorpora al paquete de Spring Boot como recursos estáticos (`/`) y scripts de inicialización (`classpath:db/`). Así se conserva el despliegue integrado y no es necesario duplicar archivos.

## Ejecución local

1. Instala Java 17, Maven 3.9+ y PostgreSQL.
2. Crea la base de datos:

   ```sql
   CREATE DATABASE smartpass;
   ```

3. Configura las variables de entorno si tus credenciales son distintas a las locales:

   - `DB_URL` (por defecto `jdbc:postgresql://localhost:5432/smartpass`)
   - `DB_USER` (por defecto `postgres`)
   - `DB_PASSWORD` (por defecto `postgres`)
   - `JWT_SECRET` (mínimo 32 caracteres)
   - `TARIFA_PASAJE` (por defecto `1.80`)

4. Abre una terminal dentro de `BackEnd/` y ejecuta:

   ```bash
   mvn spring-boot:run
   ```

5. Ingresa a <http://localhost:8080>.

Para generar el JAR:

```bash
mvn clean package
java -jar target/smartpass-piura-0.2.0.jar
```

## Usuarios de demostración

| Rol | DNI | Clave |
|---|---|---|
| Administrador | 10000001 | Admin123 |
| Cobrador | 10000002 | Cobrador123 |
| Pasajero | Se registra desde la web | La que elija |

Los datos de ruta, unidad y empleados son de ejemplo. Cambia las claves y el secreto JWT antes de cualquier uso real.

## Funcionalidades

- Registro e inicio de sesión de pasajeros.
- Recarga de saldo simulada.
- Generación y visualización de código QR.
- Validación de pagos por parte del cobrador.
- Historial de transacciones.
- Reportes administrativos de recaudación.

## Pruebas

Desde `BackEnd/`:

```bash
mvn test
```

## Nota

Docker y sus archivos de configuración se retiraron de esta versión. El proyecto se ejecuta localmente con Maven y PostgreSQL, sin contenedores.
