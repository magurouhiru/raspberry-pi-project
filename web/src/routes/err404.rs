use axum::body::Body;
use axum::http::{Response, StatusCode};

pub async fn handler() -> Response<Body> {
    err_404()
}

pub fn err_404() -> Response<Body> {
    Response::builder()
        .status(StatusCode::NOT_FOUND)
        .body(Body::from("404 Not Found"))
        .unwrap()
}
