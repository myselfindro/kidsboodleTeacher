package com.kidsboodle.teacher.ui.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.repository.MainRepository

class LoginViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        LoginViewModel(MainRepository(apiHelper)) as T
}