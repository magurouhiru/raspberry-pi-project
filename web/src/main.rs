#[allow(unused)]
use axum::{
    Json, Router,
    body::Body,
    extract::Request,
    http::{Response, StatusCode},
    response::IntoResponse,
    routing::{get, post},
};
use include_dir::include_dir;
use mime_guess::from_path;
use serde::{Deserialize, Serialize};

static DIST_DIR: include_dir::Dir<'_> = include_dir!("./frontend/dist/frontend/browser");

#[tokio::main]
async fn main() {
    // initialize tracing
    tracing_subscriber::fmt::init();

    // build our application with a route
    let app = Router::new()
        // `GET /` goes to `root`
        .route("/api", get(root))
        // `POST /users` goes to `create_user`
        .route("/users", post(create_user))
        .fallback_service(get(dist_file));

    // run our app with hyper, listening globally on port 3000
    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn dist_file(req: Request<Body>) -> impl IntoResponse {
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
        // 多分ここまで来ることはない
        None => Response::builder()
            .status(StatusCode::NOT_FOUND)
            .body(Body::from("404 Not Found"))
            .unwrap(),
    }
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
