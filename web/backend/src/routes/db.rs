use axum::{Json, extract::State, response::IntoResponse};
use chrono::{DateTime, Utc};
use hyper::StatusCode;

use crate::{api::db, errors::error_500, state::AppState};

pub async fn get(State(state): State<AppState>) -> impl IntoResponse {
    let hello = match sqlx::query_as::<_, db::Hello>("SELECT * FROM hello")
        .fetch_all(&state.db)
        .await
    {
        Ok(v) => v,
        Err(err) => {
            println!("{}", err);
            return error_500::create_response();
        }
    };
    let hello = db::AnyTable {
        name: "hello".to_string(),
        label: vec![
            "id".to_string(),
            "timestamp".to_string(),
            "message".to_string(),
        ],
        data: hello
            .iter()
            .map(|row| {
                let utc_dt = DateTime::<Utc>::from_naive_utc_and_offset(row.timestamp, Utc);
                vec![
                    row.id.to_string(),
                    utc_dt.to_string(),
                    row.message.to_string(),
                ]
            })
            .collect(),
    };

    let db = db::AnyDb {
        tables: vec![hello],
    };
    (StatusCode::OK, Json(db)).into_response()
}
