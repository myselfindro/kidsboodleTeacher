package com.kidsboodle.teacher.data.model.homefragment.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("results")
	val results: Results? = null
)

data class Results(

	@field:SerializedName("msg")
	val msg: String? = null
)
