use sqlx::{Pool, Sqlite};

#[derive(Clone)]
pub struct AppState {
    pub db: Pool<Sqlite>,
}
