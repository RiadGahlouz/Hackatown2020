package ca.teamrocket.polyeats.network

import ca.teamrocket.polyeats.network.models.Resto
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Backend {
    private val GSON = Gson()

    private val BACKEND_ADDR = "192.168.1.4"
    private val BACKEND_PORT = "80"

    private val END_RESTOS = "/resto"

    fun GetRestos(queue: RequestQueue, callback: (List<Resto>?) -> Void){
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, END_RESTOS,
            Response.Listener<String> { response ->
                val responseType = object : TypeToken<List<Resto>>() {}.type
                val restos = GSON.fromJson<List<Resto>>(response, responseType)
                callback(restos)
            },
            Response.ErrorListener { callback(null)})

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}