package com.dev.demoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QrData(
    val id: Long? = null,

    @SerializedName("store_reference")
    val storeReference: String? = null,

    @SerializedName("supplier_reference")
    val supplierReference: String? = null,

    @SerializedName("supplier_code")
    val supplierCode: String? = null,

    @SerializedName("supplier_name")
    val supplierName: String? = null,

    @SerializedName("supplier_email")
    val supplierEmail: String? = null,

    @SerializedName("supplier_phone")
    val supplierPhone: String? = null,

    @SerializedName("line_reference")
    val lineReference: String? = null,

    @SerializedName("department_reference")
    val departmentReference: String? = null,

    @SerializedName("delivery_date")
    val deliveryDate: String? = null,

    @SerializedName("delivery_status")
    val deliveryStatus: String? = null,

    @SerializedName("receipt_status")
    val receiptStatus: String? = null,

    @SerializedName("delivery_order_quantity")
    val deliveryOrderQuantity: String? = null,

    @SerializedName("vehicle_name")
    val vehicleName: String? = null,

    @SerializedName("license_plate")
    val licensePlate: String? = null,

    @SerializedName("driver_name")
    val driverName: String? = null,

    @SerializedName("vehicle_id")
    var vehicleID: String? = null,

    val cmnd: String? = null,

    @SerializedName("emergn_email")
    val emergnEmail: String? = null,

    @SerializedName("emergn_phone")
    val emergnPhone: String? = null,

    @SerializedName("delivery_item_quantity")
    val deliveryItemQuantity: String? = null,

    @SerializedName("delivery_regis_date")
    val deliveryRegisDate: String? = null,

    @SerializedName("registime_reference")
    val regisTimeReference: String? = null,

    @SerializedName("invoice_number")
    val invoiceNumber: String? = null,

    @SerializedName("miss_invoice_number")
    val missInvoiceNumber: String? = null,

    @SerializedName("delivery_invoice_date")
    val deliveryInvoiceDate: String? = null,

    @SerializedName("receiver_name")
    val receiverName: String? = null,

    @SerializedName("receiver_time")
    val receiverTime: String? = null,

    @SerializedName("check_in")
    val checkIn: String? = null,

    @SerializedName("check_out")
    val checkOut: String? = null,

    val reason: String? = null,
    val link: String? = null,

    @SerializedName("qr_code")
    val qrCode: String? = null,

    val code: String? = null,

    @SerializedName("qr_status")
    val qrStatus: Long? = null,

    val user: String? = null,

    @SerializedName("registime_from")
    var regisTimeFrom: String? = null,

    @SerializedName("registime_to")
    var regisTimeTo: String? = null,

    @SerializedName("contract_type")
    var contractType: String? = null

) : Parcelable

@Parcelize
data class QrParams(
    @SerializedName("qr_code")
    val qrCode: String? = null,
    val code: String? = null
) : Parcelable

@Parcelize
data class QrScanRequest(
    val value: String? = null
) : Parcelable

@Parcelize
data class QrUpdate(
    var id: String? = null,

    @SerializedName("qr_status")
    var qrStatus: String? = null,

    @SerializedName("supplier_code")
    var supplierCode: String? = null,

    @SerializedName("qr_code")
    var qrCode: String? = null,

    var code: String? = null,
    var user: Int? = null,

    var cmnd: String? = null,
    @SerializedName("vehicle_name")
    var vehicleName: String? = null,

    @SerializedName("license_plate")
    var licensePlate: String? = null,

    @SerializedName("driver_name")
    var driverName: String? = null,

    @SerializedName("delivery_item_quantity")
    var deliveryItemQuantity: String? = null,

    @SerializedName("vehicle_id")
    var vehicleID: String? = null,

    @SerializedName("registime_reference")
    var regisTimeReference: String? = null,

    @SerializedName("registime_from")
    var regisTimeFrom: String? = null,

    @SerializedName("registime_to")
    var regisTimeTo: String? = null

) : Parcelable
