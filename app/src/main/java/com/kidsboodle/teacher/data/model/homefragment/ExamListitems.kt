package com.kidsboodle.teacher.data.model.homefragment

import com.google.gson.annotations.SerializedName

data class ExamListitems (
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

        @field:SerializedName("school")
        val school_user: Int? = 0,


        @field:SerializedName("created_at")
        val created_at: String? = null,


        @field:SerializedName("id")
        val id: Int? = null,


        @field:SerializedName("exam_type")
        val exam_type: String? = "",

        @field:SerializedName("is_active")
        val is_active: Boolean? = false
    )

