use sqlx::{SqlitePool, sqlite::SqlitePoolOptions};

pub async fn init_db() -> Result<SqlitePool, sqlx::Error> {
    let pool = SqlitePoolOptions::new()
        .max_connections(5)
        .connect("sqlite://./hello.db")
        .await?;

    sqlx::query(
        "CREATE TABLE IF NOT EXISTS hello (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
            message TEXT NOT NULL
        );",
    )
    .execute(&pool)
    .await?;

    Ok(pool)
}
