package com.kidsboodle.teacher.data.repository

import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.model.changepwd.ChangePwdRequest
import com.kidsboodle.teacher.data.model.forgotpwd.EmailVerifyRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordPhoneRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordRequest
import com.kidsboodle.teacher.data.model.forgotpwd.MobileVerifyRequest
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.UpdateAttendanceRequest
import com.kidsboodle.teacher.data.model.login.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(requestBody: LoginRequest) = apiHelper.login(requestBody)
    suspend fun getTeacherProfileDetails(token: String) = apiHelper.getTeacherProfileDetails(token)
    suspend fun updateProfileDetails(token: String, requestBody: Map<String, Any>) =
        apiHelper.updateProfileDetails(token, requestBody)

    suspend fun getTeacherStudentClassList(token: String) =
        apiHelper.getTeacherStudentClassList(token)

    suspend fun getTeacherClassSectionList(authToken: String, Class: String, section: String) =
        apiHelper.getTeacherClassSectionList(authToken, Class, section)

    suspend fun getTeacherDashboardDetails(token: String) =
        apiHelper.getTeacherDashboardDetails(token)

    suspend fun getTeacherEventDetails(token: String) = apiHelper.getTeacherEventDetails(token)
    suspend fun getTeacherRoutineDetails(token: String, pageSize: Int, dayName: String) =
        apiHelper.getTeacherRoutineDetails(token, pageSize, dayName)

    suspend fun checkEmailExist(requestBody: EmailVerifyRequest) =
        apiHelper.checkEmailExist(requestBody)

    suspend fun checkMobileExist(requestBody: MobileVerifyRequest) =
        apiHelper.checkMobileExist(requestBody)

    suspend fun setForgotPassword(requestBody: ForgotPasswordRequest) =
        apiHelper.setForgotPassword(requestBody)

    suspend fun setForgotPasswordPhone(requestBody: ForgotPasswordPhoneRequest) =
        apiHelper.setForgotPasswordPhone(requestBody)

    suspend fun setChangePassword(token: String, requestBody: ChangePwdRequest) =
        apiHelper.setChangePassword(token, requestBody)

    suspend fun getSubjectClassSec(token: String, Class: String, section: String) =
        apiHelper.getSubjectClassSec(token, Class, section)

    suspend fun getAttendanceCheck(
        token: String,
        session: String,
        Class: String,
        section: String,
        attendanceType: String,
        routine: String
    ) = apiHelper.getAttendanceCheck(token, session, Class, section, attendanceType, routine)

    suspend fun getTeacherStudentDetails(
        token: String,
        Class: String,
        section: String,
        session: String,
        startDate: String,
        endDate: String,
        pageSize: String
    ) = apiHelper.getTeacherStudentDetails(
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
    ) = apiHelper.getAttendanceUpdate(token, Class, section, session, pageSize)

    suspend fun setAttendanceUpdate(token: String, requestBody: UpdateAttendanceRequest) =
        apiHelper.setAttendanceUpdate(token, requestBody)

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
    ) = apiHelper.postAnnouncement(
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