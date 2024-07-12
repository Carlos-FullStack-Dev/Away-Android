package com.plum.networkk.awmapplication.data_model
// To parse the JSON, install Klaxon and do:
//
//   val statisticsResponse = StatisticsResponse.fromJson(jsonString)

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName


data class StatisticsResponse (
    val status: Boolean,
    val message: String,
    val monthArray: Map<String, Long>,
    val last24Hour: Map<String, Long>,

    @SerializedName("UnlocksToday")
    val unlocksToday: Long,

    @SerializedName( "MinutesToday")
    val minutesToday: Long,

    @SerializedName( "MinutesThisWeek")
    val minutesThisWeek: Long
) {

}

