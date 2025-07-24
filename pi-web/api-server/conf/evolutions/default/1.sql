# --- !Ups
CREATE TABLE hello(
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      message TEXT,
                      timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
# --- !Downs
DROP TABLE hello;
