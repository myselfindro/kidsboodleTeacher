package com.kidsboodle.teacher.ui.fragment.account

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.teacherprofile.TeacherProfileResponse
import com.kidsboodle.teacher.databinding.AccountFragmentBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.utility.*
import java.util.HashMap


class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var userAddress:String
    private lateinit var userPhone:String

    private var _binding: AccountFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context as MainActivity).changeToolbarTitle("Little Star")
        (context as MainActivity).displayHomeUpORHamburger(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (context as MainActivity).displayHomeUpORHamburger(true)

        val vm: AccountViewModel by viewModels {
            AccountViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }

        viewModel=vm

        _binding = AccountFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)


        getTeacherDetails()

        binding.ivEditPersonalInformation.setOnClickListener {
            showDialog()
        }
//        setUpTheAccount()
    }

    private fun getTeacherDetails() {
        var loader: KProgressHUD? = null
        viewModel.getTeacherProfileDetails(PreferenceManager.getUserToken(requireContext()))
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                        setUpTheAccount(resource.data)
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR ->{
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING ->{
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }

    private fun setUpTheAccount(data: TeacherProfileResponse) {
        binding.tvTeacherName.text=data.results?.firstName + " "+ data.results?.lastName
        binding.tvTeacherDesignation.text=data.results?.education
        binding.tvMailId.text=data.results?.email
        binding.tvContactInfo.text=data.results?.phone
        binding.tvPermanentAddress.text=data.results?.address1
        userAddress=data.results?.address1!!
        userPhone=data.results?.phone!!
//        binding.tvSchoolName.text="Little Star"
        binding.tvDateOfJoiningInSchool.text=data.results?.doj

        binding.tvSubject1.visibility=View.VISIBLE
        binding.tvSubject1.text="Class : ${data.results?.teacherSubject?.get(0)?.sclClassClassName}(${data.results?.teacherSubject?.get(0)?.clsSectionSectionName}) - ${data.results?.teacherSubject?.get(0)?.subjectSubject}"

        Glide.with(requireContext()).load(data.results?.image).into(binding.civProfile)
//        binding.tvSubject2.visibility=View.VISIBLE
//        binding.tvSubject2.text="Chemistry"
    }

    fun showDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val mView = layoutInflater.inflate(R.layout.edit_profile_dialog, null)
        val contactNumber = mView.findViewById(R.id.etContactNumber) as EditText
        val address = mView.findViewById(R.id.etAddress) as EditText
        val btnCancel: TextView = mView.findViewById(R.id.ivCancel)
        val btnUpdate: MaterialButton = mView.findViewById(R.id.btnUpdate)
        alert.setView(mView)
        val alertDialog: AlertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(false)

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        btnUpdate.setOnClickListener {
            if (contactNumber.text.toString().isNotEmpty() || address.text.toString().isNotEmpty()){
                if (contactNumber.text.toString().isNotEmpty()){
                    if (ReusableFunctions.isPhoneNumberValid(contactNumber.text.toString(),contactNumber)){
                        updateProfile(alertDialog,address.text.toString(),contactNumber.text.toString())
                    }
                }else{
                    updateProfile(alertDialog,address.text.toString(),contactNumber.text.toString())
                }
            }
//            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun updateProfile(alertDialog: AlertDialog,address1:String,phone:String) {

        val requestBody: MutableMap<String, Any> = HashMap()
        if(address1.isEmpty()){
            requestBody["address1"] = userAddress
        }
        else{
            requestBody["address1"] = address1
        }

        if(phone.isEmpty()){
            requestBody["phone"] = userPhone
        }
        else{
            requestBody["phone"] = phone
        }


        var loader: KProgressHUD? = null
        viewModel.updateProfileDetails(PreferenceManager.getUserToken(requireContext()),requestBody)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                alertDialog.dismiss()
                                Toast.makeText(requireContext(),"Successfully updated",Toast.LENGTH_SHORT).show()
                                getTeacherDetails()

                            } else binding.root.showSnackBar(resource.data?.results?.msg)
                        }
                        Status.ERROR ->{
                            hideLoader(loader)
                            alertDialog.dismiss()
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING ->{
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroyed","Destroyed")
    }

}