package com.kidsboodle.teacher.data.model.homefragment.studentsattendance

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails





data class SubjectListResponse(

    @field:SerializedName("result")
    val result: List<ResultI?>? = null,

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("msg")
    val msg: String? = null
)
data class ResultI(



    @field:SerializedName("subject")
    val subjectClasswise: String? = null,

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("end_time")
    val endTime: String? = null,

    @field:SerializedName("is_live")
    val isLive: Boolean? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    )
