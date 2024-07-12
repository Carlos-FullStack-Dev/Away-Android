package com.plum.networkk.awmapplication.apis


import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*

/**
 * Created by aCodeMechanic on 2019-06-29.
 */
open class MultipartRequest(
    url: String,
    params: Map<String, String>,
    imageFile: File,
    filename: String,
    fileFieldName: String,
    errorListener: Response.ErrorListener,
    listener: Response.Listener<String>
) : Request<String>(Request.Method.POST, url, errorListener) {

    init {
        mListener = listener
        mImageFile = imageFile
        mParams = params
        mFileFieldName = fileFieldName
        mFilename = filename
        buildMultipartEntity()

    }

    companion object {

        private var mBuilder = MultipartEntityBuilder.create()
        private lateinit var mListener: Response.Listener<String>
        private lateinit var mImageFile: File
        protected var headers: Map<String, String>? = null
        private var mBoundary: String? = null
        private lateinit var mParams: Map<String, String>
        private lateinit var mFileFieldName: String
        private lateinit var mFilename: String
        private var mBodyContentType: String? = null

        fun setBoundary(boundary: String) {
            mBoundary = boundary
        }


    }

    private fun buildMultipartEntity() {
        for ((key, value) in mParams) {
            mBuilder.addTextBody(key, value)
        }
        mBuilder.addBinaryBody(
            mFileFieldName,
            mImageFile, ContentType.create("image/jpg"),
            mFilename
        )
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
    }

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        var headers: MutableMap<String, String>? = super.getHeaders()

        if (headers == null || headers == emptyMap<Any, Any>()) {
            headers = HashMap()
        }

        headers["Accept"] = "application/json"
        headers["X-Requested-With"] = "XMLHTTPRequest"
        headers["User-Agent"] = "KaliMessenger"
        return headers
    }

    override fun getBodyContentType(): String? {
        return mBodyContentType
    }

    @Throws(AuthFailureError::class)
    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        try {
            val entity = mBuilder.build()
            mBodyContentType = entity.contentType.value
            entity.writeTo(bos)
        } catch (e: IOException) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.")
        }

        return bos.toByteArray()
    }

    override fun deliverResponse(response: String) {
        mListener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
        var parsed: String
        try {
            parsed =
                String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers)))
        } catch (e: UnsupportedEncodingException) {
            parsed = String(response.data)
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
    }
}