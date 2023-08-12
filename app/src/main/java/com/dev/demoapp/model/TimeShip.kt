package com.dev.demoapp.model

import com.google.gson.annotations.SerializedName

data class TimeShip(
    @SerializedName("id")
    var timeID: String? = null,

    @SerializedName("time_from")
    var timeFrom: String? = null,

    @SerializedName("time_to")
    var timeTo: String? = null,

    @SerializedName("reference_id")
    var referenceID: String? = null

)
