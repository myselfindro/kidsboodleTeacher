package com.kidsboodle.teacher.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.DashBoardItemsRecyclerAdapter
import com.kidsboodle.teacher.adapters.DashboardNotificationsRecyclerAdapter
import com.kidsboodle.teacher.adapters.NoticeRecyclerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.*
import com.kidsboodle.teacher.databinding.FragmentHomeBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.utility.*
import com.kidsboodle.teacher.utility.ReusableFunctions.getCurrentDate
import com.kidsboodle.teacher.utility.ReusableFunctions.getCurrentTime
import kotlin.collections.ArrayList


private const val DELAY_BETWEEN_SCROLL_MS = 25L
private const val SCROLL_DX = 5
private const val DIRECTION_RIGHT = 1

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private var listOfDashboardItems: ArrayList<DashboardItems> = ArrayList()
    private var listOfNotice: ArrayList<DashBoardNoticeItem> = ArrayList()
    private var listOfNotifications: ArrayList<DashBoardNotificationItem> = ArrayList()

    private var linearLayoutManager: LinearLayoutManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).displayHomeUpORHamburger(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vm: HomeViewModel by viewModels {
            HomeViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        homeViewModel = vm
        (context as MainActivity).displayHomeUpORHamburger(true)
//        (context as MainActivity).setToolbarLeftIcon(R.drawable.ic_hamberger)

//        (context as MainActivity).makeScreenTitleToLeft(false)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setUpItemsRecyclerView()
//        setUpNoticeRecyclerView()
        getDashboardDetails()
        getEventsDateWise()
        setUpNotificationsRecyclerView()
        setUpCalendar()

    }

    private fun setUpCalendar() {

//        val month_date = SimpleDateFormat("MMMM")
//        val cal = Calendar.getInstance().time

//        binding.tvCalendarDate.setText(month_date.format(cal))

        binding.calenderView.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, day ->
//            val month_date = SimpleDateFormat("MMMM")
//            val cal = Calendar.getInstance()
//            cal.set(Calendar.MONTH,month);
////            Log.d("date",month_date.format(cal.time))
//
//            binding.tvCalendarDate.setText(month_date.format(cal.time))

        })
    }

    private fun setUpItemsRecyclerView(announcementCount: Int) {
        val item1 =
            DashboardItems(R.color.light_pink, "Announcements", R.drawable.ic_announcement, announcementCount)
        val item2 = DashboardItems(R.color.light_green, "Time Table", R.drawable.ic_time_table)
        val item3 = DashboardItems(R.color.deep_yellow, "Student", R.drawable.ic_students)
        val item4 = DashboardItems(R.color.light_blue, "Grade Card", R.drawable.ic_grade_card)
        val item5 = DashboardItems(
            R.color.yellow_light_dark,
            "Student Attendance",
            R.drawable.ic_student_attendance
        )
        val item6 = DashboardItems(R.color.light_violet, "Profile", R.drawable.ic_profile)


        if (listOfDashboardItems.isEmpty()) {
            listOfDashboardItems.addAll(listOf(item1, item2, item3, item4, item5, item6))
        }

        val adapter = DashBoardItemsRecyclerAdapter(
            requireContext(),
            listOfDashboardItems, findNavController()
        )

        binding.rvRoundedItems.adapter = adapter
        binding.rvRoundedItems.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.ivRightArrow.setOnClickListener {
            val currentPosition =
                (binding.rvRoundedItems.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            binding.rvRoundedItems.scrollToPosition(currentPosition + 1)
        }

        binding.ivLeftArrow.setOnClickListener {
            val currentPosition =
                (binding.rvRoundedItems.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            binding.rvRoundedItems.scrollToPosition(currentPosition - 1)
        }


    }
    private fun setupTeacherInfo(teacherDetails: TeacherDetails)
    {
            binding.tvName.text ="Hey, " +teacherDetails.firstName + " " +teacherDetails.lastName
            binding.tvDateAndDay.text = getCurrentDate()
            binding.tvTime.text = getCurrentTime()
    }

    private fun setUpNoticeRecyclerView(announcementList:List<AnnouncementListItem?>, announcementCount:Int) {
//        val item1 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item2 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item3 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item4 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item5 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item6 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item7 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item8 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item9 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))
//        val item10 = DashBoardNoticeItem(requireContext().getString(R.string.lorem_with_dot))

        linearLayoutManager = LinearLayoutManager(requireContext())
//        if (listOfNotice.isEmpty()) {
//            listOfNotice.addAll(
//                listOf(
//                    item1,
//                    item2,
//                    item3,
//                    item4,
//                    item5,
//                    item6,
//                    item7,
//                    item8,
//                    item9,
//                    item10
//                )
//            )
//        }
        val adapter = NoticeRecyclerAdapter(
            requireContext(),
            announcementList, findNavController()
        )

        binding.rvNotices.adapter = adapter
        binding.rvNotices.layoutManager = linearLayoutManager

        setUpItemsRecyclerView(announcementCount)

    }

    fun getDashboardDetails() {
        var loader: KProgressHUD? = null
        homeViewModel.getTeacherDashboardDetails(PreferenceManager.getUserToken(requireContext()))
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                             setupTeacherInfo(resource.data.results?.teacherDetails!!)
                             setUpNoticeRecyclerView(resource.data.results?.announcementList!!,resource.data.results.announcementCount!!)

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

    fun getEventsDateWise()
    {
        homeViewModel.getTeacherEventDetails(PreferenceManager.getUserToken(requireContext()))
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.requestStatus?.equals(1) == true) {

                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {

                        }
                    }
                }
            })
    }

    private fun setUpNotificationsRecyclerView() {
        val item1 = DashBoardNotificationItem("Mon 19 :", "School Annual Function")
        val item2 = DashBoardNotificationItem("Mon 12 :", "Staff Meeting")

        if (listOfNotifications.isEmpty()) {
            listOfNotifications.addAll(listOf(item1, item2))
        }

        val adapter = DashboardNotificationsRecyclerAdapter(
            requireContext(),
            listOfNotifications, findNavController()
        )

        binding.rvNotifications.adapter = adapter
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (context as MainActivity).changeToolbarTitle(PreferenceManager.getUserSchoolName(requireContext())!!)
    }
}