package com.kidsboodle.teacher.ui.activity.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.forgotpwd.EmailVerifyRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordPhoneRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordRequest
import com.kidsboodle.teacher.data.model.forgotpwd.MobileVerifyRequest
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class ForgotPasswordViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun verifyEmail(requestBody: EmailVerifyRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.checkEmailExist(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun verifyMobile(requestBody: MobileVerifyRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.checkMobileExist(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun setNewPassword(requestBody: ForgotPasswordRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.setForgotPassword(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
    fun setNewPasswordPhone(requestBody: ForgotPasswordPhoneRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.setForgotPasswordPhone(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


}