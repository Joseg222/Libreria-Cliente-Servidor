Proyecto Final – Programación Cliente/Servidor Concurrente
Librería Cliente-Servidor

Descripción
Aplicación de escritorio en Java que permite la gestión de publicaciones de una librería: libros, revistas y ebooks.
El sistema utiliza sockets para la comunicación cliente-servidor y mantiene los datos de las publicaciones en memoria.
Se permite agregar publicaciones, listar todas y buscar por título mediante un interfaz gráfico moderno.

Tecnologías

Java 17+ (JDK)

Swing (GUI)

Sockets TCP (cliente-servidor concurrente)

Colecciones de Java (ArrayList)

ExecutorService para manejo de múltiples clientes

Estructura de clases principales

Clase	Función
Servidor	Escucha conexiones de clientes y asigna cada cliente a un hilo (ClientHandler)
ClientHandler	Maneja los comandos enviados por los clientes: ADD, LIST, SEARCH, EXIT
ClienteGUI	Interfaz gráfica que permite agregar/listar/buscar publicaciones y conectarse al servidor
Publicacion	Clase base para libros, revistas y ebooks
Libro, Revista, EBook	Extienden Publicacion con atributos específicos (páginas, número de revista, tamaño, etc.)
Cómo ejecutar

Ejecutar el servidor

Abrir Servidor.java y ejecutarlo.

El servidor escuchará en puerto 5555 y podrá manejar varios clientes concurrentes.

Ejecutar el cliente

Abrir ClienteGUI.java y ejecutarlo.

Se conectará automáticamente al servidor en 127.0.0.1:5555.

La interfaz permite:

Listar publicaciones

Agregar Libro, Revista o EBook

Buscar publicaciones por título

Salir (cerrando la conexión)

Funcionalidades

Agregar publicaciones:

Se ingresan datos por cuadros de diálogo (JOptionPane).

Los libros incluyen: título, autor, año, ISBN y número de páginas.

Las revistas incluyen: título, autor, año y número de edición.

Los ebooks incluyen: título, autor, año, formato y tamaño en MB.

Listar publicaciones:

Se muestra en un JTextArea dentro de un JScrollPane para ver todas las publicaciones agregadas.

Buscar publicaciones:

Permite buscar por título (parcial o completo) y devuelve coincidencias en un área de texto.

Concurrencia:

Se pueden conectar varios clientes al mismo tiempo.

Cada cliente es manejado en un hilo independiente con ExecutorService.

Requisitos

JDK 17+

IDE Java (NetBeans recomendado, pero no obligatorio)

No requiere base de datos externa (los datos se mantienen en memoria)

Capturas / Ejemplo de uso

Menú principal del ClienteGUI con botones organizados y colores modernos.

Agregar un libro y recibir confirmación "Libro agregado correctamente".

Listar todas las publicaciones agregadas.

Buscar un título específico y mostrar resultados.

Video de presentación

Mostrar ejecución del Servidor y conexión de ClienteGUI.

Demostración de agregar, listar y buscar publicaciones.

Explicación de la estructura de clases y flujo de ejecución.

Comentar retos y lecciones aprendidas (GUI, concurrencia, manejo de objetos).

Autor: Jose Delgado – Curso Programación Cliente/Servidor Concurrente
