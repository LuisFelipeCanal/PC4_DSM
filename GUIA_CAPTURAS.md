# GUÍA PARA TOMAR CAPTURAS DE CÓDIGO Y PANTALLA

## Para tomar CAPTURAS DE CÓDIGO en Android Studio

### Opción 1: Screenshot de código (Recomendado)
1. Abre Android Studio
2. Ve a: **View → Editor Tabs → Screenshot Preview** (si está disponible)
3. O usa: **File → Export → Export as Screenshot**
4. O simplemente: **Ctrl + Shift + G** (en algunas versiones)

### Opción 2: Captura de pantalla de ventana
1. Abre el archivo en Android Studio
2. Presiona: **PrtScn** (captura de pantalla de pantalla completa)
3. ¡O presiona: **Windows Key + Shift + S** (captura de región)
4. Selecciona la región del código que quieres

### Opción 3: Captura manual
1. Uso **Alt + Tab** para enfocar la ventana de Android Studio
2. Presiona **PrtScn**
3. Abre **Paint** o **Paint 3D** (escribe "Paint" en inicio de Windows)
4. **Ctrl + V** para pegar
5. **Ctrl + S** para guardar como PNG

---

## ARCHIVOS A CAPTURAR - CÓDIGO

### 1. MainActivity.kt
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/MainActivity.kt
```
**¿Qué capturar?**: La clase completa

---

### 2. FlightSearchViewModel.kt
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/ui/FlightSearchViewModel.kt
```
**¿Qué capturar?**: 
- Sección de initialización (companion, init)
- StateFlows (searchQuery, selectedAirport, availableFlights, favorites)
- Funciones principales (updateSearchQuery, selectAirport, addFavorite, removeFavorite)

**Tip**: Usa **Ctrl + Shift + Plus** para aumentar el zoom en Android Studio

---

### 3. HomeScreen.kt
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/ui/screens/HomeScreen.kt
```
**¿Qué capturar?**: 
- Función HomeScreen() decorada con @Composable
- Los bloques when() que muestran lógica de UI

---

### 4. AirportDao.kt  
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/data/AirportDao.kt
```
**¿Qué capturar?**: 
- Las consultas SQL @Query 
- Métodos searchAirports() y getAllExcept()

---

### 5. FlightSearchDatabase.kt
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/data/FlightSearchDatabase.kt
```
**¿Qué capturar?**: 
- Anotación @Database
- Métodos abstract (airportDao, favoriteDao)
- Singleton pattern en companion object

---

### 6. DataStore (persistencia)
**Ubicación**: 
```
app/src/main/java/com/example/flightsearch/ui/FlightSearchViewModel.kt
```
**¿Qué capturar?** En este mismo archivo, las funciones:
- `loadSearchQuery()`
- `updateSearchQuery(query: String)`

---

## PARA TOMAR CAPTURAS DE PANTALLA DE LA APP

### Opción 1: Emulador de Android Studio
1. **Tools → Device Manager → Crear dispositivo** (si no existe)
2. **Run app** o **Shift + F10**
3. En el emulador: **Ctrl + Z** (screenshot)
4. Las imágenes se guardan automáticamente

### Opción 2: Captura manual del emulador
1. Abre el emulador
2. **Windows + Shift + S** (región de captura)
3. Selecciona la pantalla de la app
4. Pega en Paint o guarda directamente

### Opción 3: Dispositivo real (si tienes)
1. Conecta dispositivo Android via USB
2. **Run → Run app**
3. En el dispositivo: presiona **Power + Volumen Abajo** (simultáneamente)
4. La captura se guarda en **Galería → Screenshots**

---

## CASOS A CAPTURAR - PANTALLA

1. **Pantalla inicial sin búsqueda**
   - Debería mostrar: Lista de favoritos (vacía o con elementos)

2. **Escribiendo en el campo de búsqueda**
   - Debería mostrar: Campo de texto con texto + teclado visible

3. **Sugerencias desplegadas**
   - Debería mostrar: Dropdown con aeropuertos sugeridos (ej: LAX, LAS, etc)

4. **Después de seleccionar un aeropuerto**
   - Debería mostrar: Encabezado con aeropuerto seleccionado + botón "Volver"

5. **Lista de vuelos disponibles**
   - Debería mostrar: Vuelos en formato "LAX → JFK John F. Kennedy International"
   - Con estrella (☆) para agregar a favoritos

6. **Agregando vuelo a favoritos**
   - Debería mostrar: La estrella cambiar a llena (★)

7. **Volviendo sin búsqueda**
   - Debería mostrar: Campo de búsqueda vacío + lista de favoritos guardados

8. **Reabriendo app con persistencia**
   - Debería mostrar: Campo de búsqueda pre-llenado con último texto buscado
   - Las sugerencias correctas aparecen automáticamente

---

## ORGANIZACIÓN DE CARPETAS

Crea esta estructura en tu PC para guardar las capturas:

```
Capturas/
├── Codigo/
│   ├── 1-MainActivity.png
│   ├── 2-ViewModel.png
│   ├── 3-HomeScreen.png
│   ├── 4-AirportDao.png
│   ├── 5-FlightSearchDatabase.png
│   └── 6-DataStore.png
└── Pantalla/
    ├── 1-Pantalla_inicial.png
    ├── 2-Escribiendo.png
    ├── 3-Sugerencias.png
    ├── 4-Vuelos_seleccionado.png
    ├── 5-Lista_Vuelos.png
    ├── 6-Agregando_favorito.png
    ├── 7-Volver_favoritos.png
    └── 8-Persistencia.png
```

---

## NOTAS IMPORTANTES

- **Zoom**: En Android Studio usa **Ctrl + Shift + /=** para zoom
- **Formato**: Guarda en PNG (mejor calidad)
- **Resolución**: 1920x1080 o similar es ideal
- **Cursor**: Intenta no incluir cursor del ratón
- **Limpio**: Cierra warnings/errores si es posible antes de capturar

---

## COMANDOS ÚTILES EN TERMINAL

Si quieres compilar de nuevo antes de probar:

```powershell
cd C:\Users\luisf\AndroidStudioProjects\PC4_LuisFelipeCanalAlvarado

# Build y install
.\gradlew.bat installDebug

# Ejecutar emulador
emulator -avd <nombre_dispositivo> &

# Iniciar app
adb shell am start -n com.example.flightsearch/.MainActivity
```

---

**¡Listo para capturar!** 📸

