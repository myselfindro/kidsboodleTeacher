package com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.updatelist

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.UpdateStudentAttendanceListRecyclerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.StudentsAttendanceItem
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.UpdateAttendanceRequest
import com.kidsboodle.teacher.databinding.FragmentUpdateAttendanceListBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendanceViewModel
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendenceViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment.Companion.selectedClass
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment.Companion.selectedSection
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment.Companion.selectedSubject
import com.kidsboodle.teacher.utility.*
import com.kidsboodle.teacher.utility.ReusableFunctions.getCurrentDate
import org.json.JSONObject

class UpdateAttendanceListFragment : Fragment() {

    private var _binding: FragmentUpdateAttendanceListBinding? = null

    private val binding get() = _binding!!

    private lateinit var rvAdapter: UpdateStudentAttendanceListRecyclerAdapter

    private val studentAttendUpdateList: ArrayList<StudentsAttendanceItem> = ArrayList()

    companion object{
        var setAttendUpdateList: ArrayList<JSONObject> = ArrayList()
    }



    private lateinit var viewModel: StudentAttendanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle("Little Star")
        setHasOptionsMenu(true)
//        (context as MainActivity).makeScreenTitleToLeft(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).displayHomeUpORHamburger(false)
        _binding = FragmentUpdateAttendanceListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  makeList()
        val vm: StudentAttendanceViewModel by viewModels {
            StudentAttendenceViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        viewModel = vm



        getAttendanceUpdateList()

        binding.checkBoxSelectAll.setOnClickListener{
            if (binding.checkBoxSelectAll.isChecked) {
                rvAdapter.selectAll()
            } else {
                rvAdapter.deselectAll()
            }
        }

        binding.btnSubmitAttendance.setOnClickListener {
            println("setAttendUpdateList==> $setAttendUpdateList")
            setUpdatedList()
        }
    }

    private fun setUpdatedList(auth:String = PreferenceManager.getUserToken(requireContext())) {
        var loader: KProgressHUD? = null

        viewModel.setAttendanceUpdate(auth,UpdateAttendanceRequest(attendanceList = setAttendUpdateList))
            .observe( viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            if (resource.data?.requestStatus?.equals(1) == true) {
                                getAttendanceUpdateList()

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
            }
    }


    fun getAttendanceUpdateList(auth:String = PreferenceManager.getUserToken(requireContext()), sessionID:String = PreferenceManager.getSessionId(requireContext())!!){
        var loader: KProgressHUD? = null
        viewModel.getTeacherStudentDetailsUpdate(auth,
            selectedClass,
            selectedSection,
            sessionID
            )
            .observe( viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for(i in it.data?.resultsList!!)
                              {
                                  studentAttendUpdateList.add(StudentsAttendanceItem(i?.studentName!!,i?.rollNumber!!,false,"",i.id.toString(),
                                      selectedSubject,PreferenceManager.getSchoolAttendanceType(requireContext())!!,i.cls_section!!,i.scl_class!!,getCurrentDate()!!))
                              }
                                setUpList()

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



   /* private fun makeList() {
        val item1 = StudentsAttendanceItem("Subhajit Das", "07", false)
        val item2 = StudentsAttendanceItem("Sohom Dutt", "03", false)
        val item3 = StudentsAttendanceItem("Avijit Kumar", "12", false)
        val item4 = StudentsAttendanceItem("Subhajit Das", "07", false)

        studentAttendUpdateList.addAll(listOf(item1, item2, item3, item4))
    }*/

    private fun setUpList() {
        rvAdapter =
            UpdateStudentAttendanceListRecyclerAdapter(requireContext(), studentAttendUpdateList, findNavController())
        binding.rvUpdateAttendanceList.adapter = rvAdapter
        binding.rvUpdateAttendanceList.layoutManager = LinearLayoutManager(requireContext())
        rvAdapter.notifyDataSetChanged()

    }


}