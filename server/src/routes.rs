use gotham::router::Router;
use gotham::router::builder::*;
use gotham::state::State;
use gotham::handler::IntoResponse;
use gotham::middleware::state::StateMiddleware;
use gotham::pipeline::single_middleware;
use gotham::pipeline::single::single_pipeline;
use crate::model::AppData;
use gotham::state::FromState;
use serde::{Deserialize, Serialize};
use gotham_derive::StateData;
use gotham_derive::StaticResponseExtender;
use crate::model::MenuItem;
use hyper::{Body, StatusCode};
use futures::future;
use gotham::helpers::http::response::create_empty_response;
use futures::stream::Stream;
use futures::future::Future;
use gotham::handler::IntoHandlerError;
use gotham::handler::HandlerFuture;
use crate::model::Order;
use gotham::handler::HandlerError;
use hyper::Response;
use hyper::Chunk;
use crate::model::Position;

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

#[derive(Serialize, Deserialize)]
struct ClientOrder {
    menu_item_ids: Vec<String>,
}

fn insert_order(valid_body: Chunk, state: &mut State) -> Result<Response<Body>, HandlerError> {
    let body_content = String::from_utf8(valid_body.to_vec()).unwrap();
    {
        let order = serde_json::from_str::<ClientOrder>(&body_content)
            .map_err(|e| { e.into_handler_error() })?;

        let data = AppData::borrow_from(&state);
        let mut data = data.data.lock().unwrap();
        data.orders.push(Order {
            id: "2".to_string(),
            menu_item_ids: order.menu_item_ids,
        });
    }

    Ok(create_empty_response(&state, StatusCode::OK))
}

pub fn order(mut state: State) -> Box<HandlerFuture> {
    let f = Body::take_from(&mut state)
        .concat2()
        .then(|full_body| match full_body {
            Ok(valid_body) => {
                match insert_order(valid_body, &mut state) {
                    Ok(res) => future::ok((state, res)),
                    Err(err) => future::err((state, err))
                }

            }
            Err(e) => future::err((state, e.into_handler_error())),
        });
    Box::new(f)
}

pub fn all_orders(state: State) -> (State, impl IntoResponse) {
    let json_str = {
        let data = AppData::borrow_from(&state);
        let data = data.data.lock().unwrap();
        serde_json::to_string(&(*data).orders).unwrap()
    };
    (state, (mime::APPLICATION_JSON, json_str))
}


pub fn set_position(mut state: State) -> Box<HandlerFuture> {
    fn set_internal(valid_body: Chunk, state: &mut State) -> Result<Response<Body>, HandlerError> {
        let body_content = String::from_utf8(valid_body.to_vec()).unwrap();
        {
            let pos = serde_json::from_str::<Position>(&body_content)
                .map_err(|e| { e.into_handler_error() })?;

            let data = AppData::borrow_from(&state);
            let mut data = data.data.lock().unwrap();
            data.delivery_pos = pos;
        }

        Ok(create_empty_response(&state, StatusCode::OK))

    }

    let f = Body::take_from(&mut state)
        .concat2()
        .then(|full_body| match full_body {
            Ok(valid_body) => {
                match set_internal(valid_body, &mut state) {
                    Ok(res) => future::ok((state, res)),
                    Err(err) => future::err((state, err))
                }

            }
            Err(e) => future::err((state, e.into_handler_error())),
        });
    Box::new(f)
}


pub fn get_position(state: State) -> (State, impl IntoResponse) {
    let json_str = {
        let data = AppData::borrow_from(&state);
        let data = data.data.lock().unwrap();
        serde_json::to_string(&(*data).delivery_pos).unwrap()
    };
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
        route.get("/orders").to(all_orders);
        route.post("/set_pos").to(set_position);
        route.get("/get_pos").to(get_position);
    })
}