
-- V2__insert_data.sql

INSERT INTO roles (NOMBRE_ROL) VALUES
('Administrador'),
('Paciente'),
('Domiciliario');

INSERT INTO privilegios (DESCRIPCION_PRIVILEGIOS) VALUES
('SELECT'),
('UPDATE'),
('INSERT'),
('DELETE');

INSERT INTO medicamentos (NOMBRE_MEDICAMENTOS, DESCRIPCION_MEDICAMENTOS, IMAGEN_MEDICAMENTO) VALUES
('Aspirina', 'Analgésico', ''),
('Ibuprofeno', 'Antiinflamatorio', ''),
('Paracetamol', 'Antipirético', ''),
('Omeprazol', 'Antiácido', ''),
('Azitromicina', 'Antibiótico', ''),
('Ciprofloxina', 'Antibiótico', ''),
('Metformina', 'Antidiabético', ''),
('Atrovastatina', 'Hipolipemiante', ''),
('Fexofedina', 'Antialérgico', ''),
('Dexametasona', 'Corticoide', '');

INSERT INTO usuarios (NOMBRE_USUARIOS, TIPODOC_USUARIOS, NUMDOC_USUARIOS, DIRECCION_USUARIOS, ESTADO_USUARIO, TELEFONO_USUARIOS, CORREO_USUARIOS, CONTRASEÑA_USUARIOS, ID_ROLES_USUARIOS) VALUES
('Mariana Barreto', 'CC', '1612950689', 'Calle 10 #12-45', 'INACTIVO', '300-123-4567', 'Mariana.Barreto@gmail.com', '1612950689mb', 1),
('Juan Torres', 'CC', '1827842630', 'Avenida 5 #67-89', 'INACTIVO', '301-234-5678', 'Juan.Camilo.Torres@gmail.com', '1827842630jt', 1);

INSERT INTO inventario (FECHA_ENTRADA_INVENTARIO, FECHA_SALIDA_INVENTARIO, ID_MEDICAMENTOS, STOCK_INVENTARIO, VENCIMIENTOMED_INVENTARIO, ESTADOMED_INVENTARIO) VALUES
('2024-08-01 15:37:49', '2024-11-01 15:38:07', 1, 24, '2026-06-01', 'ACTIVO');

INSERT INTO vehiculo (color_vehiculo, marca_vehiculo, placa_vehiculo, tipo_vehiculo, estado_vehiculo, PROPIETARIO_USUARIOS) VALUES
('Rojo', 'Chevrolet', 'ABC-123', 'Carro', 'ACTIVO', 1);

INSERT INTO ordenes (FECHA_ENTREGA, eps_orden, estado_orden, USUARIOS_PACIENTE, CANTIDADMED_ORDEN, direccion_orden, telefono_orden) VALUES
('2025-07-21 18:59:00', 'Nueva eps', 'ACTIVO', 1, 4, 'calle 72 #23-345', '2147483647');

INSERT INTO ordenes_has_medicamentos (ID_ORDENES, ID_MEDICAMENTOS) VALUES
(1, 1);

INSERT INTO domicilio (ubicacion_domicilio, estado_domicilio, FECHA_ENTREGA_DOMICILIO, ID_VEHICULO, ID_ORDENES, FORMULA_MEDICA, eps_domicilio, telefono_domicilio) VALUES
('Calle 45 #67-89', 'EN ESPERA', '2025-07-22 10:00:00', 1, 1, 'Ninguna', 'SURA', '3000000000');

INSERT INTO usuarios_has_privilegios (ID_USUARIOS, ID_PRIVILEGIOS) VALUES
(1, 1),
(1, 2);

INSERT INTO pqrs (TIPO_SOLICITUD, motivo_pqrs, ID_USUARIOS, FECHA_PQRS, tipo_pqrs) VALUES
('Queja', 'Demora en domicilio', 1, '2025-07-01 10:00:00', 'Demora');
