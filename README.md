# Sistema de gestión veterinaria

Aplicación de escritorio para administrar información de una veterinaria. Está desarrollada en Java con Swing y guarda los datos en una base de datos MySQL.

## Funcionalidades

- Registrar y consultar dueños y sus datos de contacto.
- Registrar mascotas y asociarlas con un dueño.
- Administrar veterinarios.
- Registrar consultas asociadas a una mascota y un veterinario.
- Registrar vacunas asociadas a una mascota y una consulta.

La interfaz principal permite abrir los formularios de cada módulo. Las pantallas de veterinarios, consultas y vacunas ofrecen operaciones para crear, listar, modificar y eliminar registros. Las operaciones disponibles para dueños y mascotas tienen las limitaciones indicadas más abajo.

## Tecnologías y dependencias

- Java 8 o posterior.
- MySQL Server. El proyecto está configurado para una instancia local, como la incluida en XAMPP.
- MySQL Connector/J 8.0.33: `lib/mysql-connector-j-8.0.33.jar`.
- FlatLaf: `lib/flatlaf.jar`.

El código fuente está en `src/`, las bibliotecas en `lib/` y la configuración de VS Code define `bin/` como carpeta de salida para las clases compiladas.

## Configuración de la base de datos

La conexión está definida en `src/conexion/Conexion.java` con estos valores predeterminados:

| Parámetro | Valor |
| --- | --- |
| Host y puerto | `localhost:3306` |
| Base de datos | `veterinaria` |
| Usuario | `root` |
| Contraseña | Vacía |

Antes de iniciar la aplicación:

1. Inicia MySQL y crea la base de datos `veterinaria`.
2. Crea las tablas y relaciones requeridas por la aplicación. **El repositorio no incluye un archivo SQL de creación del esquema.**
3. Si tu servidor usa otro host, puerto, base de datos o credenciales, actualiza las constantes de conexión en `Conexion.java`.

Los DAOs consultan las siguientes tablas y columnas. Cada tabla necesita una columna `id` compatible con la generación de claves de MySQL:

| Tabla | Columnas utilizadas |
| --- | --- |
| `dueño` | `id`, `nombre`, `ci`, `telefono`, `email` |
| `mascota` | `id`, `nombre`, `edad`, `especie`, `raza`, `genero`, `peso`, `id_dueño` |
| `veterinario` | `id`, `nombre`, `password`, `especialidad` |
| `consulta` | `id`, `procedimiento`, `fecha`, `hora`, `id_mascota`, `id_veterinario` |
| `vacuna` | `id`, `tipo`, `fecha`, `hora`, `vencimiento`, `id_mascota`, `id_consulta` |

Los campos `fecha` y `vencimiento` se manejan como fechas, y `hora` como hora. Las columnas de referencia (`id_dueño`, `id_mascota`, `id_veterinario` e `id_consulta`) deben ser compatibles con los identificadores de sus tablas relacionadas. La tabla `dueño` lleva `ñ` en su nombre; conserva exactamente ese nombre o adapta las consultas del código junto con el esquema.

## Compilar y ejecutar en Windows

Desde la raíz del proyecto, en PowerShell:

```powershell
$sources = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp "lib\*" -d bin $sources
java -cp "bin;lib\*" App
```

También se puede abrir la carpeta del proyecto en VS Code con la extensión **Extension Pack for Java**. La configuración existente compila desde `src/` hacia `bin/` y carga las bibliotecas JAR de `lib/`.

## Prueba de conexión

Con la base de datos creada y los parámetros configurados, se puede comprobar la conexión desde PowerShell:

```powershell
java -cp "bin;lib\*" conexion.Conexion
```

El programa imprime un mensaje de conexión correcta o el error informado por MySQL.

## Estructura del código

```text
src/
├── App.java                 # Punto de entrada
├── conexion/Conexion.java   # Conexión JDBC a MySQL
├── dao/                     # Consultas SQL y acceso a datos
├── modelo/                  # Entidades y clases de gestión
└── vista/                   # Ventanas y formularios Swing
lib/                         # FlatLaf y MySQL Connector/J
bin/                         # Clases compiladas
```

La capa `dao` ejecuta las consultas SQL; las clases de gestión de `modelo` conectan los formularios con los DAO, y `vista` contiene la interfaz Swing.

## Limitaciones conocidas

- No hay script SQL ni datos de ejemplo; el esquema debe prepararse por separado.
- La modificación de mascotas todavía no está implementada en la interfaz.
- La acción **Modificar** de dueños no actualiza el registro correctamente en la versión actual. Evita usarla hasta corregir el formulario y la operación de actualización del DAO.
- Las contraseñas de veterinarios se guardan y se leen desde la base de datos como texto ordinario. No se debe usar esta implementación para almacenar credenciales reales sin añadir almacenamiento seguro de contraseñas.
