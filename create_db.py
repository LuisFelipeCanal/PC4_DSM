#!/usr/bin/env python3
import sqlite3
import os
import argparse

REPO_ROOT = os.path.dirname(__file__)
ASSETS_DB = os.path.join(REPO_ROOT, 'app', 'src', 'main', 'assets', 'flight_search.db')

AIRPORTS = [
    (1, 'LAX', 'Los Angeles International', 88068121),
    (2, 'JFK', 'John F. Kennedy International', 62765044),
    (3, 'ORD', 'Chicago O\'Hare International', 84648760),
    (4, 'DFW', 'Dallas/Fort Worth International', 75043834),
    (5, 'ATL', 'Hartsfield-Jackson Atlanta International', 110531300),
    (6, 'DEN', 'Denver International', 69306167),
    (7, 'SFO', 'San Francisco International', 58720201),
    (8, 'LAS', 'Harry Reid International', 51934151),
    (9, 'SEA', 'Seattle-Tacoma International', 51805690),
    (10, 'MIA', 'Miami International', 45936948),
]

SQL_CREATE_AIRPORT = '''
CREATE TABLE IF NOT EXISTS airport (
    id INTEGER PRIMARY KEY,
    iata_code TEXT NOT NULL,
    name TEXT NOT NULL,
    passengers INTEGER NOT NULL
)
'''

SQL_CREATE_FAVORITE = '''
CREATE TABLE IF NOT EXISTS favorite (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    departure_code TEXT NOT NULL,
    destination_code TEXT NOT NULL
)
'''

def ensure_assets_dir(path):
    d = os.path.dirname(path)
    os.makedirs(d, exist_ok=True)


def create_db(path, force=False):
    if os.path.exists(path):
        if not force:
            print(f"Database already exists at {path}. Use --force to overwrite.")
            return
        else:
            try:
                os.remove(path)
            except Exception as e:
                raise

    ensure_assets_dir(path)

    conn = sqlite3.connect(path)
    cur = conn.cursor()

    cur.execute(SQL_CREATE_AIRPORT)
    cur.execute(SQL_CREATE_FAVORITE)

    # Insert airports if table empty
    cur.execute('SELECT COUNT(1) FROM airport')
    count = cur.fetchone()[0]
    if count == 0:
        cur.executemany('INSERT INTO airport (id, iata_code, name, passengers) VALUES (?, ?, ?, ?)', AIRPORTS)

    conn.commit()
    conn.close()
    print(f'Base de datos creada correctamente en: {path}')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Generar flight_search.db en app/src/main/assets')
    parser.add_argument('--force', '-f', action='store_true', help='Sobrescribir si existe')
    args = parser.parse_args()

    create_db(ASSETS_DB, force=args.force)
