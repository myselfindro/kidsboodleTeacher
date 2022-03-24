package com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.attendancelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.UpdateStudentAttendanceListRecyclerAdapter
import com.kidsboodle.teacher.adapters.ViewAttendanceListRecyclerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.StudentsAttendanceItem
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.ViewStudentsAttendanceListItem
import com.kidsboodle.teacher.databinding.FragmentUpdateAttendanceListBinding
import com.kidsboodle.teacher.databinding.FragmentViewAttendanceListBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendanceViewModel
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.StudentAttendenceViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.ViewAttendanceFragment.Companion._selectedClass
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.ViewAttendanceFragment.Companion._selectedEndDate
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.ViewAttendanceFragment.Companion._selectedSection
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.ViewAttendanceFragment.Companion._selectedStartDate
import com.kidsboodle.teacher.utility.*

class ViewAttendanceListFragment : Fragment() {

    private var _binding: FragmentViewAttendanceListBinding? = null

    private val binding get() = _binding!!

    private val list: ArrayList<ViewStudentsAttendanceListItem> = ArrayList()

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
        _binding = FragmentViewAttendanceListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm: StudentAttendanceViewModel by viewModels {
            StudentAttendenceViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        viewModel = vm
        getAttendanceList()
     //   makeList()
     //   setUpList()


    }

    private fun makeList() {
        val item1 = ViewStudentsAttendanceListItem("20036","Rahul Kaushik","10/20")
        val item2 = ViewStudentsAttendanceListItem("05","Sohom Ghosh","15/20")
        val item3 = ViewStudentsAttendanceListItem("21203","Preeti Kumari","100/200")
        val item4 = ViewStudentsAttendanceListItem("200","Rahul Kaushik","180/356")

        list.addAll(listOf(item1, item2, item3, item4))
    }

    private fun setUpList() {
       val rvAdapter =
            ViewAttendanceListRecyclerAdapter(requireContext(), list, findNavController())
        binding.rvViewAttendanceList.adapter = rvAdapter
        binding.rvViewAttendanceList.layoutManager = LinearLayoutManager(requireContext())

        rvAdapter.notifyDataSetChanged()
    }

    private fun getAttendanceList(auth:String = PreferenceManager.getUserToken(requireContext()), sessionID:String = PreferenceManager.getSessionId(requireContext())!!)
    {
        var loader: KProgressHUD? = null
        viewModel.getTeacherStudentDetails(auth,
            _selectedClass,
            _selectedSection,
            sessionID,
            _selectedStartDate,
            _selectedEndDate)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            list.clear()

                            if (resource.data?.requestStatus?.equals(1) == true) {

                                   for (i in it.data?.resultsList!!){
                                       list.add(ViewStudentsAttendanceListItem(i?.rollNumber!!,i.studentName!!,i.studentAttendanceDetails?.absentCount.toString()+"/"+i.studentAttendanceDetails?.countTotal.toString()))
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
}