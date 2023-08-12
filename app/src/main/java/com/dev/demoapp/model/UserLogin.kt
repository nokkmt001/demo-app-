package com.dev.demoapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLogin(
    var id: Int? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var role: String? = null
) : Parcelable

@Parcelize
data class UserLoginPass(
    var email: String? = null,

    var password: String? = null
) : Parcelable

@Parcelize
data class UserResponse(
    var user: UserLogin? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null,

    var statusCode: Int? = null,
    var message: String? = null
) : Parcelable
