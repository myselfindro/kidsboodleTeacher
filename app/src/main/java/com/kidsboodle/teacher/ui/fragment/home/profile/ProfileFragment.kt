package com.kidsboodle.teacher.ui.fragment.home.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.AnnouncementHistoryRecyclerAdapter
import com.kidsboodle.teacher.adapters.ExamsRecyclerAdapter
import com.kidsboodle.teacher.adapters.HobbiesRecyclerAdapter
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.profile.ExamsItem
import com.kidsboodle.teacher.data.model.homefragment.profile.HobbiesItem
import com.kidsboodle.teacher.databinding.*
import com.kidsboodle.teacher.ui.activity.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val examsList:ArrayList<ExamsItem> = ArrayList()
    private val hobbiesList:ArrayList<HobbiesItem> = ArrayList()

    private val binding get() = _binding!!

    val args:ProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).changeBackButton()
        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle("Little Star")

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpProfile()
        makeExams()
        setUpExamsRecyclerAdapter()
        setUpHobbiesRecyclerAdapter()
    }

    private fun setUpProfile() {
        binding.tvStudentName.text = args.studentDetails.studentName
        binding.tvStudentClass.text = args.studentDetails.studentClass
        binding.tvFatherName.text = args.studentDetails.fathersName
        binding.tvMotherName.text = args.studentDetails.mothersName
        binding.tvMailId.text = args.studentDetails.mailId
        binding.tvContactInfo.text =args.studentDetails.contactInfo
        binding.tvPermanentAddress.text = args.studentDetails.permanentAddress
        binding.tvGender.text = args.studentDetails.gender
        binding.tvDateOfBirth.text = args.studentDetails.dateOfBirth
        binding.tvSchoolName.text = args.studentDetails.schoolName
        binding.tvDateOfJoiningInSchool.text = args.studentDetails.dateOfJoining
        binding.tvAttendancePerformancePercent.text = args.studentDetails.attendancePerformancePercent
        binding.horizontalProgressBar.setProgress(50)
        binding.tvAveragePerformance.text = args.studentDetails.averagePerformance
        binding.tvClassTeacher.text=args.studentDetails.classTeacher

        args.studentDetails.examsList?.let { examsList.addAll(it) }

        args.studentDetails.hobbiesList?.let {
            hobbiesList.addAll(it)
        }
//        binding.tvStudentClass.text = "VII-A"
//        binding.tvFatherName.text = "Sukhwinder Singh"
//        binding.tvMotherName.text = "Preet Singh"
//        binding.tvMailId.text = "xysjsj@gmail.com"
//        binding.tvContactInfo.text = "456321563231565"
//        binding.tvPermanentAddress.text = "NSB road, near Mahatma Gandhi statue"
//        binding.tvSecondaryAddress.text = "NSB road, near Mahatma Gandhi statue"
//        binding.tvSchoolName.text = "Little Star"
//        binding.tvDateOfJoiningInSchool.text = "21.10.2020"
//        binding.tvAttendancePerformancePercent.text = "50%"
//        binding.horizontalProgressBar.setProgress(50)
//        binding.tvAveragePerformance.text = "Great"

        binding.ivStar1.setImageResource(R.drawable.ic_filled_yellow_star)
        binding.ivStar2.setImageResource(R.drawable.ic_filled_yellow_star)
        binding.ivStar3.setImageResource(R.drawable.ic_filled_yellow_star)
        binding.ivStar4.setImageResource(R.drawable.ic_filled_yellow_star)
    }

    private fun makeExams() {
//        val item1=ExamsItem("Class Test 1","40")
//
//        val item2=ExamsItem("Half Yearly Exam","80")
//
//        val item3=ExamsItem("Annual Exam","100")
//        val item4=ExamsItem("Annual Exam","100")
//        val item5=ExamsItem("Annual Exam","100")
//        val item6=ExamsItem("Annual Exam","100")
//        val item7=ExamsItem("Annual Exam","100")
//        val item8=ExamsItem("Annual Exam","100")

//        examsList.addAll(listOf(item1,item2,item3,item4,item5,item6,item7,item8))

    }

    private fun setUpExamsRecyclerAdapter() {
        val adapter= ExamsRecyclerAdapter(requireContext(),examsList,findNavController())
        binding.rvProfileExamsHistory.adapter=adapter
        binding.rvProfileExamsHistory.layoutManager=LinearLayoutManager(requireContext())
    }

    private fun setUpHobbiesRecyclerAdapter() {
        val adapter= HobbiesRecyclerAdapter(requireContext(),hobbiesList,findNavController())
        binding.rvProfileHobbies.adapter=adapter
        binding.rvProfileHobbies.layoutManager=LinearLayoutManager(requireContext())
    }


}