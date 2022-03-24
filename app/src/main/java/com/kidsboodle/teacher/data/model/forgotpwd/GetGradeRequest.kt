package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName



data class GetGradeRequest (
    @field:SerializedName("page_size")
    val page_size: Int = 0,


    @field:SerializedName("exam")
    val exam: String = "",

    @field:SerializedName("scl_class")
    val scl_class: String = "",

    @field:SerializedName("cls_section")
    val cls_section: String = "",

    @field:SerializedName("student_id")
    val student_id: String? = "",
)