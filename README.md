# HelpDesk - Gestión de Incidencias

Aplicación de consola en Java para registrar, consultar, cerrar y conservar las incidencias del departamento de informática. Desarrollado por Ander Chans Rodríguez para el módulo de Desarrollo Web en Entorno Servidor .

## Instrucciones de Ejecución

1. **Requisitos previos:** Asegúrate de tener instalado **Java JDK** y **Maven**.
2. **Ejecutar la aplicación:** 
   * Desde la terminal, en la raíz del proyecto, ejecuta: mvn clean compile exec:java
   * Alternativamente, puedes ejecutar la clase principal AplicacionHelpDesk.java directamente desde tu entorno de desarrollo (como IntelliJ IDEA).
3. **Ejecutar las pruebas automáticas:** 
   * Para lanzar los tests de JUnit, utiliza el comando: mvn test.

## Responsabilidades de las Clases

El código está organizado dividiendo las responsabilidades para mantener un diseño limpio y encapsulado:

* **Ticket**: Gestiona los datos, la validación y el estado de una incidencia individual.
* **GestorTickets**: Administra la colección de incidencias, genera los identificadores automáticos y consecutivos, realiza las búsquedas y calcula las estadísticas.
* **ArchivoTickets**: Se encarga exclusivamente de la persistencia, realizando la lectura y escritura de las incidencias en el archivo de texto.
* **AplicacionHelpDesk**: Controla el menú principal, la lectura del teclado con Scanner, muestra los mensajes por consola y coordina las operaciones.

## Limitaciones Conocidas

* **Guardado manual:** La aplicación no realiza un guardado automático tras cada operación. Es obligatorio utilizar la opción del menú "Guardar incidencias" antes de salir del programa para no perder los datos generados durante la sesión.
* **Carga estricta:** Si el archivo tickets.txt contiene datos inválidos o identificadores repetidos, la aplicación informa del error y detiene su arranque. No se aceptan cargas parciales de datos.
