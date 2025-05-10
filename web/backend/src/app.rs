use std::time::Duration;

use axum::{
    body::{Body, Bytes},
    extract::Request,
    http::{Method, StatusCode},
    middleware::Next,
    response::{IntoResponse, Response},
    routing::{any, get},
};
use http_body_util::BodyExt;
use sqlx::{Pool, Sqlite};
use tower_http::{
    cors::{Any, CorsLayer},
    trace::TraceLayer,
};
use tracing::Span;

use crate::{errors::error_404, routes, state::AppState};

fn create_api_router(db: Pool<Sqlite>) -> axum::Router {
    axum::Router::new()
        .route("/hello", get(routes::hello::get).post(routes::hello::post))
        .route("/hello/ws", any(routes::hello::ws))
        .with_state(AppState { db: db.clone() })
        .layer(axum::middleware::from_fn(pring_request_body))
        .fallback(error_404::handler)
}

pub fn create_app(db: Pool<Sqlite>) -> axum::Router {
    let cors = CorsLayer::new()
        .allow_origin(Any) // 必要に応じて特定のオリジンを指定
        .allow_methods([Method::GET, Method::PUT, Method::OPTIONS]) // 許可するHTTPメソッド
        .allow_headers(Any); // 必要に応じて特定のヘッダーを指定

    axum::Router::new()
        .nest("/api", create_api_router(db))
        .fallback_service(get(routes::static_file::get))
        .layer(cors) // カスタムCORS設定を適用
        .layer(TraceLayer::new_for_http()
        .make_span_with(|request: &Request<Body>| {
            tracing::info_span!("request", method = ?request.method(), uri = ?request.uri().path())
        })
        .on_response(|response: &Response<Body>,latency:Duration, _span: &Span| {
            match response.status() {
                StatusCode::OK => tracing::info!("Response: {} {}", response.status(), latency.as_millis()),
                StatusCode::SWITCHING_PROTOCOLS => tracing::info!("Response: {} {}", response.status(), latency.as_millis()),
                _ => tracing::error!("Response: {} {}", response.status(), latency.as_millis()),
            }
        }))
}

async fn pring_request_body(request: Request, next: Next) -> Result<impl IntoResponse, Response> {
    let request = buffer_request_body(request).await?;
    Ok(next.run(request).await)
}
// the trick is to take the request apart, buffer the body, do what you need to do, then put
// the request back together
async fn buffer_request_body(request: Request) -> Result<Request, Response> {
    let (parts, body) = request.into_parts();

    // this won't work if the body is an long running stream
    let bytes = body
        .collect()
        .await
        .map_err(|err| (StatusCode::INTERNAL_SERVER_ERROR, err.to_string()).into_response())?
        .to_bytes();

    do_thing_with_request_body(bytes.clone());

    Ok(Request::from_parts(parts, Body::from(bytes)))
}

fn do_thing_with_request_body(bytes: Bytes) {
    tracing::info!(body = ?bytes);
}
