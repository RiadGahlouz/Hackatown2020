package ca.teamrocket.polyeats.network

import android.util.Log
import ca.teamrocket.polyeats.network.models.Resto
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

object Backend {
    private val GSON = Gson()

    private val BACKEND_PORT = "80"
    private val BACKEND_ADDR = "http://34.95.4.15"

    private val END_RESTOS = "$BACKEND_ADDR/restos"
    private val END_SET_POS = "$BACKEND_ADDR/set_pos"

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
    }

    fun setPosition(queue: RequestQueue, longitude: Double, latitude: Double, altitude: Double, accuracyV: Float, accuracyR: Float) {
        val jo = JSONObject()
        jo.put("lon", longitude)
        jo.put("lat", latitude)
        jo.put("alt", altitude)
        jo.put("accv", accuracyV)
        jo.put("accr", accuracyR)
        val stringRequest = JsonObjectRequest(
            Request.Method.POST,  END_SET_POS ,
            jo,
            Response.Listener<JSONObject> { response ->
                Log.d("BACKEND", "Ze response is $response")
                val responseType = object : TypeToken<List<Resto>>() {}.type
            },
            Response.ErrorListener {
                Log.d("BACKEND", "No response: ${it.message}")
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}