package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FullMenuItem: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("options")
    @Expose
    var options: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("specs")
    @Expose
    var specs: String? = null
}