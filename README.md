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

## Inicialización de la base de datos

La aplicación inicializa los datos de aeropuertos desde Kotlin al crear la base de datos por primera vez. Esto evita la necesidad de generar el `.db` con Python en la mayoría de los casos.

- Si prefieres regenerar un asset SQLite manualmente, existe `create_db.py` en la raíz del repo como herramienta opcional; **no es necesario** para que la app funcione porque Room poblará la BD en la primera ejecución.
- Para forzar la recreación del asset (opcional):

```powershell
python create_db.py --force
```
