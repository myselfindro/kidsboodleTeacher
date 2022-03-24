package com.kidsboodle.teacher.data.api

import com.kidsboodle.teacher.data.model.changepwd.ChangePasswordResponse
import com.kidsboodle.teacher.data.model.changepwd.ChangePwdRequest
import com.kidsboodle.teacher.data.model.forgotpwd.*
import com.kidsboodle.teacher.data.model.homefragment.TeacherClassSectionListResponse
import com.kidsboodle.teacher.data.model.homefragment.TeacherDashboardDetailsResponse
import com.kidsboodle.teacher.data.model.homefragment.TeacherStudentClassListResponse
import com.kidsboodle.teacher.data.model.homefragment.announcement.GetAllAnnouncementsHistoryResponse
import com.kidsboodle.teacher.data.model.homefragment.profile.UpdateProfileResponse
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.*
import com.kidsboodle.teacher.data.model.homefragment.teacherprofile.TeacherProfileResponse
import com.kidsboodle.teacher.data.model.homefragment.timetable.TeacherRoutineDetailsResponse
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.data.model.login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("login/")
    suspend fun _login(@Body requestBody:LoginRequest): LoginResponse

    @PUT("teacher_profile_details/")
    @JvmSuppressWildcards
    suspend fun _updateProfileDetails(@Header("Authorization") authToken:String,@Body requestBody: Map<String,Any>,@Query("method") method:String = "edit"): UpdateProfileResponse

    @GET("teacher_profile_details/")
    suspend fun _getTeacherProfileDetails(@Header("Authorization") authToken:String): TeacherProfileResponse

    @GET("teacher_student_class_list/")
    suspend fun _getTeacherStudentClassList(@Header("Authorization") authToken:String): TeacherStudentClassListResponse

//    @GET("teacher_holiday_list/")
//    suspend fun _getTeacherHolidayList(@Header("Authorization") authToken:String,@Query("page_size") pageSize:Int): TeacherStudentClassListResponse

    @GET("teacher_holiday_list/")
    suspend fun _getTeacherEventListDashboard(@Header("Authorization") authToken:String,@Query("page_size") pageSize:Int,@Query("date") Date:String): TeacherStudentClassListResponse

    @GET("teacher_all_announcement_list_get/")
    suspend fun _getAllAnnouncementList(@Header("Authorization") authToken:String,@Query("page_size") pageSize:Int): GetAllAnnouncementsHistoryResponse

    @GET("teacher_class_section_list/")
    suspend fun _getTeacherClassSectionList(@Header("Authorization") authToken:String,@Query("class") Class:String,@Query("section") section:String): TeacherClassSectionListResponse

    @GET("teacher_dashboard_details/")
    suspend fun _getTeacherDashboardDetails(@Header("Authorization") authToken:String): TeacherDashboardDetailsResponse

    @GET("teacher_routine_details_get/")
    suspend fun _getTeacherRoutineDetails(@Header("Authorization") authToken:String,@Query("page_size") pageSize:Int,@Query("day_name") dayName:String): TeacherRoutineDetailsResponse

    @POST("email_verify/")
    suspend fun _checkEmailExist(@Body requestBody:EmailVerifyRequest): EmailVerifyResponse

    @POST("phone_number_verify/")
    suspend fun _checkMobileExist(@Body requestBody: MobileVerifyRequest): MobileVerifyResponse

    @POST("forgot_password/")
    suspend fun _setForgotPassword(@Body requestBody: ForgotPasswordRequest): ForgotPasswordResponse

    @POST("forgot_password/")
    suspend fun _setForgotPasswordPhone(@Body requestBody: ForgotPasswordPhoneRequest): ForgotPasswordResponse

    @PUT("change_password/")
    suspend fun _setChangePassword(@Header("Authorization") authToken:String,@Body requestBody: ChangePwdRequest,@Query("method") method:String = "edit"): ChangePasswordResponse

    @GET("teacher_routine_list_by_daywise/")
    suspend fun _getSubjectClassSec(@Header("Authorization") authToken:String,@Query("class") Class:String,@Query("section") section:String): SubjectListResponse

    @GET("teacher_student_attendence_check/")
    suspend fun _getAttendanceCheck(@Header("Authorization") authToken:String,@Query("session") session:String,@Query("class") Class:String,@Query("section") section:String,@Query("attendence_type") attendenceType:String,@Query("routine") routine:String): AttendanceUpdateListResponse

    @GET("teacher_student_details_get/")
    suspend fun _getTeacherStudentDetails(@Header("Authorization") authToken:String,@Query("scl_class") Class:String,@Query("cls_section") section:String,@Query("session") session:String,@Query("start_date") startDate:String,@Query("end_date") endDate:String,@Query("page_size") pageSize:String): AttendanceListResponse

    @GET("teacher_student_details_get/")
    suspend fun _getAttendanceUpdate(@Header("Authorization") authToken:String,@Query("class") Class:String,@Query("section") section:String,@Query("session") session:String,@Query("page_size") pageSize:String): AttendanceUpdateListResponse

    @POST("teacher_student_attendence_post/")
    suspend fun _setAttendanceUpdate(@Header("Authorization") authToken:String,@Body requestBody: UpdateAttendanceRequest): AttendanceUpdateCheckResponse

    @Multipart
    @POST("teacher_announcement_post/")
    suspend fun _postAnnouncement(@Header("Authorization") authToken:String,
                                  @Part ("title") title: RequestBody,
                                  @Part ("description") description:RequestBody,
                                  @Part("date") date:RequestBody,
                                  @Part ("to_school_or_class") to_school_or_class:RequestBody,
                                  @Part ("scl_class") scl_class:RequestBody,
                                  @Part ("cls_section") cls_section:RequestBody,
                                  @Part("student") student:RequestBody,
                                  @Part image1: MultipartBody.Part,
                                  @Part image2: MultipartBody.Part,
                                  @Part image3: MultipartBody.Part,

    ): AttendanceUpdateCheckResponse

}