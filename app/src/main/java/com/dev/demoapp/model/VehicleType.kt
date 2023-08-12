package com.dev.demoapp.model

import com.google.gson.annotations.SerializedName

data class VehicleType(
    @SerializedName("vehicle_id")
    var vehicleID: String?= null,
    @SerializedName("vehicle_name")
    var vehicleName: String?= null,
)
