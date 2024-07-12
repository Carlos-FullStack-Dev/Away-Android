package com.plum.networkk.awmapplication.apis


import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


/**
 * Created by aCodeMechanic.
 */
class VolleySingleton(context: Context) {

    private var mInstance: VolleySingleton? = null
    private var mCtx: Context = context
    private var mRequestQueue: RequestQueue = getRequestQueue()
    val TAG = "AWMApplication"
    @Synchronized
    fun getInstance(context: Context): VolleySingleton? {
        if (mInstance == null) {
            mInstance =
                VolleySingleton(context)
        }
        return mInstance
    }

    @Synchronized
    fun getRequestQueue(): RequestQueue {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(
                mCtx.applicationContext
            )
        }
        return mRequestQueue
    }

    @Synchronized
    fun <T> addToRequestQueue(req: Request<T>) {
        req.retryPolicy = DefaultRetryPolicy(
            20 * 1000, 0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        req.tag = TAG
        getRequestQueue().add(req)
    }

    fun emptyRequestQueue() {
        getRequestQueue().cancelAll(TAG)
    }
}