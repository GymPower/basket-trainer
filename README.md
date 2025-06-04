# MyBasketTrainer

## 📋 Descripción

**MyBasketTrainer** es una aplicación Android desarrollada con **Jetpack Compose**, diseñada para asistir a entrenadores de baloncesto en la gestión de sus equipos, jugadores y sesiones de entrenamiento.

La app ofrece una interfaz moderna y responsiva con **tema oscuro**: fondo negro, texto blanco y detalles naranjas para una experiencia profesional. Se conecta a una base de datos **MySQL remota alojada en Aiven**.

---

## ✨ Características

- **🔐 Autenticación de Usuarios**  
  Registro y login seguros con hash de contraseñas.

- **🧑‍🤝‍🧑 Gestión de Equipos**  
  Crear, editar y eliminar equipos asociados a un entrenador (`trainer_dni`).

- **👤 Gestión de Jugadores**  
  Administrar jugadores por equipo y entrenador (`team_id`, `trainer_dni`).

- **🗓️ Agenda de Entrenamientos**  
  Programar y visualizar eventos.

- **🏀 Marcador de Partidos**  
  Incrementar/decrementar puntuación en tiempo real.

- **📐 Pizarra Táctica**  
  Dibuja jugadas sobre una cancha interactiva usando `painterResource`.

---

## 🛠️ Tecnologías Utilizadas

- **UI Framework**: Jetpack Compose  
- **API Client**: Retrofit  
- **Base de Datos Remota**: MySQL (Aiven)  
- **Base de Datos Local (opcional)**: Room  
- **Async**: Kotlin Coroutines  
- **DI (asumido)**: Hilt  
- **Pruebas (asumido)**: JUnit, Espresso

---

## 📁 Estructura del Proyecto

```
app/
├── data/          # Repositorios y servicios API
├── ui/            # Pantallas y componentes de interfaz
├── navigation/    # Navegación y rutas
├── theme/         # Definición de tema visual
└── build.gradle   # Configuración de compilación
```

---

## 🧱 Arquitectura

**Clean Architecture** en tres capas:

- **Presentación**  
  UI con Compose (LoginScreen, MainScreen, etc.)

- **Dominio**  
  Lógica de negocio (entities, use cases)

- **Datos**  
  API con Retrofit y (opcionalmente) Room para almacenamiento local.

---

## 🧭 Navegación

- Jetpack Navigation Component
- `NavHost` único definido en `Navigation.kt`
- Composables como rutas, con parámetros (`trainerDni`, etc.)

---

## 🔁 Flujo de Datos

- **API**: Retrofit + MySQL (`getTeamsByTrainer(trainerDni)`, etc.)
- **Local** (opcional): Room para caché.
- **Estado**: `remember`, `mutableStateOf`, o `StateFlow` + `ViewModel`

---

## 🎨 Paleta de Colores

| Color   | Hex       | Uso                        |
|---------|-----------|----------------------------|
| Negro   | `#000000` | Fondo                      |
| Blanco  | `#FFFFFF` | Texto                      |
| Naranja | `#FF9800` | Botones, bordes, detalles  |

---

## ✅ Requisitos Previos

- **Android Studio**: Última versión
- **JDK**: 11 o superior  
- **Android SDK**: API nivel 33+  
- **Git**

---

## 🚀 Instalación

1. **Clonar Repositorio**
   ```bash
   git clone https://github.com/GymPower/MyBasketTrainer.git
   ```

2. **Abrir en Android Studio**  
   `File > Open > Selecciona el directorio del proyecto`

3. **Sincronizar con Gradle**  
   `Sync Project with Gradle Files`

---

## ▶️ Ejecución

### Configurar Backend

La app se conecta a MySQL (Aiven). Configura estas credenciales de forma segura:

- **URI**:  
  ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/defaultdb}
- **Base de Datos**: `defaultdb`  
- **Host**: `baskettrainer-db-baskettrainer-db.k.aivencloud.com`  
- **Puerto**: `27546`  
- **Usuario**: `avnadmin`  
- **Contraseña**: ${SPRING_DATASOURCE_PASSWORD:}
- **SSL**: `REQUIRED`

### Iniciar App

1. Conecta un dispositivo o usa un emulador  
2. Selecciona el dispositivo en Android Studio  
3. Haz clic en **Run > Run 'app'**

---

## 📄 Licencia

Este proyecto es de uso académico/personal. Consulta el repositorio para condiciones específicas.

---

## 👥 Autor

Proyecto desarrollado como parte de **GymPower**.

---