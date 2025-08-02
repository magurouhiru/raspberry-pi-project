# --- !Ups
PRAGMA foreign_keys = ON;

CREATE TABLE hello(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    message TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE device_temp_info(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp DATETIME,
    temp INTEGER
);

CREATE TABLE device_freq_info(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp DATETIME,
    freq INTEGER
);

CREATE TABLE device_cpu_info(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp DATETIME
);

CREATE TABLE device_cpu_detail_info(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cpu_info_id INTEGER,
    cpu_number INTEGER,
    total INTEGER,
    idle INTEGER,
    FOREIGN KEY (cpu_info_id) REFERENCES device_cpu_info(id) ON DELETE CASCADE
);

CREATE TABLE device_mem_info(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp DATETIME,
    mem_total INTEGER,
    mem_free INTEGER,
    buffers INTEGER,
    cached INTEGER,
    active INTEGER,
    inactive INTEGER
);

# --- !Downs
DROP TABLE hello;

DROP TABLE device_temp_info;
DROP TABLE device_freq_info;
DROP TABLE device_cpu_info;
DROP TABLE device_mem_info;
