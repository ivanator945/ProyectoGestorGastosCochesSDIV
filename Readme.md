# Aplicaci-n-de-Control-de-Gastos-de-Coches-SDiv

# # Descripción General:
Este proyecto consiste en el desarrollo de una aplicación de consola en Java que permite a múltiples usuarios gestionar los gastos asociados a coches, tanto propios como compartidos, cumpliendo los principios de diseño de software y organización modular mediante el patrón Modelo-Vista-Controlador (MVC).

Los datos se almacenan en una base de datos MySQL y se accede a ellos utilizando JDBC, con una arquitectura pensada para escalabilidad y claridad en la separación de responsabilidades.

# # Objetivos
- Desarrollar una solución funcional y mantenible para la gestión de vehículos y sus gastos.

- Aplicar el patrón MVC como base de la estructura del código.

- Utilizar UUIDs para identificar usuarios y facilitar la gestión segura de relaciones.

- Implementar relaciones de muchos a muchos entre usuarios y coches.

- Permitir la filtración avanzada de gastos mediante múltiples criterios.

# # Funcionalidades Principales

   - Gestión de Usuarios
     
-- Registro con nombre único y asignación automática de UUID.

-- Inicio de sesión (login) validado por nombre de usuario.

-- Control de errores para entradas inválidas o duplicadas.

   - Gestión de Coches
     
-- Creación de vehículos con los atributos: marca, modelo, matrícula y año.

-- El usuario que crea el coche se convierte en propietario.

-- Posibilidad de añadir más propietarios conociendo su UUID.

-- Relación muchos a muchos entre usuarios y coches.

   - Gestión de Gastos
   
-- Añadir gastos a coches donde el usuario es propietario.

-- Tipos de gasto predefinidos: Gasolina, Revisión, ITV, Cambio de aceite, Otros.

-- Campos obligatorios: 
      - Tipo de gasto (seleccionable)
      - Kilometraje
      - Fecha
      - Importe

-- Campo opcional: descripción del gasto.

-- Visualización en forma de tabla de gastos por coche.

-- Filtros aplicables:
      - Año
      - Rango de fechas
      - Rango de kilometraje
      - Opción “Todos” para mostrar todos los resultados

- Arquitectura del Proyecto

src/
├── ControlDeGastosMain/
│   └── Main/
├── controller/
│    ├── CarController/
│    ├── ExpenseController/
│    └── UserController/
├── model/
│   ├── CarDAO/
│   ├── ExpenseDAO/
│   ├── OwnerShipDAO/
│   └── UserDAO/
├── ModelSentities/
│   ├── Car/
│   ├── Expense/
│   └── User/
├── Utils/
│   ├── DBCoonection/
│   ├── PaswirdUtil/
│   └── UUUIUtil/
├── Views/
│   ├── CarView/
│	├── ConsoleView/
│	├── GastosView/
│   └── UserView/
└── Main.java

- Descripción de Carpetas:

-`controller`: Lógica de negocio y coordinación entre vista y modelo.
-`model`: Clases de dominio, DAOs y enumeraciones (tipos de gasto).
-`utils`: Clases de utilidad como la conexión a base de datos.
-`view`: Interacción con el usuario (consola).
-`Main`: Punto de entrada de la aplicación.

 Base de Datos
La base de datos se gestiona con MySQL. Se proporcionan scripts `.sql` para:

Crear las tablas:
users (UUID, nombre)
cars (ID, marca, modelo, matrícula, año)
user_car (UUID_usuario, ID_coche) — relación M:N
expenses (ID, tipo, fecha, importe, km, descripción, coche_id)
Insertar datos iniciales si es necesario.

 Seguridad y Gestión de Errores
Validaciones en consola para evitar entradas inválidas.

Gestión de errores en conexiones JDBC.

Comprobaciones de permisos antes de ejecutar acciones sobre coches o gastos (solo los propietarios pueden modificarlos).

Mensajes de error claros para el usuario final.


- Tecnologías Utilizadas
 -- Lenguaje: Java

 -- Base de datos: MySQL

 -- Conectividad: JDBC

 -- Control de versiones: Git

 -- Patrón de diseño: MVC

 -- Identificación única: UUID


bbss:
-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS PracticaFinalSdiv
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE PracticaFinalSdiv;

-- Tabla de usuarios
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    user_id CHAR(36) NOT NULL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    UUid VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL UNIQUE
);

-- Tabla de coches
DROP TABLE IF EXISTS cars;
CREATE TABLE IF NOT EXISTS cars (    
    car_id CHAR(36) NOT NULL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    year INT NOT NULL
    -- Validar rango de año en Java, no en SQL
);

-- Relación de propiedad entre usuarios y coches
DROP TABLE IF EXISTS ownerships;
CREATE TABLE IF NOT EXISTS ownerships (
    user_id CHAR(36) NOT NULL,
    car_id CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, car_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de gastos asociados a los coches
DROP TABLE IF EXISTS expenses;
CREATE TABLE IF NOT EXISTS expenses (
    expense_id CHAR(36) NOT NULL PRIMARY KEY,
    car_id CHAR(36) NOT NULL,
    expense_type ENUM('GASOLINE', 'REVIEW', 'ITV', 'OIL_CHANGE', 'OTHER') NOT NULL,
    mileage INT NOT NULL,
    date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Consulta de prueba (opcional)
SELECT * FROM cars;
SELECT * FROM users;
