package com.kidsboodle.teacher.data.model.changepwd

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails





data class ChangePasswordResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,


    @field:SerializedName("msg")
    val msg: String? = null
)
