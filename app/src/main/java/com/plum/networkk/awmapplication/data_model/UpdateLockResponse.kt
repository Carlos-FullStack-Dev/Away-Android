package com.plum.networkk.awmapplication.data_model

// To parse the JSON, install Klaxon and do:
//
//   val fAQResponse = FAQResponse.fromJson(jsonString)

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class UpdateLockResponse (
    val result: Boolean,
    val value: String?,
    val message: String

) {
}


