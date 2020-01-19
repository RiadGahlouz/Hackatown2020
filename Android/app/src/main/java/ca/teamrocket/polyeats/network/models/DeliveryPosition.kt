package ca.teamrocket.polyeats.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class DeliveryPosition {
    @SerializedName("lon")
    @Expose
    var longitude: Double? = null

    @SerializedName("lat")
    @Expose
    var latitude: Double? = null

    @SerializedName("alt")
    @Expose
    var altitude: Double? = null

    @SerializedName("accv")
    @Expose
    var accuracyV: Float? = null

    @SerializedName("accr")
    @Expose
    var accuracyR: Float? = null

    @SerializedName("speed")
    @Expose
    var speed: Float? = null
}