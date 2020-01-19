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
use crate::model::MenuItem;

#[derive(Deserialize, StateData, StaticResponseExtender)]
struct IdPathExtractor {
    id: String,
}

pub fn index(state: State) -> (State, impl IntoResponse) {
    (state, (mime::TEXT_HTML, include_str!("resources/index.html")))
}

pub fn restos(state: State) -> (State, impl IntoResponse) {
    let json_str = {
        let data = AppData::borrow_from(&state);
        let data = data.data.lock().unwrap();
        serde_json::to_string(&(*data).restos).unwrap()
    };
    (state, (mime::APPLICATION_JSON, json_str))
}


pub fn menus(mut state: State) -> (State, impl IntoResponse) {
    let id_extractor = IdPathExtractor::take_from(&mut state);
     let json_str = {
        let data = &*AppData::borrow_from(&state).data.lock().unwrap();
         let plats: Vec<&MenuItem> =data.plats.iter()
            .filter(|plat| { plat.id_resto == id_extractor.id })
            .collect();
         serde_json::to_string(&plats).unwrap()
    };

    (state, (mime::APPLICATION_JSON, json_str))
}


pub fn get_all_menus(state: State) -> (State, impl IntoResponse) {
    let json_str = {
        let data = AppData::borrow_from(&state);
        let data = data.data.lock().unwrap();
        serde_json::to_string(&(*data).plats).unwrap()
    };
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
        route.get("/menus/:id").with_path_extractor::<IdPathExtractor>().to(menus);
        route.get("/menus").to(get_all_menus);
        route.post("/order").to(order);
        route.get("/orders/:id").with_path_extractor::<IdPathExtractor>().to(orders);
    })
}