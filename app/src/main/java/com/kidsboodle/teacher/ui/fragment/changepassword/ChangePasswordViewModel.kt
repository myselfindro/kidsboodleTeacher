package com.kidsboodle.teacher.ui.fragment.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.changepwd.ChangePwdRequest
import com.kidsboodle.teacher.data.model.forgotpwd.EmailVerifyRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordRequest
import com.kidsboodle.teacher.data.model.forgotpwd.MobileVerifyRequest
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class ChangePasswordViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun setChangePassword(token: String, requestBody: ChangePwdRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.setChangePassword(token,requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


}