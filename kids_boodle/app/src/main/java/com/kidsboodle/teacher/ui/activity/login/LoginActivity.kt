package com.kidsboodle.teacher.ui.activity.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.databinding.ActivityLoginBinding
import com.kidsboodle.teacher.ui.activity.forgotpassword.ForgotPasswordActivity
import com.kidsboodle.teacher.utility.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val vm: LoginViewModel by viewModels {
            LoginViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }

        viewModel=vm

        initClicks()
    }

    private fun initClicks() {

        binding.tvForgotPassword.setOnClickListener {
            this.goToActivity(ForgotPasswordActivity::class.java, false)
        }

        binding.btnLogin.setOnClickListener {
//            this.goToActivity(SuccessfulLoginActivity::class.java, true)
            val username=binding.edtTeacherUserId.text.toString()
            val password=binding.edtTeacherPassword.text.toString()
            if(isContentValid(username,binding.edtTeacherUserId) && isContentValid(password,binding.edtTeacherPassword)){
                login(username,password)
            }
        }
    }

    private fun login(username:String,password:String){
        var loader: KProgressHUD? = null
        viewModel.login(LoginRequest(username = username,password = password))
            .observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                PreferenceManager.saveToken(resource.data.result?.token!!, this)
                                PreferenceManager.saveUserDetails(
                                    resource.data.result.userDetails?.schoolName!!,
                                    resource.data.result.userDetails.schoolAttendenceType!!,
                                    this
                                )
                                PreferenceManager.saveUserSession(
                                    resource.data.result.userDetails.userSession?.id.toString(),
                                    this
                                )

                                PreferenceManager.saveLoginStatus(
                                    true,
                                    resource.data.result.tokenExpiry!!,
                                    this
                                )
                                this.goToActivity(SuccessfulLoginActivity::class.java, true)
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = this.showLoader()
                        }
                    }
                }
            }
    }
}