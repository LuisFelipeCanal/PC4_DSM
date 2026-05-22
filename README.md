# Buscador de vuelos - PC4

Aplicacion Android (Jetpack Compose) que permite buscar aeropuertos por nombre o codigo IATA, ver rutas salientes y guardar favoritas usando Room y DataStore.

## Requisitos implementados

- Campo de texto para buscar aeropuerto por nombre o IATA.
- Sugerencias de autocompletado en tiempo real desde Room.
- Lista de vuelos salientes cuando se selecciona un aeropuerto.
- Guardado y eliminacion de rutas favoritas.
- Lista de favoritas cuando no hay texto de busqueda.
- Persistencia del texto de busqueda con Preferences DataStore.

## Tecnologias

- Kotlin + Jetpack Compose
- Room (SQLite)
- Preferences DataStore
- StateFlow + ViewModel

## Ejecutar proyecto

```powershell
cd C:\Users\luisf\AndroidStudioProjects\PC4_LuisFelipeCanalAlvarado
.\gradlew.bat :app:assembleDebug
```

## Probar rapidamente

```powershell
cd C:\Users\luisf\AndroidStudioProjects\PC4_LuisFelipeCanalAlvarado
.\gradlew.bat :app:testDebugUnitTest
```

## Entregable solicitado

### a) Link de git (publico)

- Pendiente de agregar: `https://github.com/<usuario>/<repositorio>`

### b) Capturas de pantalla del codigo

Agrega aqui capturas de:

- `app/src/main/java/com/example/pc4_luisfelipecanalalvarado/MainActivity.kt`
- `app/src/main/java/com/example/pc4_luisfelipecanalalvarado/FlightSearchViewModel.kt`
- `app/src/main/java/com/example/pc4_luisfelipecanalalvarado/FlightSearchScreen.kt`
- `app/src/main/java/com/example/pc4_luisfelipecanalalvarado/data/FlightSearchDao.kt`
- `app/src/main/java/com/example/pc4_luisfelipecanalalvarado/data/FlightSearchDatabase.kt`

### c) Capturas de pantalla del funcionamiento

Agrega aqui capturas de:

- Campo de busqueda vacio mostrando rutas favoritas.
- Autocompletado al escribir un nombre o IATA.
- Seleccion de aeropuerto y lista de vuelos.
- Guardar y quitar una ruta favorita.
- Reapertura de app con texto de busqueda restaurado.

