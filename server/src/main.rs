mod routes;

use gotham::state::State;

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
    let addr = "0.0.0.0:80";
    println!("Listening for requests at http://{}", addr);
    gotham::start(addr, routes::create_router())
}