CREATE DATABASE IF NOT EXISTS inventario_itca;
USE inventario_itca;

-- Tabla principal de inventario
CREATE TABLE inventario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero_registro VARCHAR(10),
  nombre VARCHAR(150) NOT NULL,
  codigo VARCHAR(50),
  cantidad_total INT DEFAULT 0,
  cantidad_utilizada INT DEFAULT 0,
  cantidad_actual INT DEFAULT 0,
  tipo VARCHAR(100),
  area_encargada VARCHAR(100),
  fecha_vencimiento DATE NULL,
  observacion TEXT,
  ubicacion ENUM('Recursos oficina','Consumibles oficina','Taller cocina','Bodega 1','Bodega 2')
);

-- Tabla de categorías
CREATE TABLE categorias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT
);

-- Tabla de movimientos (entradas y salidas de inventario)
CREATE TABLE movimientos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_inventario INT NOT NULL,
  tipo ENUM('Entrada', 'Salida', 'Ajuste') NOT NULL,
  cantidad INT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  responsable VARCHAR(100),
  observacion TEXT,
  FOREIGN KEY (id_inventario) REFERENCES inventario(id)
);

-- Tabla de usuarios del sistema
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  usuario VARCHAR(50) UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  rol ENUM('Administrador', 'Encargado', 'Docente') DEFAULT 'Encargado'
);


-- Crear tabla para Tipos
CREATE TABLE tipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

-- Crear tabla para Áreas Encargadas
CREATE TABLE areas_encargadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

-- Crear tabla para Ubicaciones
CREATE TABLE ubicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

-- Agregar nuevas columnas de clave foránea a la tabla inventario
ALTER TABLE inventario 
ADD COLUMN tipo_id INT,
ADD COLUMN area_encargada_id INT,
ADD COLUMN ubicacion_id INT;

-- Agregar restricciones de clave foránea
ALTER TABLE inventario 
ADD CONSTRAINT fk_inventario_tipo FOREIGN KEY (tipo_id) REFERENCES tipos(id),
ADD CONSTRAINT fk_inventario_area_encargada FOREIGN KEY (area_encargada_id) REFERENCES areas_encargadas(id),
ADD CONSTRAINT fk_inventario_ubicacion FOREIGN KEY (ubicacion_id) REFERENCES ubicaciones(id);