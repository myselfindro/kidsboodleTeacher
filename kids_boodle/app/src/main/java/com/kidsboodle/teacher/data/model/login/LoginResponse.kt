package com.kidsboodle.teacher.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("result")
    val result: Result? = null,

    @field:SerializedName("msg")
    val msg: String? = null
)

data class Result(

    @field:SerializedName("user_details")
    val userDetails: UserDetails? = null,

    @field:SerializedName("token_expiry")
    val tokenExpiry: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class UserDetails(

    @field:SerializedName("education")
    val education: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("address2")
    val address2: Any? = null,

    @field:SerializedName("address1")
    val address1: String? = null,

    @field:SerializedName("school_user_id")
    val schoolUserId: Int? = null,

    @field:SerializedName("pin_code")
    val pinCode: String? = null,

    @field:SerializedName("roll")
    val roll: Int? = null,

    @field:SerializedName("school_name")
    val schoolName: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("city_code")
    val cityCode: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("school_attendence_type")
    val schoolAttendenceType: String? = null,

    @field:SerializedName("country_code")
    val countryCode: String? = null,

    @field:SerializedName("dial_code")
    val dialCode: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("dob")
    val dob: String? = null,

    @field:SerializedName("state_code")
    val stateCode: String? = null,

    @field:SerializedName("user")
    val user: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("session")
    val userSession: UserSession? = null,
)
data class UserSession
    (
    @field:SerializedName("id")
    val id: Int? = null,
)


