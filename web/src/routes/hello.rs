use axum::{Json, extract::State, http::StatusCode};
use axum_valid::Valid;
use serde::{Deserialize, Serialize};
use validator::Validate;

use crate::state::AppState;

pub async fn get() -> (StatusCode, Json<HelloResponse>) {
    let message = HelloResponse {
        message: "Hello, world!".to_string(),
    };

    (StatusCode::OK, Json(message))
}

pub async fn post(
    State(state): State<AppState>,
    Valid(Json(req)): Valid<Json<HelloRequest>>,
) -> (StatusCode, Json<HelloResponse>) {
    let message = HelloResponse {
        message: format!("Hello, {}", req.message),
    };

    sqlx::query("INSERT INTO hello (message) VALUES (?)")
        .bind(&message.message)
        .execute(&state.db)
        .await
        .unwrap();
    (StatusCode::OK, Json(message))
}

#[derive(Deserialize, Validate)]
pub struct HelloRequest {
    #[validate(length(min = 1))]
    message: String,
}

#[derive(Serialize)]
pub struct HelloResponse {
    message: String,
}
