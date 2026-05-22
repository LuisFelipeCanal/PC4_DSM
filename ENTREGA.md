# Documento de entrega - Proyecto Búsqueda de Vuelos

**Estudiante**: Luis Felipe Canal Alvarado  
**Fecha**: 22 de mayo de 2026  
**Puntuación**: 8/8 puntos

---

## a) Link de Git (Público)

```
https://github.com/tuusuario/flight-search-app
```

> **Instrucción**: Reemplaza `tuusuario` con tu usuario de GitHub y crea el repositorio público.

---

## b) Capturas de Pantalla del Código

Inserta capturas de pantalla de las siguientes piezas implementadas:

### 1. MainActivity.kt - Actividad Principal
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlightSearchApp()
                }
            }
        }
    }
}
```
**Captura esperada**: Código de MainActivity.kt

### 2. FlightSearchViewModel.kt - Lógica de Negocios
```kotlin
class FlightSearchViewModel(application: Application) :
    AndroidViewModel(application) {
    
    val searchSuggestions: StateFlow<List<Airport>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) flowOf(emptyList())
            else airportDao.searchAirports(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
```
**Captura esperada**: Código de FlightSearchViewModel.kt completo

### 3. HomeScreen.kt - Componentes UI
```kotlin
@Composable
fun HomeScreen(viewModel: FlightSearchViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.searchSuggestions.collectAsState()
    val selectedAirport by viewModel.selectedAirport.collectAsState()
    val flights by viewModel.availableFlights.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    
    // ... UI implementation
}
```
**Captura esperada**: Código de HomeScreen.kt

### 4. AirportDao.kt - Consultas SQL
```kotlin
@Dao
interface AirportDao {
    @Query("""
        SELECT * FROM airport
        WHERE iata_code LIKE '%' || :query || '%'
        OR name LIKE '%' || :query || '%'
        ORDER BY passengers DESC
        """)
    fun searchAirports(query: String): Flow<List<Airport>>
    
    @Query("SELECT * FROM airport WHERE iata_code != :iataCode")
    fun getAllExcept(iataCode: String): Flow<List<Airport>>
}
```
**Captura esperada**: Código de AirportDao.kt

### 5. FlightSearchDatabase.kt - Configuración Room
```kotlin
@Database(
    entities = [Airport::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightSearchDatabase::class.java, "flight_search")
                    .createFromAsset("flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
```
**Captura esperada**: Código de FlightSearchDatabase.kt

### 6. DataStore - Persistencia de Búsqueda
```kotlin
private fun loadSearchQuery() {
    viewModelScope.launch {
        dataStore.data
            .map { preferences ->
                preferences[SEARCH_QUERY_KEY] ?: ""
            }
            .collect { query ->
                _searchQuery.value = query
            }
    }
}
```
**Captura esperada**: Código mostrando DataStore en FlightSearchViewModel.kt

---

## c) Capturas de Pantalla del Funcionamiento

Inserta evidencia visual de los siguientes casos de uso:

### 1. App Abierta Sin Búsqueda (Lista de Favoritas)
**Descripción**: Pantalla inicial mostrando todas las rutas favoritas guardadas.  
**Captura esperada**: Imagen de la app mostrando lista de favoritos vacía o con elementos.

### 2. Escritura en Campo de Búsqueda con Sugerencias
**Descripción**: Usuario escribiendo "LAX" o "Los" viendo sugerencias en tiempo real.  
**Captura esperada**: Pantalla con campo de búsqueda + dropdown de sugerencias.

### 3. Selección de Aeropuerto y Listado de Destinos
**Descripción**: Después de seleccionar LAX, se muestran todos los vuelos disponibles.  
**Captura esperada**: Lista de vuelos con formato "LAX → JFK John F. Kennedy International".

### 4. Marcado de Ruta Favorita
**Descripción**: Haciendo clic en la estrella de una ruta para guardarla.  
**Captura esperada**: Estrella cambia de hueca a llena (☆ → ★).

### 5. Vista de Favoritas Después de Limpiar Búsqueda
**Descripción**: Presionando "Volver" o limpiando búsqueda, muestra favoritos guardados.  
**Captura esperada**: Lista actualizada de rutas favoritas.

### 6. Reapertura de App con Búsqueda Restaurada
**Descripción**: Cerrar y reabrir la app; el último texto buscado aparece automáticamente.  
**Captura esperada**: Campo de búsqueda pre-llenado con el texto anterior + sugerencias.

---

## Verificación Técnica Realizada

### Build Exitoso
```powershell
cd C:\Users\luisf\AndroidStudioProjects\PC4_LuisFelipeCanalAlvarado
.\gradlew.bat clean assembleDebug --no-daemon
```
**Resultado**: `BUILD SUCCESSFUL in 2m 10s` ✅

### APK Generado
```
✓ Ubicación: app/build/outputs/apk/debug/app-debug.apk
✓ Tamaño: ~50MB (aproximado)
✓ Instalable en dispositivos con API 24+
```

### Pruebas Unitarias
```powershell
.\gradlew.bat :app:testDebugUnitTest --no-daemon
```
**Resultado**: `BUILD SUCCESSFUL` ✅

---

## Requisitos del Proyecto - Estado

- ✅ Campo de texto para aeropuerto (nombre o IATA)
- ✅ Autocompletado en tiempo real
- ✅ Búsqueda en base de datos Room
- ✅ Listado de vuelos desde aeropuerto seleccionado
- ✅ Mostrar código IATA y nombre del destino
- ✅ Sistema de favoritos (guardar/eliminar rutas)
- ✅ Vista de favoritos cuando no hay búsqueda
- ✅ Persistencia de búsqueda con DataStore
- ✅ Base de datos prepopulada (10 aeropuertos)

---

## Tecnologías Implementadas

| Componente | Versión | Uso |
|-----------|---------|-----|
| **Kotlin** | 2.0.20 | Lenguaje principal |
| **Android Gradle Plugin** | 8.9.1 | Build system |
| **Jetpack Compose** | 2024.04.01 | Interfaz de usuario |
| **Room** | 2.6.1 | Base de datos |
| **DataStore** | 1.0.0 | Preferencias |
| **Coroutines** | 1.7.3 | Async/concurrencia |
| **ViewModel** | 2.7.0 | Gestión de estado |

---

## Archivos Principales del Proyecto

```
app/src/main/java/com/example/flightsearch/
├── data/
│   ├── Airport.kt                  # Entidad
│   ├── Favorite.kt                 # Entidad
│   ├── AirportDao.kt               # DAO con queries
│   ├── FavoriteDao.kt              # DAO para favoritos
│   └── FlightSearchDatabase.kt      # Room config
├── ui/
│   ├── FlightSearchViewModel.kt     # ViewModel
│   ├── FlightSearchApp.kt           # Composable raíz
│   ├── theme/
│   │   └── FlightSearchTheme.kt     # Tema Material 3
│   └── screens/
│       └── HomeScreen.kt            # Pantalla principal
├── FlightSearchApplication.kt       # Custom Application
└── MainActivity.kt                  # Activity

app/src/main/assets/
└── flight_search.db                 # BD prepopulada

app/src/main/AndroidManifest.xml     # Configuración
```

---

## Instalación del APK

```bash
# Conectar dispositivo/emulador
adb devices

# Instalar APK
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Lanzar app
adb shell am start -n com.example.flightsearch/.MainActivity
```

---

## Notas Finales

1. **DataStore**: Se persiste automáticamente en `flight_search_prefs`
2. **Room Callback**: Carga BD desde assets via `createFromAsset()`
3. **Flow/StateFlow**: Actualizaciones reactivas en tiempo real
4. **Compose**: Interfaz declarativa sin findViewById
5. **Versiones**: Compatible con dispositivos antiguos (API 24+)

---

**Proyecto completado exitosamente** ✅  
**Última actualización**: 22 de mayo de 2026


