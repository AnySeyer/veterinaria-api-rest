# 🐾 Sistema de Gestión de Adopciones y Seguimiento Veterinario

## 📋 Descripción del proyecto

API REST desarrollada con Java 21, Spring Boot y Oracle para gestionar las adopciones de mascotas y su seguimiento veterinario.

El sistema permite administrar adoptantes, mascotas, vacunas, chips de rastreo y citas de seguimiento, aplicando validaciones de negocio y eliminación lógica.

## 🛠️ Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Oracle Database
- Maven
- Postman
- IntelliJ IDEA
- Git y GitHub

## 🏗️ Arquitectura del proyecto

El sistema utiliza una arquitectura en capas:

- **Controller:** recibe y procesa las solicitudes HTTP.
- **Service:** contiene la lógica de negocio y las validaciones.
- **Repository:** gestiona el acceso a la base de datos.
- **Model:** representa las entidades del sistema.
- **DTO:** organiza los datos utilizados en las solicitudes y respuestas.
- **Exception:** centraliza el manejo de errores.
- **Util:** contiene funciones auxiliares.

## 🐶 Entidades principales

1. Adoptante
2. Mascota
3. Vacuna
4. ChipRastreo
5. CitaSeguimiento

## ⚙️ Funcionalidades principales

- Registro, consulta y actualización de adoptantes.
- Eliminación lógica de adoptantes.
- Registro de mascotas y asignación de adoptantes.
- Registro y consulta del historial de vacunación.
- Cálculo automático de la próxima fecha de vacunación.
- Registro de chips de rastreo.
- Programación y cancelación de citas veterinarias.
- Validación del adoptante antes de programar una cita.
- Validación del esquema de vacunación.
- Reporte consolidado de mascotas vacunadas.

## 📊 Reporte consolidado

El sistema incluye el endpoint:

`GET /api/reportes/mascotas-vacunadas`

Este permite consultar las mascotas con vacunas registradas, organizadas por especie e incluyendo la cantidad de vacunas aplicadas.

## 🗄️ Base de datos

El proyecto utiliza Oracle Database.

El script SQL se encuentra en la carpeta `sql` del repositorio e incluye la creación de tablas, secuencias, relaciones y registros de prueba.


## 🚀 Ejecución del proyecto

1. Contar con Java 21, Maven y Oracle Database.
2. Clonar el repositorio.
3. Ejecutar el script SQL ubicado en `sql`.
4. Configurar la conexión a Oracle en `application.properties`.
5. Definir la variable de entorno `DB_PASSWORD` con la contraseña de la base de datos.
6. Ejecutar la aplicación Spring Boot.

La API estará disponible en:

`http://localhost:8080`

## 📁 Repositorio

https://github.com/AnySeyer/veterinaria-api-rest

## 📌 Estado del proyecto

Backend funcional y documentado. La incorporación de una interfaz de usuario se contempla como una posible mejora futura.
