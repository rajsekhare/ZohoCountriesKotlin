package com.example.zoho.viewmodel

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response

class ByteRequest
    (method: Int, url: String, private val mListener: Response.Listener<ByteArray>?,
     errorListener: Response.ErrorListener) : Request<ByteArray>(method, url, errorListener) {


    constructor(url: String, listener: Response.Listener<ByteArray>,
                errorListener: Response.ErrorListener) : this(Request.Method.GET, url, listener, errorListener) {}

    override fun deliverResponse(response: ByteArray) {
        mListener?.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
        return Response.success(response.data, null)
    }

    override fun getBodyContentType(): String {
        return "application/octet-stream"
    }
}