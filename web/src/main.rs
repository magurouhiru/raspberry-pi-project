mod app;
mod db;
mod errors;
mod routes;
mod state;

use std::net::SocketAddr;

#[tokio::main]
async fn main() {
    let db = db::init_db().await.unwrap();
    tracing_subscriber::fmt::init();
    let app = app::create_app(db);
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    tracing::info!("Listening on http://{}", listener.local_addr().unwrap());
    axum::serve(
        listener,
        app.into_make_service_with_connect_info::<SocketAddr>(),
    )
    .await
    .unwrap();
}
