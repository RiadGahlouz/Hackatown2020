package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Suggestion {
    @SerializedName("name")
    @Expose
    var name: String? = null
}