package com.berlin.remote.network

import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.getStatusCodeFromResponse(): String =
    JSONObject(errorBody()!!.string()).getString("status_code")