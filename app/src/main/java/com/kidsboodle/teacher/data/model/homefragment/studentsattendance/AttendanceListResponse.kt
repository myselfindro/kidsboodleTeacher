package com.kidsboodle.teacher.data.model.homefragment.studentsattendance

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.homefragment.timetable.ResultsItem
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails





data class AttendanceListResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,


    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("results")
    val resultsList: List<Resulttt?>? = null,


)

data class Resulttt(
    @field:SerializedName("student_attendence_details")
    val studentAttendanceDetails:StudentAttendDetail? = null,

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
data class StudentAttendDetail(

    @field:SerializedName("count_total")
    val countTotal: Int? = null,
    @field:SerializedName("present_count")
    val presentCount: Int? = null,
    @field:SerializedName("absent_count")
    val absentCount: Int? = null,

)
