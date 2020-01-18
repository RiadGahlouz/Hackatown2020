use std::sync::Arc;
use std::sync::Mutex;
use serde::{Serialize, Deserialize};
use gotham_derive::StateData;

#[derive(Serialize, Deserialize)]
pub struct Resto {
    id: String,
    name: String,
    hours: String,
    location: String,
}

#[derive(Clone, StateData)]
pub struct AppData {
    pub restos: Arc<Mutex<Vec<Resto>>>,
}

impl AppData {
    pub fn new() -> Self {
        // TODO: Remove mock data :)
        let restos = vec![
            Resto {
                id: "0".to_string(),
                name: "Navier + Stokes".to_string(),
                hours: "7h à 19h".to_string(),
                location: "1er étage du Lassonde".to_string(),
            },
            Resto {
                id: "1".to_string(),
                name: "_Hamilton;".to_string(),
                hours: "11h à 14h30".to_string(),
                location: "???".to_string(),
            },
            Resto {
                id: "2".to_string(),
                name: "Galileo;".to_string(),
                hours: "???".to_string(),
                location: "???".to_string(),
            },
            Resto {
                id: "3".to_string(),
                name: "Curie;".to_string(),
                hours: "???".to_string(),
                location: "???".to_string(),
            },
            Resto {
                id: "4".to_string(),
                name: "Hertz;".to_string(),
                hours: "???".to_string(),
                location: "???".to_string(),
            },
            Resto {
                id: "5".to_string(),
                name: "Pascal;".to_string(),
                hours: "???".to_string(),
                location: "???".to_string(),
            }
        ];

        AppData {
            restos: Arc::new(Mutex::new(restos))
        }
    }
}