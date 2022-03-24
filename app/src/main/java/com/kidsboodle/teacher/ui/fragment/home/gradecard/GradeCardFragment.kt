package com.kidsboodle.teacher.ui.fragment.home.gradecard

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.forgotpwd.GetGradeRequest
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.databinding.FragmentGradecardBinding
import com.kidsboodle.teacher.databinding.FragmentStudentsBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementViewModel
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.utility.*


class GradeCardFragment : Fragment() {
    private var classList: ArrayList<String> = ArrayList()
    private var classListsValue: ArrayList<String> = ArrayList()
    private var sectionList: ArrayList<String> = ArrayList()
    private var sectionListValue: ArrayList<String> = ArrayList()
    private var studentList: ArrayList<String> = ArrayList()
    private var studentListID: ArrayList<String> = ArrayList()
    private var subjectList: ArrayList<String> = ArrayList()
    private var subjectListID: ArrayList<String> = ArrayList()

    private lateinit var createAnnouncementViewModel: CreateAnnouncementViewModel;

    companion object {
        var selectedClass: String = ""

        var selectedSection: String = ""

        var selectedSubject: String = ""

        var selectedStudent=""

        var session:String?=""
    }

    private var _binding: FragmentGradecardBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
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

//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)

        (context as MainActivity).displayHomeUpORHamburger(true)
        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).makeScreenTitleToLeft(true)

        session =PreferenceManager.getSessionId(requireContext())
        _binding = FragmentGradecardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm: CreateAnnouncementViewModel by viewModels {
            CreateAnnouncementViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        createAnnouncementViewModel = vm
        getStudentClassList()

        binding.btnCheckGradeCard.setOnClickListener {
            if(!selectedClass.isEmpty())
                getGrade()
            else
                binding.root.showSnackBar("Please select mandatory fields")
        }
    }

    private fun getGrade() {
        val auth: String = PreferenceManager.getUserToken(requireContext())
        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getGrade(auth, 10, selectedSubject, selectedClass, selectedSection, selectedStudent)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            classList.clear()
                            classListsValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                if(resource.data?.result?.size!! >0)
                                binding.root.showSnackBar(resource.data?.result?.get(0)?.student_name+" has obtained"+resource.data?.result?.get(0)?.total_marks_obtained.toString())
                                else binding.root.showSnackBar("No data found")

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
    private fun getStudentList(auth:String=PreferenceManager.getUserToken(requireContext())){

        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherStudentList(auth)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            studentList.clear()
                            studentListID.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for (i in it.data?.result!!) {
                                    studentList.add("Student - ${i?.className}")
                                    studentListID.add(i?.className!!)
                                }

                                val classAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    studentList
                                )
                                binding.autoCompleteSelectStudent.setAdapter(classAdapter)

                                binding.autoCompleteSelectStudent.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedStudent = studentListID[position]
                                        getExamList()
                                         //section filter is not working as of now
                                        //section filter is not working as of now
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

    private fun getStudentClassList(auth: String = PreferenceManager.getUserToken(requireContext())) {
        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherStudentClassList(auth)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            classList.clear()
                            classListsValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for (i in it.data?.result!!) {
                                    classList.add("Class - ${i?.className}")
                                    classListsValue.add(i?.id!!.toString())
                                }

                                val classAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    classList
                                )
                                binding.autoCompleteClass.setAdapter(classAdapter)

                                binding.autoCompleteClass.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedClass = classListsValue[position]
                                        getStudentSectionList(
                                            Class = classListsValue[position],
                                            section = session!!,
                                            page = 1,
                                            pageSize = 25
                                        ) //section filter is not working as of now
                                        //section filter is not working as of now
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

    private fun getStudentSectionList(
        auth: String = PreferenceManager.getUserToken(requireContext()),
        Class: String,
        section: String,
        page:Int,
        pageSize: Int
    ) {
        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherClassSectionList(auth, Class, section, page, pageSize)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            sectionList.clear()
                            sectionListValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {

                                for (i in it.data?.result!!) {
                                    sectionList.add("Section - ${i?.sectionName!!}")
                                    sectionListValue.add(i.id.toString())
                                }
                                val sectionAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    sectionList
                                )
                                binding.autoCompleteSection.setAdapter(sectionAdapter)


                                binding.autoCompleteSection.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position ")
                                        selectedSection = sectionListValue[position]
                                        //section filter is not working as of now
                                        getStudentList()
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
    private fun getExamList(auth:String=PreferenceManager.getUserToken(requireContext())){

     var loader: KProgressHUD? = null
        createAnnouncementViewModel.getExamTypeList(auth, 10)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            subjectList.clear()
                            subjectListID.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for (i in it.data?.result!!) {
                                    subjectList.add(i?.exam_type!!)
                                    subjectListID.add(i?.id.toString())
                                }

                                val classAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    subjectList
                                )
                                binding.autoCompleteSelectExam.setAdapter(classAdapter)

                                binding.autoCompleteSelectExam.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedSubject = subjectListID[position]
                                        //section filter is not working as of now
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



}