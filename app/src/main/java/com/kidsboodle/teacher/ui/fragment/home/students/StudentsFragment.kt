package com.kidsboodle.teacher.ui.fragment.home.students

import android.os.Bundle
import android.text.InputType
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
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.databinding.FragmentStudentsBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.HomeViewModel
import com.kidsboodle.teacher.ui.fragment.home.HomeViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.utility.*
import kotlin.properties.Delegates


class StudentsFragment : Fragment() {
    var session:String?=null

    private var _binding: FragmentStudentsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: StudentsViewModel

    private var classList:ArrayList<String> =ArrayList()
    private var classListsValue:ArrayList<String> =ArrayList()
    private var sectionList:ArrayList<String> =ArrayList()
    private var classListPosition:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
//        (context as MainActivity).displayHomeUpORHamburger(false)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vm: StudentsViewModel by viewModels {
            StudentsViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        viewModel = vm

//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)

        (context as MainActivity).displayHomeUpORHamburger(false)

        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).makeScreenTitleToLeft(true)
        session=PreferenceManager.getSessionId(requireContext())
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStudentClassList()
        binding.btnCheck.setOnClickListener {
            findNavController().navigate(R.id.action_studentsFragment_to_studentCheckFragment)
        }
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
                            if (resource.data?.requestStatus?.equals(1) == true) {

                                for(i in it.data?.result!!){
                                    sectionList.add("Section - ${i?.sectionName!!}")
                                }
                                val sectionAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,sectionList)
                                binding.autoCompleteSelectSection.setAdapter(sectionAdapter)

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

    private fun getStudentClassList(auth:String = PreferenceManager.getUserToken(requireContext())){
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
                                        getStudentSectionList(Class = classListsValue[position],section =  session!!, page = 1, pageSize = 25) //section filter is not working as of now
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

    override fun onResume() {
        super.onResume()
        val classAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_menu_item, classList)
        binding.autoCompleteSelectClass.setAdapter(classAdapter)

        val sectionAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,sectionList)
        binding.autoCompleteSelectSection.setAdapter(sectionAdapter)
    }



}