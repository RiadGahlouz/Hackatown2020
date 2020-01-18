use gotham::router::Router;
use gotham::router::builder::*;
use gotham::state::State;
use gotham::handler::IntoResponse;
use gotham::middleware::state::StateMiddleware;
use gotham::pipeline::single_middleware;
use gotham::pipeline::single::single_pipeline;
use crate::model::AppData;
use gotham::state::FromState;


pub fn index(state: State) -> (State, impl IntoResponse) {
    (state, (mime::TEXT_HTML, include_str!("resources/index.html")))
}

pub fn restos(state: State) -> (State, impl IntoResponse) {
    let json_str = {
        let state = AppData::borrow_from(&state);
        let restos = state.restos.lock().unwrap();
        serde_json::to_string(&*restos).unwrap()
    };
    (state, (mime::APPLICATION_JSON, json_str))
}

pub fn create_router() -> Router {
    let state = AppData::new();
    let (chain, pipeline) = single_pipeline(single_middleware(StateMiddleware::new(state)));

    build_router(chain, pipeline, |route| {
        route.get("/").to(index);
        route.get("/restos").to(restos);
    })
}