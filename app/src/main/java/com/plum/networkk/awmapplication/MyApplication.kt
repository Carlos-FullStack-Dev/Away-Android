package com.plum.networkk.awmapplication

import android.R
import android.app.Application
import android.content.Context


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()


    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
}