package com.kidsboodle.teacher.ui.fragment.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.changepwd.ChangePwdRequest
import com.kidsboodle.teacher.databinding.ActivityChangePasswordBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.activity.login.LoginActivity
import com.kidsboodle.teacher.utility.*

class ChangePasswordFragment : Fragment() {
    private var _binding: ActivityChangePasswordBinding ?= null
    private lateinit var viewModelFP: ChangePasswordViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).changeBackButton()
        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle("Little Star")

        _binding = ActivityChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm: ChangePasswordViewModel by viewModels {
            CPViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }

        viewModelFP = vm

        initClicks()
    }
    private fun initClicks() {
        binding.btnConfirm.setOnClickListener{
            val oldPassword= binding.edtEnterOldPwd.text.toString()
            val newPassword= binding.edtNewPassword.text.toString()
            if (isContentValid(oldPassword, binding.edtEnterOldPwd)   && isContentValid(newPassword, binding.edtNewPassword) ) {
                setChangePassword(oldPassword,newPassword)
            }
        }
    }
    private fun setChangePassword(oldPassword: String, newPassword: String) {
        var loader: KProgressHUD? = null
        print("authToken ${PreferenceManager.getUserToken( activity!!) }")
        viewModelFP.setChangePassword(PreferenceManager.getUserToken( activity!!),ChangePwdRequest(oldPassword =oldPassword, newPassword = newPassword ))
            .observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                clearSessionLocally(activity!!)
                                activity?.goToActivity(LoginActivity::class.java, true)

                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = activity?.showLoader()
                        }
                    }
                }
            }

    }
}