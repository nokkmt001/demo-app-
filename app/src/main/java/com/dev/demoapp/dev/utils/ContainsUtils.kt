package com.dev.demoapp.dev.utils

object ContainsUtils {

    private var isTest = true

    var HEADER_AUTHORIZATION = "Authorization"
    var HEADER_LANG = "Accept-Language"
    var HEADER_API_KEY = "x-api-key"
    var HEADER_UUID = "x-request-id"
    var HEADER_HEAD_TOKEN = "BEARER "
    var HEADER_CONTENT_TYPE = "Content-Type"
    var HEADER_CONTENT_TYPE_VALUE_JSON = "application/json"

    var RESPONSE_MESSAGE_OK = "OK "
    var HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded"
    /**
     * Use for upload image --> part
     */
    var UPLOAD_IMAGE_PART = "file\"; filename=\"_img.png\" "

    /**
     * Use for upload image --> Content type
     */
    var HEADER_CONTENT_TYPE_IMAGE = "multipart/form-data"

    var TIME_OUT = 60L

    var key_pass = "4qIQfWVADXaPbhXnzxgFjnHmj5RteDJTABC"

    /**
     * multi language, =true if has 2 or more language
     */
    var isMultiLanguage = true

    var DEFAULT_LANGUAGE = "vi"

    var API_KEY_TOKEN = "BEARER "

    val HOST =
         "http://streaming.nexlesoft.com:3001"

    const val checkLogin = "/auth/signin"
    const val checkSignup = "/auth/signup"

    const val getQr = "/booking-service/Qr/getqr"
    const val updateQr = "/booking-service/Qr/updateqr"
    const val getHistory = "/booking-service/Qr/gethistory"
    const val deleteHistory = "/booking-service/Qr/deletehistory"
    const val getVehicleType = "/booking-service/vehicle/getVehicleType"
    const val getTimeType = "/user-service/timeframe/dropdown-timeframe-mobile"

    const val checkUpdate  = "/booking-service/resource/get-version"


//    o	http://streaming.nexlesoft.com:3001/auth/signup

}
