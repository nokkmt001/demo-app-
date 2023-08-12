package com.dev.demoapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignup(
    var id: Int? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var role: String? = null
) : Parcelable

@Parcelize
data class UserSignupRequest(
    var email: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null

) : Parcelable