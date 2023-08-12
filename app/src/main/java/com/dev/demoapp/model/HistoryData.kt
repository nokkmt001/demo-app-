package com.dev.demoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class HistoryData(
    val id: Long? = null,
    val time: String? = "",

    @SerializedName("supplier_code")
    val supplierCode: String? = null,

    @SerializedName("supplier_name")
    val supplierName: String? = null,

    @SerializedName("qr_code")
    val qrCode: String? = null,

    val code: String? = null,
    val user: Long? = null,
    val status: Long? = null,

    @SerializedName("license_plate")
    val licensePlate: String? = null
) : Parcelable

data class HistoryDataResponse(
    var total: Int? = null,

    @SerializedName("historyDTOs")
    var result: List<HistoryData>? = null,
)

@Parcelize
data class HistoryParams(
    val user: String? = null,

    @SerializedName("qr_status")
    var qrStatus: Int? = null,
    var begin: Int? = 0,
    var end: Int? = 0,
) : Parcelable

@Parcelize
data class HistoryDataRemove(
    val time: Date? = null,

    @SerializedName("supplier_Code")
    val supplierCode: String? = null,

    @SerializedName("qR_Code")
    val qRCode: String? = null,

    val code: String? = null,
    val id: Long? = null,
    val createdAt: Date? = null,
    val createdBy: Long? = null,
    val modifiedAt: Date? = null,
    val modifiedBy: Long? = null,
    val deletedAt: Date? = null,
    val deteleBy: Long? = null,
    val status: Long? = null,

    @SerializedName("referenceId")
    val referenceID: String? = null,
) : Parcelable
