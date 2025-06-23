-- Crear la base de datos si no exist

CREATE DATABASE IF NOT EXISTS PracticaFinalSdi

CHARACTER SET utf8mb

COLLATE utf8mb4_unicode_ci


-- Usar la base de dato

USE PracticaFinalSdiv


-- Tabla de usuario

DROP TABLE IF EXISTS users

CREATE TABLE IF NOT EXISTS users 

    user_id CHAR(36) NOT NULL PRIMARY KEY

    user_name VARCHAR(100) NOT NULL UNIQUE

    UUid VARCHAR(100) NOT NULL UNIQUE

    password VARCHAR(100) NOT NULL UNIQU

)


-- Tabla de coche

DROP TABLE IF EXISTS cars

CREATE TABLE IF NOT EXISTS cars (   

    car_id CHAR(36) NOT NULL PRIMARY KEY

    brand VARCHAR(50) NOT NULL

    model VARCHAR(50) NOT NULL

    license_plate VARCHAR(20) NOT NULL UNIQUE

    year INT NOT NUL

    -- Validar rango de año en Java, no en SQ

)


-- Relación de propiedad entre usuarios y coche

DROP TABLE IF EXISTS ownerships

CREATE TABLE IF NOT EXISTS ownerships 

    user_id CHAR(36) NOT NULL

    car_id CHAR(36) NOT NULL

    PRIMARY KEY (user_id, car_id)

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE

    FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE ON UPDATE CASCAD

)


-- Tabla de gastos asociados a los coche

DROP TABLE IF EXISTS expenses

CREATE TABLE IF NOT EXISTS expenses 

    expense_id CHAR(36) NOT NULL PRIMARY KEY

    car_id CHAR(36) NOT NULL

    expense_type ENUM('GASOLINE', 'REVIEW', 'ITV', 'OIL_CHANGE', 'OTHER') NOT NULL

    mileage INT NOT NULL

    date DATE NOT NULL

    amount DECIMAL(10,2) NOT NULL

    description VARCHAR(255)

    FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE ON UPDATE CASCAD

)


-- Consulta de prueba (opcional

SELECT * FROM cars

SELECT * FROM users
