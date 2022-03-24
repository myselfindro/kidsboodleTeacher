package com.kidsboodle.teacher.data.model.homefragment.teacherprofile

import com.google.gson.annotations.SerializedName

data class TeacherProfileResponse(

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("results")
	val results: Results? = null
)

data class Results(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("education")
	val education: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("address2")
	val address2: Any? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("pin_code")
	val pinCode: String? = null,

	@field:SerializedName("roll")
	val roll: Int? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("city_code")
	val cityCode: Int? = null,

	@field:SerializedName("country_code")
	val countryCode: Int? = null,

	@field:SerializedName("staff_type")
	val staffType: Int? = null,

	@field:SerializedName("dial_code")
	val dialCode: String? = null,

	@field:SerializedName("teacher_subject")
	val teacherSubject: List<TeacherSubjectItem?>? = null,

	@field:SerializedName("school")
	val school: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state_code")
	val stateCode: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("auth_provider")
	val authProvider: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("doj")
	val doj: String? = null
)

data class TeacherSubjectItem(

	@field:SerializedName("scl_class__class_name")
	val sclClassClassName: String? = null,

	@field:SerializedName("subject__subject")
	val subjectSubject: String? = null,

	@field:SerializedName("cls_section__section_name")
	val clsSectionSectionName: String? = null
)
