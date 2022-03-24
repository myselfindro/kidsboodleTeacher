package com.kidsboodle.teacher.data.model.changepwd

import com.google.gson.annotations.SerializedName

//"auth_provider": "school_user", "new_password": "12345", "old_password": "teacher1234"



data class ChangePwdRequest(
    @field:SerializedName("auth_provider")
    val authProvider: String = "teacher",

    @field:SerializedName("new_password")
    val newPassword: String? = null,

    @field:SerializedName("old_password")
    val oldPassword: String? = null
)