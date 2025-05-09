use axum::{
    body::Body,
    extract::Request,
    response::{IntoResponse, Response},
};
use include_dir::include_dir;
use mime_guess::from_path;

use crate::errors::error_404;

static DIST_DIR: include_dir::Dir<'_> = include_dir!("./frontend/dist/frontend/browser");
pub async fn get(req: Request<Body>) -> impl IntoResponse {
    tracing::info!("Static file request: {}", req.uri());
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
        None => error_404::create_response(),
    }
}
