use gotham::middleware::Middleware;
use gotham::state::State;
use gotham::handler::HandlerFuture;
use gotham_derive::NewMiddleware;
use futures::future;
use futures::future::Future;
use hyper::header::CONTENT_TYPE;

/// Middleware to inject UTF-8 encoding header in responses
#[derive(Clone, NewMiddleware)]
pub struct Utf8Middleware;

impl Middleware for Utf8Middleware {
    fn call<Chain>(self, state: State, chain: Chain) -> Box<HandlerFuture>
        where
            Chain: FnOnce(State) -> Box<HandlerFuture>,
    {

        // Process the request
        let result = chain(state);

        // Add a post-processing function
        let f = result.and_then(move |(state, mut response)| {
            {
                let headers = response.headers_mut();
//                headers.get_mut()
                if let Some(content_type) = headers.get_mut(CONTENT_TYPE) {
                    if content_type.to_str().unwrap() == mime::APPLICATION_JSON.to_string() {
                        headers.insert(CONTENT_TYPE, "application/json; charset=utf-8".parse().unwrap());
                    }
                }
//                headers.insert("charset", "utf-8".parse().unwrap());
            };
            future::ok((state, response))
        });

        Box::new(f)
    }
}