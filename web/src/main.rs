mod app;
mod db;
mod errors;
mod routes;
mod state;

#[tokio::main]
async fn main() {
    let db = db::init_db().await.unwrap();
    tracing_subscriber::fmt::init();
    let app = app::create_app(db);
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    tracing::info!("Listening on http://{}", listener.local_addr().unwrap());
    axum::serve::serve(listener, app).await.unwrap();
}
