package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MenuItem {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("id_resto")
    @Expose
    var id_resto: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}