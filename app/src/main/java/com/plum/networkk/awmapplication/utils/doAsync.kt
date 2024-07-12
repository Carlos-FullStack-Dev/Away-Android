package com.plum.networkk.awmapplication.utils

import android.os.AsyncTask


/*
Created By aCodeMechanic, mac on 10/11/22.
*/

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}