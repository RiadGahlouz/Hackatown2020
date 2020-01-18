use gotham::router::Router;
use gotham::router::builder::*;
use gotham::state::State;
use gotham::handler::IntoResponse;
use gotham::middleware::state::StateMiddleware;
use gotham::pipeline::single_middleware;
use gotham::pipeline::single::single_pipeline;
use crate::model::AppData;
use gotham::state::FromState;
use serde::{Deserialize};
use gotham_derive::StateData;
use gotham_derive::StaticResponseExtender;

#[derive(Deserialize, StateData, StaticResponseExtender)]
struct IdPathExtractor {
    id: i32,
}

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


pub fn menus(state: State) -> (State, impl IntoResponse) {
    let json_str = "[{\"id\":\"1\", \"name\": \"Steak\", \"description\": \"Un gros steak\", \"vegan\": false, \"vege\": false, \"carbon_footprint\": \"Major\"}]";
    (state, (mime::APPLICATION_JSON, json_str))
}

pub fn order(state: State) -> (State, impl IntoResponse) {
    let json_str = "{\"order_id\": \"1\"}";
    (state, (mime::APPLICATION_JSON, json_str))
}

pub fn orders(state: State) -> (State, impl IntoResponse) {
    let json_str = "{\"lat\": 3.33, \"lon\": 12.9324, \"state\": \"Assigned\"}";
    (state, (mime::APPLICATION_JSON, json_str))
}


pub fn create_router() -> Router {
    let state = AppData::new();
    let (chain, pipeline) = single_pipeline(single_middleware(StateMiddleware::new(state)));

    build_router(chain, pipeline, |route| {
        route.get("/").to(index);
        route.get("/restos").to(restos);
        route.get("/menus/:id").to(menus);
        route.post("/order").to(order);
        route.get("/orders/:id").to(orders);
    })
}