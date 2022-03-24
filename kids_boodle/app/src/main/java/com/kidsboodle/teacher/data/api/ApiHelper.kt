package com.kidsboodle.teacher.data.api

import com.kidsboodle.teacher.data.model.changepwd.ChangePwdRequest
import com.kidsboodle.teacher.data.model.forgotpwd.EmailVerifyRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordPhoneRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordRequest
import com.kidsboodle.teacher.data.model.forgotpwd.MobileVerifyRequest
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.UpdateAttendanceRequest
import com.kidsboodle.teacher.data.model.login.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiHelper(private val apiService: ApiService) {

    suspend fun login(requestBody: LoginRequest) = apiService._login(requestBody)
    suspend fun getTeacherProfileDetails(token: String) =
        apiService._getTeacherProfileDetails(token)

    suspend fun updateProfileDetails(token: String, requestBody: Map<String, Any>) =
        apiService._updateProfileDetails(token, requestBody)

    suspend fun getTeacherStudentClassList(token: String) =
        apiService._getTeacherStudentClassList(token)

    suspend fun getTeacherClassSectionList(authToken: String, Class: String, section: String) =
        apiService._getTeacherClassSectionList(authToken, Class, section)

    suspend fun getTeacherDashboardDetails(token: String) =
        apiService._getTeacherDashboardDetails(token)

    suspend fun getTeacherEventDetails(token: String) =
        apiService._getTeacherEventListDashboard(token, 0, "")

    suspend fun getTeacherRoutineDetails(token: String, pageSize: Int, dayName: String) =
        apiService._getTeacherRoutineDetails(token, pageSize, dayName)

    suspend fun checkEmailExist(requestBody: EmailVerifyRequest) =
        apiService._checkEmailExist(requestBody)

    suspend fun checkMobileExist(requestBody: MobileVerifyRequest) =
        apiService._checkMobileExist(requestBody)

    suspend fun setForgotPassword(requestBody: ForgotPasswordRequest) =
        apiService._setForgotPassword(requestBody)

    suspend fun setForgotPasswordPhone(requestBody: ForgotPasswordPhoneRequest) =
        apiService._setForgotPasswordPhone(requestBody)

    suspend fun setChangePassword(token: String, requestBody: ChangePwdRequest) =
        apiService._setChangePassword(token, requestBody)

    suspend fun getSubjectClassSec(token: String, Class: String, section: String) =
        apiService._getSubjectClassSec(token, Class, section)

    suspend fun getAttendanceCheck(
        token: String,
        session: String,
        Class: String,
        section: String,
        attendanceType: String,
        routine: String,
    ) =
        apiService._getAttendanceCheck(token, session, Class, section, attendanceType, routine)

    suspend fun getTeacherStudentDetails(
        token: String,
        Class: String,
        section: String,
        session: String,
        startDate: String,
        endDate: String,
        pageSize: String
    ) = apiService._getTeacherStudentDetails(
        token,
        Class,
        section,
        session,
        startDate,
        endDate,
        pageSize
    )

    suspend fun getAttendanceUpdate(
        token: String,
        Class: String,
        section: String,
        session: String,
        pageSize: String
    ) = apiService._getAttendanceUpdate(token, Class, section, session, pageSize)

    suspend fun setAttendanceUpdate(token: String, requestBody: UpdateAttendanceRequest) =
        apiService._setAttendanceUpdate(token, requestBody)

    suspend fun postAnnouncement(
        token: String,
        title: RequestBody,
        desc: RequestBody,
        date: RequestBody,
        to_school_or_class: RequestBody,
        scl_class: RequestBody,
        cls_section: RequestBody,
        student: RequestBody,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        image3: MultipartBody.Part,

        ) = apiService._postAnnouncement(
        token,
        title,
        desc,
        date,
        to_school_or_class,
        scl_class,
        cls_section,
        student,
        image1,
        image2,
        image3
    )

}