# Feedback3
 
Link al repositorio: https://github.com/siraglez/Feedback3.git

# Explicación del funcionamiento

Este proyecto es una aplicación de Android para gestionar una base de datos de novelas. La aplicación permite agregar, ver y administrar detalles sobre diversas novelas mediante una interfaz amigable y un sistema de temas personalizable. Además los usuarios pueden iniciar sesión, guradar su preferencia de tema claro/oscuro y hacer copias de seguridad y recuperar los datos.

## Clases del Proyecto

### Paquete Actividades

#### 1. `AgregarNovelaActivity`
   - **Descripción**: Esta clase es una actividad de Android encargada de la interfaz para agregar una nueva novela a la base de datos de la aplicación.
   - **Funcionalidades**:
     - Inicializa los componentes de la interfaz, como los campos de entrada para el título, autor, año de publicación y sinopsis de la novela.
     - Aplica el tema (oscuro o claro) según las preferencias del usuario almacenadas en `SharedPreferences`.
     - Realiza validaciones en los campos de entrada: asegura que no estén vacíos, convierte el año a un número y verifica que esté dentro de un rango razonable (1300-2100).
     - Crea una nueva instancia de la clase `Novela` con los datos ingresados y la guarda en la base de datos utilizando `NovelaDatabaseHelper`.
     - Proporciona retroalimentación al usuario mediante mensajes `Toast`, informando sobre errores de entrada o confirmando la adición exitosa de la novela.
    
#### 2. `AgregarResenaActivity`
   - **Descripción**: Esta actividad permite al usuario agregar una reseña a una novela específica en la base de datos.
   - **Funcionalidades**:
     - **Interfaz**: Configura la interfaz con un campo de texto para la reseña y dos botones: uno para guardar la reseña y otro para volver a la pantalla anterior.
     - **Aplicación de Tema**: Aplica el tema de la interfaz (oscuro o claro) basado en las preferencias almacenadas en `SharedPreferences`.
     - **Validación y Almacenamiento**:
       - Verifica que el campo de reseña no esté vacío antes de intentar guardarlo.
       - Comprueba si la novela existe en la base de datos antes de agregar la reseña.
       - Si la novela existe, guarda la reseña y muestra un mensaje de confirmación; en caso de error o si la novela no existe, muestra el mensaje correspondiente.
     - **Mensajes al Usuario**: Utiliza `Snackbar` para mostrar mensajes de éxito o error, proporcionando una retroalimentación clara en la pantalla.

#### 3. `ConfiguracionActivity`
   - **Descripción**: Esta actividad permite al usuario ajustar la configuración de la aplicación, incluyendo el tema y opciones de copias de seguridad de datos de usuario.
   - **Funcionalidades**:
     - **Cambio de Tema**: Utiliza un `Switch` para activar o desactivar el tema oscuro. Al cambiar la preferencia, recarga la actividad para aplicar el nuevo tema.
     - **Copia de Seguridad de Usuarios**: Permite crear un archivo de respaldo con la información de los usuarios almacenados en la base de datos. El archivo se guarda en el almacenamiento externo y contiene datos como el correo electrónico, contraseña y configuración de tema.
     - **Restauración de Datos**: Restaura los datos de usuario desde una copia de seguridad. Lee el archivo de respaldo y agrega cada usuario a la base de datos si aún no existe.
     - **Mensajes al Usuario**: Informa el estado de la copia de seguridad y restauración mediante mensajes `Toast`.

#### 4. `DetallesNovelaActivity`
   - **Descripción**: Esta actividad muestra los detalles de una novela seleccionada y permite al usuario marcarla como favorita, eliminarla o agregar una reseña.
   - **Funcionalidades**:
     - **Visualización de Detalles**: Muestra el título, autor, año de publicación y sinopsis de la novela.
     - **Marcado como Favorito**: Permite al usuario marcar o desmarcar la novela como favorita. Al cambiar el estado, se actualiza el texto del botón y se guarda la preferencia en la base de datos.
     - **Eliminación de Novela**: Ofrece un botón para eliminar la novela de la base de datos y regresa al `MainActivity` tras la eliminación.
     - **Agregar Reseña**: Permite al usuario navegar a `AgregarResenaActivity` para añadir una reseña a la novela seleccionada.
     - **Mensajes al Usuario**: Informa sobre el estado de las operaciones mediante `Toast`, mostrando si la acción se completó con éxito o si hubo algún error.
     - **Aplicación de Tema**: Ajusta el tema oscuro o claro según la preferencia almacenada en `SharedPreferences`.

#### 5. `LoginActivity`
   - **Descripción**: Esta actividad permite al usuario iniciar sesión o registrarse en la aplicación mediante un formulario de ingreso de credenciales.
   - **Funcionalidades**:
     - **Inicio de Sesión**: Verifica las credenciales del usuario (correo electrónico y contraseña) en la base de datos. Si son correctas, redirige al `MainActivity`; en caso de error, muestra un mensaje de advertencia.
     - **Registro de Usuario**: Permite a un nuevo usuario registrarse ingresando su correo electrónico y contraseña. Guarda el usuario en la base de datos con la preferencia de tema oscuro desactivada por defecto.
     - **Mensajes al Usuario**: Utiliza `Toast` para notificar al usuario sobre el estado del inicio de sesión o registro, como credenciales inválidas o un registro exitoso.

#### 6. `MainActivity`
   - **Descripción**: La actividad principal de la aplicación que muestra una lista de novelas. Desde aquí, el usuario puede ver detalles de las novelas, agregar nuevas, acceder a configuraciones, y cerrar sesión.
   - **Funcionalidades**:
     - **Visualización de Novelas**: Utiliza `NovelaAdapter` para mostrar la lista de novelas en un `ListView`. La lista se actualiza automáticamente al reanudar la actividad o cuando se modifica una novela.
     - **Detalle de Novela**: Permite al usuario acceder a `DetallesNovelaActivity` al hacer clic en cualquier novela de la lista para ver información detallada. También refleja los cambios en el estado de favorito en la lista.
     - **Agregar Novela**: Redirige a `AgregarNovelaActivity` para que el usuario agregue nuevas novelas.
     - **Configuración**: Permite al usuario acceder a `ConfiguracionActivity` para cambiar preferencias, como el tema oscuro.
     - **Cerrar Sesión**: Redirige a `LoginActivity` y termina la actividad actual para cerrar sesión.
     - **Cambio de Tema**: Aplica un tema claro u oscuro basado en las preferencias guardadas.

### Paquete Bases de Datos

#### 7. `NovelaDatabaseHelper`
   - **Descripción**: Clase que gestiona la base de datos de novelas, incluyendo operaciones para agregar, eliminar, y actualizar novelas, así como almacenar reseñas.
   - **Funciones**:
     - **`onCreate(db: SQLiteDatabase)`**: Crea las tablas necesarias para almacenar novelas y reseñas.
     - **`onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)`**: Maneja la actualización de la base de datos, asegurando la creación de las tablas adecuadas.
     - **`agregarNovela(novela: Novela)`**: Agrega una nueva novela a la base de datos.
     - **`eliminarNovela(titulo: String): Boolean`**: Elimina una novela especificada por su título y devuelve si la operación fue exitosa.
     - **`obtenerNovelas(): List<Novela>`**: Recupera una lista de todas las novelas almacenadas en la base de datos.
     - **`actualizarFavorito(titulo: String, esFavorita: Boolean): Boolean`**: Actualiza el estado de favorito de una novela especificada por su título.
     - **`agregarResena(titulo: String, resena: String): Boolean`**: Agrega una reseña a la base de datos para una novela específica.
     - **`existeNovela(titulo: String): Boolean`**: Verifica si una novela con el título dado ya existe en la base de datos.

#### 8. `UsuarioDatabaseHelper`
   - **Descripción**: Clase que gestiona la base de datos de usuarios, incluyendo operaciones para agregar, verificar, y recuperar usuarios, así como obtener preferencias de tema.
   - **Funciones**:
     - **`onCreate(db: SQLiteDatabase)`**: Crea la tabla de usuarios en la base de datos.
     - **`onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)`**: Maneja la actualización de la base de datos eliminando la tabla de usuarios y recreándola.
     - **`AgregarUsuario(usuario: Usuario)`**: Agrega un nuevo usuario a la base de datos.
     - **`verificarUsuario(email: String, password: String): Boolean`**: Verifica si las credenciales de un usuario son válidas.
     - **`obtenerUsuarios(): List<Usuario>`**: Recupera una lista de todos los usuarios almacenados en la base de datos.
     - **`agregarUsuarioSiNoExiste(usuario: Usuario)`**: Agrega un nuevo usuario si no existe en la base de datos.
     - **`obtenerTemaUsuario(email: String): Boolean`**: Obtiene la preferencia de tema oscuro de un usuario específico.

### Paquete Data Classes

#### 9. `Novela`
   - **Descripción**: Clase de datos que representa una novela, incluyendo su título, autor, año de publicación, sinopsis, estado de favorito, y reseñas.
   - **Propiedades**:
     - **`titulo: String`**: Título de la novela.
     - **`autor: String`**: Autor de la novela.
     - **`anioPublicacion: Int`**: Año en que se publicó la novela.
     - **`sinopsis: String`**: Sinopsis o resumen de la novela.
     - **`esFavorita: Boolean`**: Indica si la novela es marcada como favorita (por defecto es `false`).
     - **`resenas: MutableList<String>`**: Lista de reseñas asociadas a la novela (por defecto es una lista vacía).

#### 10. `Usuario`
   - **Descripción**: Clase de datos que representa un usuario, incluyendo su correo electrónico, contraseña y preferencia de tema.
   - **Propiedades**:
     - **`email: String`**: Correo electrónico del usuario.
     - **`password: String`**: Contraseña del usuario.
     - **`temaOscuro: Boolean`**: Indica si el usuario prefiere un tema oscuro (por defecto es `false`).

### Otras

### 11. `NovelaAdapter`
   - **Descripción**: Adaptador para la lista de novelas, que gestiona la visualización de cada elemento en la lista y la interacción del usuario.
   - **Constructor**:
     - **`NovelaAdapter(context: Context, novelas: List<Novela>)`**: 
       - **`context`**: Contexto de la aplicación.
       - **`novelas`**: Lista de objetos `Novela` que se mostrarán en la lista.
   - **Métodos**:
     - **`getView(position: Int, convertView: View?, parent: ViewGroup): View`**: 
       - Configura la vista de cada elemento de la lista. 
       - Muestra el título y el autor de la novela, aplicando un subrayado y color diferente si la novela es favorita.
       - Establece un `OnClickListener` para abrir la actividad de detalles de la novela al hacer clic en un elemento de la lista.
