package com.kidsboodle.teacher.ui.activity.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.repository.MainRepository

class FPViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ForgotPasswordViewModel(MainRepository(apiHelper)) as T
}