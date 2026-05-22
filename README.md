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

