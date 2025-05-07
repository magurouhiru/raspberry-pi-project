use axum::{
    Json, Router,
    http::StatusCode,
    routing::{get, post},
};
use serde::{Deserialize, Serialize};
use tower_http::services::{ServeDir, ServeFile};

#[tokio::main]
async fn main() {
    // initialize tracing
    tracing_subscriber::fmt::init();

    let service = ServeDir::new("../front/dist/front/browser")
        .fallback(ServeFile::new("../front/dist/front/browser/index.html"));

    // build our application with a route
    let app = Router::new()
        // `GET /` goes to `root`
        .route("/api", get(root))
        // `POST /users` goes to `create_user`
        .route("/users", post(create_user))
        .fallback_service(service);

    // run our app with hyper, listening globally on port 3000
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

// basic handler that responds with a static string
async fn root() -> (StatusCode, Json<Message>) {
    let message = Message {
        message: "Hello, world!".to_string(),
    };

    (StatusCode::OK, Json(message))
}

async fn create_user(
    // this argument tells axum to parse the request body
    // as JSON into a `CreateUser` type
    Json(payload): Json<CreateUser>,
) -> (StatusCode, Json<User>) {
    // insert your application logic here
    let user = User {
        id: 1337,
        username: payload.username,
    };

    // this will be converted into a JSON response
    // with a status code of `201 Created`
    (StatusCode::CREATED, Json(user))
}

// the input to our `create_user` handler
#[derive(Deserialize)]
struct CreateUser {
    username: String,
}

// the output to our `create_user` handler
#[derive(Serialize)]
struct User {
    id: u64,
    username: String,
}

#[derive(Serialize)]
struct Message {
    message: String,
}
