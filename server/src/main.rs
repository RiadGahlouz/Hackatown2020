mod routes;

use gotham::state::State;
use std::env;

const HELLO_WORLD: &str = "Hello World!";

/// Create a `Handler` which is invoked when responding to a `Request`.
///
/// How does a function become a `Handler`?.
/// We've simply implemented the `Handler` trait, for functions that match the signature used here,
/// within Gotham itself.
pub fn say_hello(state: State) -> (State, &'static str) {
    (state, HELLO_WORLD)
}

/// Start a server and call the `Handler` we've defined above for each `Request` we receive.
pub fn main() {
    let addr = match env::var("ADDRESS") {
        Ok(address) => address,
        Err(_) => "127.0.0.1:8080".to_string()
    };
    println!("Listening for requests at http://{}", addr);
    gotham::start(addr, routes::create_router())
}