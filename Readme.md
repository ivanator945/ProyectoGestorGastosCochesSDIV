# Aplicaci-n-de-Control-de-Gastos-de-Coches
# Como armar el proyecto
Para poder ejecutar correctamente esta aplicación hay que tener:

IDE Eclipse (recomendable la versión profesional)

Tener instalado un descompresor de archivos como Winrar

Tener JDK (en este proyecto se está usando el JDK 22) instalado en el ordenador para poder desarrollar la aplicación

Tener las librerías de MySQL Connectors para que pueda utilizarse la base de datos sin problema (enlace aquí). Para este proyecto se está usando la versión 9.3

Tener instalada alguna herramienta de diseño de bases de datos como MySQL Workbench por si se quiere comprobar que el script PracticaFinalRFFG.sql que crea la base de datos, funciona correctamente

El archivo .env se usa para gestionar las credenciales y configuración de la base de datos. Este archivo viene con las credenciales vacías para que se rellenen con la información de la Base de Datos que se vaya a utilizar. Así también se evitan problemas de seguridad.

El archivo se encuentra en dentro de las carpetas: Control-Gastos-Coches

```

    DB_HOST= enlace a la ubicación de la BBD

    DB_PORT= Puerto utilizado por la BBD

    DB_USERNAME= usuario de la BBD

    DB_PASSWORD= contraseña

    DB_DATABASE= nombre de la BBD

```
- ### INICIALIZACIÓN DE LA APLICACIÓN


 - Crear la base de datos ejecutando el script dntro de mysql y añadir el la contraseña la puedes cambiar al PracticaRoot

 ```

    private static final String URL = 	"jdbc:mysql://localhost:3306/PracticaFinalSdiv";
    private static final String USER = "root";
    private static final String PASS = "NuevaContra123";

```


- Añadir en el classpath todo lo que necesitamos q es ``jasypt-1.9.3.jar``, ``mysql-connector-j-9.2.0.jar``

 - Ejecutar ``Main.java`` _dentro de la carpeta ControlDeGastosMain

 - Aparecerá una pantalla de Bienvenida con 2 opciones: **Iniciar Sesión** e **Registrarse**
 - Al iniciar sesión el usuario irá a su perfil personal donde podrá ver varias opciones
 - Al registrarte simplemente te pedirán un nombre de usuario y una contraseña, tras el registro te indicara que se registro correctamente y luego para poder acceder a la aplicación tendras que darle a iniciar sesion
    



  
   


# # Objetivo

- Desarrollar una solución funcional y mantenible para la gestión de vehículos y sus gastos


- Aplicar el patrón MVC como base de la estructura del código


- Utilizar UUIDs para identificar usuarios y facilitar la gestión segura de relaciones


- Implementar relaciones de muchos a muchos entre usuarios y coches


- Permitir la filtración avanzada de gastos mediante múltiples criterios


# # Funcionalidades Principale


   - Gestión de Usuario

    

-- Registro con nombre único y asignación automática de UUID


-- Inicio de sesión (login) validado por nombre de usuario


-- Control de errores para entradas inválidas o duplicadas


   - Gestión de Coche

    

-- Creación de vehículos con los atributos: marca, modelo, matrícula y año


-- El usuario que crea el coche se convierte en propietario


-- Posibilidad de añadir más propietarios conociendo su UUID


-- Relación muchos a muchos entre usuarios y coches


   - Gestión de Gasto

  

-- Añadir gastos a coches donde el usuario es propietario


-- Tipos de gasto predefinidos: Gasolina, Revisión, ITV, Cambio de aceite, Otros


-- Campos obligatorios:

      - Tipo de gasto (seleccionable

      - Kilometrage

      - Fech

      - Import


-- Campo opcional: descripción del gasto


-- Visualización en forma de tabla de gastos por coche


-- Filtros aplicables

      - Añ

      - Rango de fecha

      - Rango de kilometraj

      - Opción “Todos” para mostrar todos los resultado


- Arquitectura del Proyect


	src
	
	├── Base_de_datos
	│   └──PracticaFinalCoches
	├── ControlDeGastosMain
	│   └── Main
	├── controller
	│    ├── CarController
	│    ├── ExpenseController
	│    └── UserController
	├── model
	│   ├── CarDAO
	│   ├── ExpenseDAO
	│   ├── OwnerShipDAO
	│   └── UserDAO
	├── ModelSentities
	│   ├── Car
	│   ├── Expense
	│   └── User
	├── Utils
	│   ├── DBCoonection
	│   ├── PaswirdUtil
	│   └── UUUIUtil
	├── Views
	│   ├── CarView
	│	├── ConsoleView
	│	├── GastosView
	│   └── UserView
	└── Main.jav


- Descripción de Carpetas


-`controller`: Lógica de negocio y coordinación entre vista y modelo

-`model`: Clases de dominio, DAOs y enumeraciones (tipos de gasto)

-`utils`: Clases de utilidad como la conexión a base de datos

-`view`: Interacción con el usuario (consola)

-`Main`: Punto de entrada de la aplicación





