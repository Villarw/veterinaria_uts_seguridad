# Veterinaria UTS

Sistema de informacion web para la gestion administrativa y clinica de una
veterinaria, que centraliza pacientes, propietarios, citas e historial medico.
Desarrollado con Spring Boot, Thymeleaf y MySQL.

## Tecnologias

- Java 17
- Spring Boot 4 (Web, Data JPA, Security)
- Thymeleaf + Bootstrap 5
- MySQL (XAMPP / phpMyAdmin)
- Spring Tool Suite (STS)
- BCrypt para cifrado de contrasenas

## Objetivo

Digitalizar y optimizar la atencion veterinaria mediante un sistema escalable
que mejore la calidad del servicio y garantice la integridad de los datos.

## Funcionalidades principales

- **Administrador**: gestiona medicos, clientes (activar/desactivar), mascotas,
  citas e historial.
- **Veterinario**: visualiza solo sus clientes y mascotas, agenda citas propias
  y registra diagnosticos.
- **Cliente**: consulta sus mascotas, anade nuevas, agenda citas con cualquier
  veterinario y revisa el historial medico.
- Activacion / desactivacion de clientes sin eliminacion fisica.

## Requisitos previos

- Java 17 o superior
- XAMPP (con MySQL corriendo en el puerto 3306)
- Spring Tool Suite (STS) o IntelliJ IDEA

## Instalacion y ejecucion rapida

1. Clona el repositorio
   git clone https://github.com/tuusuario/veterinaria_uts_seguridad.git

2. Crea la base de datos
   Abre phpMyAdmin (http://localhost/phpmyadmin) y ejecuta el script SQL
   completo que se encuentra en la carpeta docs/script.sql o en la seccion
   Script SQL del informe.

3. Configura application.properties (src/main/resources)
   Ajusta tu usuario y contrasena de MySQL si es necesario.

4. Ejecuta el proyecto
   En STS: clic derecho sobre el proyecto -> Run As -> Spring Boot App.

5. Accede al sistema
   Abre http://localhost:8080

## Credenciales de prueba

| Usuario      | Contrasena  | Rol           |
|--------------|-------------|---------------|
| admin_uts    | 12345       | Administrador |
| pablo_vet    | pablo2026   | Veterinario   |
| cliente1     | cliente123  | Cliente       |

## Informe completo

El documento con objetivos, justificacion, requerimientos y diagramas esta
disponible en docs/Informe_Veterinaria_UTS.pdf.

## Integrantes

- Maria Fernanda Gallo Serrano
- Gabriel Fernando Villar Suarez

Programa de Programacion Java - Unidades Tecnologicas de Santander, 2026.
