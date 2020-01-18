package ca.teamrocket.polyeats.network

import android.util.Log
import ca.teamrocket.polyeats.network.models.Resto
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Backend {
    private val GSON = Gson()

    private val BACKEND_PORT = "80"
    private val BACKEND_ADDR = "http://192.168.1.4:$BACKEND_PORT"

    private val END_RESTOS = "$BACKEND_ADDR/restos"

    fun getRestos(queue: RequestQueue, callback: (List<Resto>?) -> Unit){
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,  END_RESTOS,
            Response.Listener<String> { response ->
                Log.d("BACKEND", "Ze response is $response")
                val responseType = object : TypeToken<List<Resto>>() {}.type
                val restos = GSON.fromJson<List<Resto>>(response, responseType)
                callback(restos)
            },
            Response.ErrorListener {

                Log.d("BACKEND", "No response: ${it.message}")
                callback(null)})

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        queue.start()
    }
}