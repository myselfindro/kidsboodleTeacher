package com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScrollCaptureSession
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.databinding.FragmentUpdateAttendanceBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.gradecard.GradeCardFragment
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendanceViewModel
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendenceViewModelFactory
import com.kidsboodle.teacher.utility.*

class UpdateAttendanceFragment : Fragment() {

    private var _binding: FragmentUpdateAttendanceBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: StudentAttendanceViewModel

    private var classList:ArrayList<String> =ArrayList()
    private var classListsValue:ArrayList<String> =ArrayList()
    private var sectionList:ArrayList<String> =ArrayList()
    private var sectionListValue:ArrayList<String> =ArrayList()
    private var subjectList:ArrayList<String> =ArrayList()
    private var subjectListID:ArrayList<String> =ArrayList()
    private var classListPosition:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).makeScreenTitleToLeft(true)
    }
    companion object{
         var selectedClass : String =""

         var selectedSection : String =""

         var selectedSubject : String =""
        var session=""

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GradeCardFragment.session =PreferenceManager.getUserToken(requireContext())

        _binding = FragmentUpdateAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheckAttendance.setOnClickListener {
            getAttendanceUpdateSubmit()

        }
        val vm: StudentAttendanceViewModel by viewModels {
            StudentAttendenceViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        viewModel = vm


        getStudentClassList()
    }

    private fun getStudentClassList(auth:String = PreferenceManager.getUserToken(requireContext()))
    {
        var loader: KProgressHUD? = null
        viewModel.getTeacherStudentClassList(auth)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            classList.clear()
                            classListsValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {

                                for (i in it.data?.result!!){
                                    classList.add("Class - ${i?.className}")
                                    classListsValue.add(i?.id!!.toString())
                                }

                                val classAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_menu_item, classList)
                                binding.autoCompleteSelectClass.setAdapter(classAdapter)

                                binding.autoCompleteSelectClass.onItemClickListener=
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedClass= classListsValue[position]
                                        getStudentSectionList(Class = classListsValue[position],section = session , page = 1, pageSize =  25) //section filter is not working as of now
                                    }

                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }
    fun getStudentSectionList(auth:String = PreferenceManager.getUserToken(requireContext()),Class:String,section:String, page:Int, pageSize:Int){
        var loader: KProgressHUD? = null
        viewModel.getTeacherClassSectionList(auth,Class, section, page, pageSize)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            sectionList.clear()
                            sectionListValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {

                                for(i in it.data?.result!!){
                                    sectionList.add("Section - ${i?.sectionName!!}")
                                    sectionListValue.add(i.id.toString())
                                }
                                val sectionAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,sectionList)
                                binding.autoCompleteSelectSection.setAdapter(sectionAdapter)


                                binding.autoCompleteSelectSection.onItemClickListener=
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position ")
                                        selectedSection = sectionListValue[position]
                                        getSubjectClassSection(Class = classListsValue[position],section = sectionListValue[position] ) //section filter is not working as of now
                                    }

                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }

    fun getSubjectClassSection(auth:String = PreferenceManager.getUserToken(requireContext()), Class:String,section:String){
        var loader: KProgressHUD? = null
        viewModel.getSubjectOfClassSection(auth,Class, section)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            subjectList.clear()
                            subjectListID.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {

                                for(i in it.data?.result!!){
                                    subjectList.add(i?.subjectClasswise!!)
                                    subjectListID.add(i.id.toString())
                                }
                                val subjectAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,subjectList)
                                binding.autoCompleteSelectSubject.setAdapter(subjectAdapter)

                                binding.autoCompleteSelectSubject.onItemClickListener=
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedSubject = subjectListID[position]
                                        System.out.println("selectedSubject $selectedSubject")
                                    }
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }

    fun getAttendanceUpdateSubmit(auth:String = PreferenceManager.getUserToken(requireContext()),attendanceType:String = PreferenceManager.getSchoolAttendanceType(requireContext())!!, sessionID:String = PreferenceManager.getSessionId(requireContext())!!)
    {
        var loader: KProgressHUD? = null
        viewModel.CheckAttendenceSubmission(auth,
            sessionID,
            selectedClass,
            selectedSection,
            attendanceType,selectedSubject )
            .observe( viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            if (resource.data?.requestStatus?.equals(0)==true) {
                                findNavController().navigate(R.id.action_studentsAttendanceFragment_to_updateAttendanceListFragment)
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }
}