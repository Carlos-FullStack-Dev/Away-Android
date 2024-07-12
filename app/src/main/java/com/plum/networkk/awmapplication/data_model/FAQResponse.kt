package com.plum.networkk.awmapplication.data_model

// To parse the JSON, install Klaxon and do:
//
//   val fAQResponse = FAQResponse.fromJson(jsonString)

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class FAQResponse (
    val status: Boolean,
    val message: String,
    val data: List<FAQItem>
) {

}

data class FAQItem (
    val id: Long,
    val name: String,
    val description: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String
)
