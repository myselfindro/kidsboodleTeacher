package com.kidsboodle.teacher.data.model.homefragment.studentsattendance

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.homefragment.timetable.ResultsItem
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails





data class AttendanceUpdateListResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,


    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("results")
    val resultsList: List<ResultAtt?>? = null
)

data class ResultAtt(

    @field:SerializedName("roll_number")
    val rollNumber: String? = null,


    @field:SerializedName("student_name")
    val studentName: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("scl_class")
    val scl_class: String? = null,

    @field:SerializedName("cls_section")
    val cls_section: String? = null,
)
