use axum::{
    Json,
    extract::{FromRequest, Request},
    http::StatusCode,
    response::{IntoResponse, Response},
};
use serde::de::DeserializeOwned;
use serde_json::json;
use validator::{Validate, ValidationErrors};

#[derive(Debug, Clone, Copy, Default)]
#[must_use]
pub struct VaridateJson<T>(pub T);

impl<T, S> FromRequest<S> for VaridateJson<T>
where
    T: DeserializeOwned + Validate,
    S: Send + Sync,
{
    type Rejection = Response;

    async fn from_request(req: Request, state: &S) -> Result<Self, Self::Rejection> {
        match <axum::Json<T> as axum::extract::FromRequest<S>>::from_request(req, state).await {
            Ok(Json(v)) => match v.validate() {
                Ok(()) => Ok(Self(v)),
                Err(err) => Err(to_error_response(err)),
            },
            Err(err) => Err(err.into_response()),
        }
    }
}

fn to_error_response(_err: ValidationErrors) -> Response {
    let error_response = json!({
        "message": "Validation failed",
    });
    (StatusCode::BAD_REQUEST, Json(error_response)).into_response()
}
