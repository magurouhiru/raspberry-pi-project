use sqlx::{SqlitePool, sqlite::SqlitePoolOptions};
use std::fs::{self, File};

pub async fn init_db() -> Result<SqlitePool, sqlx::Error> {
    let dir_path = if cfg!(debug_assertions) {
        // cargo watch ignore がうまく動かなかったので開発時はtarget に作成
        "./target/"
    } else {
        "./"
    };
    let file_name = "sqlite.db";

    fs::create_dir_all(dir_path)?;
    File::create(format!("{dir_path}{file_name}"))?;

    let pool = SqlitePoolOptions::new()
        .max_connections(5)
        .connect(&format!("sqlite://{}{}", dir_path, file_name))
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
