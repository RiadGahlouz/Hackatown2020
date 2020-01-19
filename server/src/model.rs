use std::sync::Arc;
use std::sync::Mutex;
use serde::{Serialize, Deserialize};
use gotham_derive::StateData;
use chrono::offset::TimeZone;

#[derive(Serialize, Deserialize)]
pub struct Resto {
    pub id: String,
    pub name: String,
    pub hours: String,
    pub location: String,
}

#[derive(Serialize, Deserialize)]
pub struct MenuItem {
    pub id: String,
    pub id_resto: String,
    pub name: String,
    pub description: String,
    pub vege: bool,
    pub price: f32,
}

#[derive(Serialize, Deserialize)]
pub struct Order {
    pub id: String,
    pub menu_item_ids: Vec<String>,
    pub date: chrono::DateTime<chrono::Local>,
}

#[derive(Serialize, Deserialize)]
pub struct Position {
    lat: f64,
    lon: f64,
    alt: f64,
    accv: f64,
    accr: f64,
}

pub struct Data {
    pub restos: Vec<Resto>,
    pub plats: Vec<MenuItem>,
    pub orders: Vec<Order>,
    pub delivery_pos: Position,
}

#[derive(Clone, StateData)]
pub struct AppData {
    pub data: Arc<Mutex<Data>>,
}

impl AppData {
    pub fn new() -> Self {
        // TODO: Remove mock data :)
        let restos = vec![
            Resto {
                id: "0".to_string(),
                name: "Navier - Stokes".to_string(),
                hours: "7h à 19h".to_string(),
                location: "1er étage du pavillon principal".to_string(),
            },
            Resto {
                id: "1".to_string(),
                name: "Hamilton".to_string(),
                hours: "11h à 14h30".to_string(),
                location: "1er étage du pavillon principal".to_string(),
            },
            Resto {
                id: "2".to_string(),
                name: "Galileo".to_string(),
                hours: "7h30 à 19h".to_string(),
                location: "2ième étage du pavillon principal".to_string(),
            },
            Resto {
                id: "3".to_string(),
                name: "Curie".to_string(),
                hours: "11h à 14h (fermé le vendredi)".to_string(),
                location: "6ième étage du pavillon principal".to_string(),
            },
            Resto {
                id: "4".to_string(),
                name: "Hertz".to_string(),
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

        let plats = vec![
            MenuItem {
                id: "0".to_string(),
                id_resto: "2".to_string(),
                name: "Salade repas paysanne".to_string(),
                description: "Jambon et oeufs".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "1".to_string(),
                id_resto: "2".to_string(),
                name: "Salade repas océane".to_string(),
                description: "poisson".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "3".to_string(),
                id_resto: "2".to_string(),
                name: "Salade repas végétarienne".to_string(),
                description: "du vert".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "4".to_string(),
                id_resto: "2".to_string(),
                name: "Salade composée d'orzo".to_string(),
                description: "Orzo, pommes Empire, céleri vert, persil, emmental, concombres, menthe, vinaigrette miel & citron".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "5".to_string(),
                id_resto: "2".to_string(),
                name: "Salade composée légumineuses".to_string(),
                description: "Mélange de légumineuses, poivrons rouges, maïs en grains, céleri, écha-lotes vertes, tomates, vinaigrette balsamique".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "6".to_string(),
                id_resto: "2".to_string(),
                name: "Salade composée riz 7 grains".to_string(),
                description: "Riz 7 grains, ratatouille de légumes grillés, oignons rouges, ail, fines herbes".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "7".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch ciabatta végétarien".to_string(),
                description: "Pain ciabatta betterave, tempeh mariné, salade de choux et carottes, coriandre fraiche, sauce gingembre".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "8".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch panini jambon".to_string(),
                description: "Pain panini, jambon blanc, cheddar fort, salade, mayon-naise moutardée".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "9".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch panini poulet grillé".to_string(),
                description: "Pain panini, poulet grillé, tomates, salade, cheddard blanc, mayonnaise tomates séchées".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "10".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch panini roti de boeuf".to_string(),
                description: "Pain panini carottes, rôti de bœuf, oignons caramélisés, salade, rémoulade céleri & carottes".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "11".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch panini roti de porc".to_string(),
                description: "Pain panini, rôti de porc, cornichons à l’aneth, salade, cheddar fort, mayonnaise moutardée".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "12".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch au jambon".to_string(),
                description: "Simple".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "13".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch au poulet".to_string(),
                description: "Simple".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "14".to_string(),
                id_resto: "2".to_string(),
                name: "Sandwitch au oeufs".to_string(),
                description: "Simple".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "15".to_string(),
                id_resto: "2".to_string(),
                name: "Bol kale kimchi".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "16".to_string(),
                id_resto: "2".to_string(),
                name: "Bol kale toscana".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "17".to_string(),
                id_resto: "2".to_string(),
                name: "Bol fèves noires".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "18".to_string(),
                id_resto: "2".to_string(),
                name: "Bol kale césar".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "19".to_string(),
                id_resto: "2".to_string(),
                name: "Wrap dragon".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "20".to_string(),
                id_resto: "2".to_string(),
                name: "Wrap tempeh bbq".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "21".to_string(),
                id_resto: "2".to_string(),
                name: "Wrap végépaté".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "22".to_string(),
                id_resto: "2".to_string(),
                name: "Wrap mekong".to_string(),
                description: "".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "23".to_string(),
                id_resto: "3".to_string(),
                name: "Gamma veau éffiloché".to_string(),
                description: "Pain au maïs, veau éffiloché, oignon rouge, carotte, tomate, salade, sauce tzatziki".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "24".to_string(),
                id_resto: "3".to_string(),
                name: "Gamma falafel".to_string(),
                description: "Pain au maïs, falafels, oignon rouge, carotte, tomate, salade, sauce tzatziki".to_string(),
                vege: true,
                price: 10.,
            },
            MenuItem {
                id: "25".to_string(),
                id_resto: "3".to_string(),
                name: "Salade repas paysanne".to_string(),
                description: "Jambon & oeufs".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "26".to_string(),
                id_resto: "3".to_string(),
                name: "Salade repas océane".to_string(),
                description: "Poisson".to_string(),
                vege: false,
                price: 10.,
            },
            MenuItem {
                id: "27".to_string(),
                id_resto: "3".to_string(),
                name: "Salade repas végétarienne".to_string(),
                description: "".to_string(),
                vege: false,
                price: 10.,
            },
        ];

        let orders = vec![
            Order {
                id: "0".to_string(),
                menu_item_ids: vec!["22".to_string()],
                date: chrono::Local.ymd(2020, 1, 8).and_hms_milli(9, 10, 11, 12),
            }
        ];

        AppData {
            data: Arc::new(Mutex::new(Data { restos, plats, orders, delivery_pos: Position { lat: 0., lon: 0., alt: 0., accr: 0., accv: 0.} }))
        }
    }
}