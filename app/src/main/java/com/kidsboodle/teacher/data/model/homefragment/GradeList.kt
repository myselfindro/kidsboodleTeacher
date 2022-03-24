package com.kidsboodle.teacher.data.model.homefragment

import com.google.gson.annotations.SerializedName

data class GradeList (
    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("results")
    val result: List<GradeResultItems?>? = null
    )

    data class GradeResultItems(



        @field:SerializedName("student_name")
        val student_name: String? = null,


        @field:SerializedName("id")
        val id: Int? = null,


        @field:SerializedName("total_marks_obtained")
        val total_marks_obtained: String? = "",

        @field:SerializedName("is_active")
        val is_active: Boolean? = false
    )

