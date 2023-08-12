package com.dev.demoapp.model

import android.os.Parcelable
import com.google.zxing.BarcodeFormat
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Barcode(
    val id: Long = 0,
    val name: String? = null,
    val text: String,
    val formattedText: String,
    val format: BarcodeFormat,
    val schema: BarcodeSchema,
    val date: Long,
    val isGenerated: Boolean = false,
    val isFavorite: Boolean = false,
    val errorCorrectionLevel: String? = null,
    val country: String? = null
) : Parcelable
