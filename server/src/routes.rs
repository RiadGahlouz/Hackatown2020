use gotham::router::Router;
use gotham::router::builder::*;
use gotham::state::State;
use gotham::handler::IntoResponse;

pub fn index(state: State) -> (State, impl IntoResponse) {
    (state, (mime::TEXT_HTML, include_str!("resources/index.html")))
}

pub fn restos(state: State) -> (State, impl IntoResponse) {
    (state, (mime::APPLICATION_JSON, "[{\"id\":\"0x3f43\",\"name\":\"bobinette\", \"hours\": \"7 a 9\", \"location\": \"6e pavillon principal\"}]"))
}

pub fn create_router() -> Router {
    build_simple_router(|route| {
        route.get("/").to(index);
        route.get("/resto").to(restos);
    })
}