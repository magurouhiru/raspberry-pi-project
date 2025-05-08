use axum::{Json, http::StatusCode};
use serde::Serialize;

pub async fn handler() -> (StatusCode, Json<Message>) {
    let message = Message {
        message: "Hello, world!".to_string(),
    };

    (StatusCode::OK, Json(message))
}

#[derive(Serialize)]
pub struct Message {
    message: String,
}
