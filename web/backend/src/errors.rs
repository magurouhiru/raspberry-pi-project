pub mod error_404 {
    use axum::body::Body;
    use axum::http::{Response, StatusCode};

    pub async fn handler() -> Response<Body> {
        create_response()
    }

    pub fn create_response() -> Response<Body> {
        Response::builder()
            .status(StatusCode::NOT_FOUND)
            .body(Body::from("404 Not Found"))
            .unwrap()
    }
}
