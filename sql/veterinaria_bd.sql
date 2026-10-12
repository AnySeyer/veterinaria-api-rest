
-- ============================================================
-- SISTEMA DE GESTION DE ADOPCIONES Y SEGUIMIENTO VETERINARIO
-- Base de datos: Oracle
-- Esquema: VETERINARIA

-- 1. TABLA ADOPTANTES

CREATE TABLE adoptantes (
                            id_adoptante NUMBER(10) PRIMARY KEY,
                            nombre VARCHAR2(100) NOT NULL,
                            apellido VARCHAR2(100) NOT NULL,
                            telefono VARCHAR2(20),
                            correo VARCHAR2(150),
                            direccion VARCHAR2(250),
                            estado NUMBER(1) DEFAULT 1 NOT NULL,
                            fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Secuencia para generar identificadores de adoptantes
CREATE SEQUENCE seq_adoptantes
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 2. TABLA MASCOTAS

CREATE TABLE mascotas (
                          id_mascota NUMBER(10) PRIMARY KEY,
                          nombre VARCHAR2(100) NOT NULL,
                          especie VARCHAR2(50) NOT NULL,
                          raza VARCHAR2(100),
                          edad NUMBER(3),
                          sexo VARCHAR2(20),
                          fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          fecha_adopcion TIMESTAMP,
                          id_adoptante NUMBER(10),
                          estado NUMBER(1) DEFAULT 1 NOT NULL,

                          CONSTRAINT fk_mascotas_adoptantes
                              FOREIGN KEY (id_adoptante)
                                  REFERENCES adoptantes(id_adoptante)
);

-- Secuencia para generar identificadores de mascotas
CREATE SEQUENCE seq_mascotas
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 3. TABLA VACUNAS

CREATE TABLE vacunas (
                         id_vacuna NUMBER(10) PRIMARY KEY,
                         nombre_vacuna VARCHAR2(100) NOT NULL,
                         descripcion VARCHAR2(250),
                         fecha_aplicacion TIMESTAMP NOT NULL,
                         proxima_aplicacion TIMESTAMP,
                         id_mascota NUMBER(10) NOT NULL,
                         estado NUMBER(1) DEFAULT 1 NOT NULL,

                         CONSTRAINT fk_vacunas_mascotas
                             FOREIGN KEY (id_mascota)
                                 REFERENCES mascotas(id_mascota)
);

-- Secuencia para generar identificadores de vacunas
CREATE SEQUENCE seq_vacunas
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 4. TABLA CHIP_RASTREO

CREATE TABLE chip_rastreo (
                              id_chip NUMBER(10) PRIMARY KEY,
                              codigo_chip VARCHAR2(100) NOT NULL,
                              fecha_implantacion TIMESTAMP,
                              fabricante VARCHAR2(100),
                              id_mascota NUMBER(10) NOT NULL,
                              estado NUMBER(1) DEFAULT 1 NOT NULL,

                              CONSTRAINT fk_chip_mascota
                                  FOREIGN KEY (id_mascota)
                                      REFERENCES mascotas(id_mascota),

                              CONSTRAINT uq_codigo_chip
                                  UNIQUE (codigo_chip)
);

-- Secuencia para generar identificadores de chips
CREATE SEQUENCE seq_chip_rastreo
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 5. TABLA CITA_SEGUIMIENTO

CREATE TABLE cita_seguimiento (
                                  id_cita NUMBER(10) PRIMARY KEY,
                                  id_mascota NUMBER(10) NOT NULL,
                                  fecha_cita TIMESTAMP NOT NULL,
                                  motivo VARCHAR2(250) NOT NULL,
                                  observaciones VARCHAR2(500),
                                  estatus VARCHAR2(30) DEFAULT 'PENDIENTE' NOT NULL,
                                  estado NUMBER(1) DEFAULT 1 NOT NULL,

                                  CONSTRAINT fk_cita_mascota
                                      FOREIGN KEY (id_mascota)
                                          REFERENCES mascotas(id_mascota)
);

-- Secuencia para generar identificadores de citas
CREATE SEQUENCE seq_cita_seguimiento
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;



-- ============================================================
-- 6. INSERTS DE PRUEBA
--  REGISTRAR ADOPTANTES

INSERT INTO adoptantes ( id_adoptante,nombre,apellido,telefono,correo,direccion,estado,fecha_registro)
VALUES (seq_adoptantes.NEXTVAL,'Laura','Martinez','8441234567','lauramartinez@gmail.com',
           'Colonia Centro',1,CURRENT_TIMESTAMP);

-- INSERT DE PRUEBA: MASCOTAS
INSERT INTO mascotas (id_mascota, nombre, especie, raza, edad, sexo, fecha_ingreso, fecha_adopcion, id_adoptante, estado)
VALUES (seq_mascotas.NEXTVAL, 'Max', 'Perro', 'Golden Retriever', 3, 'Macho',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seq_adoptantes.CURRVAL, 1);


-- INSERT DE PRUEBA: VACUNAS
INSERT INTO vacunas (id_vacuna, nombre_vacuna, descripcion, fecha_aplicacion, proxima_aplicacion, id_mascota, estado)
VALUES (seq_vacunas.NEXTVAL, 'Antirrabica', 'Vacuna contra la rabia',
        CURRENT_TIMESTAMP, ADD_MONTHS(CURRENT_TIMESTAMP, 12), seq_mascotas.CURRVAL, 1);


-- INSERT DE PRUEBA: CHIP_RASTREO
INSERT INTO chip_rastreo (id_chip, codigo_chip, fecha_implantacion, fabricante, id_mascota, estado)
VALUES (seq_chip_rastreo.NEXTVAL, 'CHIP-PRUEBA-001', CURRENT_TIMESTAMP, 'PetChip', seq_mascotas.CURRVAL, 1);


-- INSERT DE PRUEBA: CITA_SEGUIMIENTO
INSERT INTO cita_seguimiento (id_cita, id_mascota, fecha_cita, motivo, observaciones, estatus, estado)
VALUES (seq_cita_seguimiento.NEXTVAL, seq_mascotas.CURRVAL, CURRENT_TIMESTAMP + INTERVAL '7' DAY,
        'Revision veterinaria', 'Seguimiento general de la mascota', 'PENDIENTE', 1);

-- Confirmar los datos insertados
COMMIT;
