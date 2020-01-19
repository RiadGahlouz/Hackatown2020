package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Resto: Serializable{

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("hours")
    @Expose
    var hours: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null
}