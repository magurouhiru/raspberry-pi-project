pub mod db {
    use chrono::NaiveDateTime;
    use serde::Serialize;

    #[derive(Serialize)]
    pub struct AnyDb {
        pub tables: Vec<AnyTable>,
    }

    #[derive(Serialize)]
    pub struct AnyTable {
        pub name: String,
        pub label: Vec<String>,
        pub data: Vec<Vec<String>>,
    }

    #[derive(sqlx::FromRow, Debug)]
    pub struct Hello {
        pub id: u32,
        pub timestamp: NaiveDateTime,
        pub message: String,
    }
}
