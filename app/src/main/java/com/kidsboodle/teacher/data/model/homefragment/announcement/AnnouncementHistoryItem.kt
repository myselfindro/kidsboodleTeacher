package com.kidsboodle.teacher.data.model.homefragment.announcement

import com.google.gson.annotations.SerializedName

data class AnnouncementHistoryItem(

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("results")
    val result: List<ResultItems?>? = null
)

data class ResultItems(

    @field:SerializedName("created_by")
    val school_user: String? = null,


    @field:SerializedName("date")
    val date: String? = null,


    @field:SerializedName("id")
    val id: Int? = null,


    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("announcement_type")
    val announcement_type: String? = null,

    @field:SerializedName("description")
    val description: String? = null

)


data class Session(


    @field:SerializedName("id")
    val id: Int? = null,


    @field:SerializedName("session")
    val results: String? = null,


    @field:SerializedName("start_date")
val start_date: String? = null,


@field:SerializedName("end_date")
val end_date: String? = null

)