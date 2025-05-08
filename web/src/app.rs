use axum::{body::Body, extract::Request, http::Response, response::IntoResponse, routing::get};
use include_dir::include_dir;
use mime_guess::from_path;

use crate::routes;

pub fn create_app() -> axum::Router {
    axum::Router::new()
        .nest("/api", create_api_router())
        .route("/", get(static_file))
        .fallback(routes::err404::handler)
}

fn create_api_router() -> axum::Router {
    axum::Router::new().route("/hello", get(routes::hello::handler))
}

// 静的ファイルを返す
static DIST_DIR: include_dir::Dir<'_> = include_dir!("./frontend/dist/frontend/browser");
async fn static_file(req: Request<Body>) -> impl IntoResponse {
    let path = req.uri().path().trim_start_matches('/');

    // ファイルが存在しない場合は index.html を返す（SPA 用）
    let file = DIST_DIR
        .get_file(path)
        .or_else(|| DIST_DIR.get_file("index.html"));

    match file {
        Some(f) => {
            let mime = from_path(f.path()).first_or_octet_stream();
            Response::builder()
                .header("Content-Type", mime.as_ref())
                .body(Body::from(f.contents()))
                .unwrap()
        }
        // 変なパスはindex.html を返すので、ここまで来ない想定
        None => routes::err404::err_404(),
    }
}
