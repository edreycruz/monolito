

1. Abrir una terminal y crear el directorio (en caso de ya tener el directorio, ingresar a) practicas-entregables
2. Ingresar al directorio creado en el paso anterior
3. Clonar el repositorio https://github.com/edreycruz/monolito.git
4. Abrir Eclipse o STS
5. Importar el proyecto employee-microservice como Proyecto Maven Existente
6. Importar el proyecto workstation-microservice como Proyecto Maven Existente
7. Abrir la clase /employee-microservice/src/main/java/com/baz/microservicio/employee/EmployeeMicroservice.java y Ejecutar como Aplicación Spring Boot
8. Abrir la clase /workstation-microservice/src/main/java/com/baz/microservicio/workstation/WorkstationMicroservice.javay Ejecutar como Aplicación Spring Boot
9. Abrir un Cliente SOAP/REST (ej. Boomerang)
10. Introducir el nombre del proyecto (ej. Monolito) y seleccionar Create a service
11. Seleccionar la opción REST e introducir un nombre para el servicio (ej. Practica) y seleccionar Add
12. En la pestaña Request1 cambiar el método GET por POST e introducir la URL http://localhost:9091/employees-microservice/employees/
13. En el cuerpo de la petición introducir {"firstName":"Aquiles","lastName":"Esquivel Madrazo"} y verificar que el formato de la petición sea JSON
14. Seleccionar Send
15. El servicio devolverá el JSON con el registro insertado
16. En la pestaña Request1 cambiar el método POST por GET
17. Seleccionar Send
18. El servicio devolverá el JSON con los registros de empleados en BD
19. En la pestaña Request1 cambiar el método GET por DELETE e introducir la URL http://localhost:9091/employees-microservice/employees/2
20. Seleccionar Send
21. El servicio devolverá el JSON con los registros que eliminó de la BD
22. En la pestaña Request1 cambiar el método DELETE por GET e introducir la URL http://localhost:9092/workstation-microservice/workstations
23. Seleccionar Send
24. El servicio devolverá el JSON con los registros de workstations registrados en BD
25. En la pestaña Request1 cambiar el método GET por PUT e introducir la URLhttp://localhost:9092/workstation-microservice/workstations/3
26. En el cuerpo de la petición introducir {"vendor": "Asus","model": "TP550LA","facilitiesSerialNumber":"777","employeeId":1} y verificar que el formato de la petición sea JSON
27. Seleccionar Send
28. El servicio devolverá el JSON con los registros de workstations actualizados en BD