package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Order {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("menu_item_ids")
    @Expose
    var menu_item_ids: Array<String>? = null
}