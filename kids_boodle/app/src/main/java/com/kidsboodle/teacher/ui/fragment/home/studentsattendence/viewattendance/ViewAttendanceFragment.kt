package com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.databinding.FragmentViewAttendanceBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendanceViewModel
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendenceViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment
import com.kidsboodle.teacher.utility.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class ViewAttendanceFragment : Fragment() {

    private var _binding: FragmentViewAttendanceBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: StudentAttendanceViewModel

    private var classList:ArrayList<String> =ArrayList()
    private var classListsValue:ArrayList<String> =ArrayList()
    private var sectionList:ArrayList<String> =ArrayList()
    private var sectionListValue:ArrayList<String> =ArrayList()
    private var classListPosition:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
//        (context as MainActivity).makeScreenTitleToLeft(true)
    }
    companion object{
        var _selectedClass : String =""

        var _selectedSection : String =""

        var _selectedSubject : String =""

        var _selectedStartDate : String =""

        var _selectedEndDate : String =""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).displayHomeUpORHamburger(false)
        _binding = FragmentViewAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm: StudentAttendanceViewModel by viewModels {
            StudentAttendenceViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        viewModel = vm

        getStudentClassList()

        val cal = Calendar.getInstance()

        val startDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.tvStartDate.text = sdf.format(cal.time)
            _selectedStartDate =  binding.tvStartDate.text.toString()

        }

        val endDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.tvEndDate.text = sdf.format(cal.time)
            _selectedEndDate = binding.tvEndDate.text.toString()

        }

        binding.tvStartDate.setOnClickListener {
            DatePickerDialog(requireContext(), startDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

        }

        binding.tvEndDate.setOnClickListener {
            DatePickerDialog(requireContext(), endDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnViewAttendance.setOnClickListener {
            findNavController().navigate(R.id.action_studentsAttendanceFragment_to_viewAttendanceListFragment)
        }
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
                                        _selectedClass = classListsValue[position]
                                        getStudentSectionList(Class = classListsValue[position],section = "A" ) //section filter is not working as of now
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
    fun getStudentSectionList(auth:String = PreferenceManager.getUserToken(requireContext()), Class:String, section:String){
        var loader: KProgressHUD? = null
        viewModel.getTeacherClassSectionList(auth,Class, section)
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
                                val sectionAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_menu_item,sectionList)
                                binding.autoCompleteSelectSection.setAdapter(sectionAdapter)


                                binding.autoCompleteSelectSection.onItemClickListener=
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position ")
                                        _selectedSection = sectionListValue[position]
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