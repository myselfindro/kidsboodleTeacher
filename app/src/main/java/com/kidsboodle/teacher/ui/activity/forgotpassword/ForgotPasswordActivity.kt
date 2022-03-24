package com.kidsboodle.teacher.ui.activity.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.forgotpwd.EmailVerifyRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordPhoneRequest
import com.kidsboodle.teacher.data.model.forgotpwd.ForgotPasswordRequest
import com.kidsboodle.teacher.data.model.forgotpwd.MobileVerifyRequest
import com.kidsboodle.teacher.databinding.ActivityForgotPasswordBinding
import com.kidsboodle.teacher.ui.activity.login.LoginActivity
import com.kidsboodle.teacher.ui.activity.login.LoginViewModel
import com.kidsboodle.teacher.ui.activity.login.LoginViewModelFactory
import com.kidsboodle.teacher.ui.activity.login.SuccessfulLoginActivity
import com.kidsboodle.teacher.utility.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModelFP: ForgotPasswordViewModel
    private var dataOTP = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val vm: ForgotPasswordViewModel by viewModels {
            FPViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }

        viewModelFP = vm
        initClicks()
    }

    private fun initClicks() {

        binding.ivForgotPassNextBtn.setOnClickListener {
            val slideOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
            val slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
            val userEmail = binding.edtEmailId.text.toString()
            val mobileNo = binding.edtUserMobileNumber.text.toString()

            if (!isContentValid(userEmail, binding.edtEmailId) && !isContentValid(mobileNo, binding.edtUserMobileNumber))
            {
                Toast.makeText(applicationContext,"Please enter email or mobile no.",Toast.LENGTH_SHORT).show()
            }
            else if (isContentValid(userEmail, binding.edtEmailId)) {
                checkEmailValidation(userEmail, slideOutAnim, slideInAnim)
            } else  if (isContentValid(mobileNo, binding.edtUserMobileNumber)) {
                checkMobileValidation(mobileNo, slideOutAnim, slideInAnim)
            }


        }

        binding.btnConfirm.setOnClickListener {
            val otpString  = binding.edtEnterOtp.text.toString()
            dataOTP.observe(this, Observer {
                setNewPassword()
              /*  if(dataOTP.value.equals(otpString))
                setNewPassword()
                else
                Toast.makeText(applicationContext,"OTP does not match",Toast.LENGTH_SHORT).show()*/
            })

        }

        binding.ivForgotPassBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setNewPassword() {
        val userEmail = binding.edtEmailId.text.toString()
        val mobileNo = binding.edtUserMobileNumber.text.toString()
        val newPWD = binding.edtNewPassword.text.toString()
        val confirmPWD = binding.edtConfirmPassword.text.toString()

        var loader: KProgressHUD? = null
        if(userEmail!="") {
            viewModelFP.setNewPassword(
                ForgotPasswordRequest(
                    emailId = userEmail,
                    newPassword = newPWD,
                    confirmPassword = confirmPWD
                )
            )
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideLoader(loader)
                                if (resource.data?.requestStatus?.equals(1) == true) {
                                    clearSessionLocally(this)
                                    this.goToActivity(LoginActivity::class.java, true)
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
        else
        {
            viewModelFP.setNewPasswordPhone(
                ForgotPasswordPhoneRequest(
                    phone = mobileNo,
                    newPassword = newPWD,
                    confirmPassword = confirmPWD
                )
            )
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideLoader(loader)
                                if (resource.data?.requestStatus?.equals(1) == true) {
                                    clearSessionLocally(this)
                                    this.goToActivity(LoginActivity::class.java, true)
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

    private fun checkMobileValidation(
        mobileNo: String,
        slideOutAnim: Animation,
        slideInAnim: Animation
    ) {
        var loader: KProgressHUD? = null
        viewModelFP.verifyMobile(MobileVerifyRequest(mobileNo = mobileNo))
            .observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                binding.llForgotPasswordMain.hide()
                                binding.tvForgotPassword.hide()
                                binding.llForgotPasswordMain.startAnimation(slideOutAnim)

                                binding.llForgotPasswordOTP.show()
                                binding.tvChangePassword.show()
                                binding.llForgotPasswordOTP.startAnimation(slideInAnim)
                               dataOTP.value =  resource.data.resultMobile?.otp.toString()
                                binding.root.showSnackBar("OTP sent to Mobile")
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            println("eror ${it.message}")
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = this.showLoader()
                        }
                    }
                }
            }

    }

    private fun checkEmailValidation(
        userEmail: String,
        slideOutAnim: Animation,
        slideInAnim: Animation
    ) {
        var loader: KProgressHUD? = null
        viewModelFP.verifyEmail(EmailVerifyRequest(emailId = userEmail))
            .observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                binding.llForgotPasswordMain.hide()
                                binding.tvForgotPassword.hide()
                                binding.llForgotPasswordMain.startAnimation(slideOutAnim)

                                binding.llForgotPasswordOTP.show()
                                binding.tvChangePassword.show()
                                binding.llForgotPasswordOTP.startAnimation(slideInAnim)
                                dataOTP.value =  resource.data.resultEmailVerify?.otp.toString()
                                binding.root.showSnackBar("OTP sent to Email")
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