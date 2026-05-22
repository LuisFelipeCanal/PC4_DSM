import sqlite3

db_path = r'C:\Users\luisf\AndroidStudioProjects\PC4_LuisFelipeCanalAlvarado\app\src\main\assets\flight_search.db'

# Crear conexión
conn = sqlite3.connect(db_path)
cursor = conn.cursor()

# Crear tabla de aeropuertos
cursor.execute('''
    CREATE TABLE IF NOT EXISTS airport (
        id INTEGER PRIMARY KEY,
        iata_code TEXT NOT NULL,
        name TEXT NOT NULL,
        passengers INTEGER NOT NULL
    )
''')

# Crear tabla de favoritos
cursor.execute('''
    CREATE TABLE IF NOT EXISTS favorite (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        departure_code TEXT NOT NULL,
        destination_code TEXT NOT NULL
    )
''')

# Insertar aeropuertos de ejemplo
airports = [
    (1, 'LAX', 'Los Angeles International', 88068121),
    (2, 'JFK', 'John F. Kennedy International', 62765044),
    (3, 'ORD', 'Chicago OHare International', 84648760),
    (4, 'DFW', 'Dallas/Fort Worth International', 75043834),
    (5, 'ATL', 'Hartsfield-Jackson Atlanta International', 110531300),
    (6, 'DEN', 'Denver International', 69306167),
    (7, 'SFO', 'San Francisco International', 58720201),
    (8, 'LAS', 'Harry Reid International', 51934151),
    (9, 'SEA', 'Seattle-Tacoma International', 51805690),
    (10, 'MIA', 'Miami International', 45936948),
]

cursor.executemany('INSERT INTO airport (id, iata_code, name, passengers) VALUES (?, ?, ?, ?)', airports)

conn.commit()
conn.close()

print('Base de datos creada correctamente')

