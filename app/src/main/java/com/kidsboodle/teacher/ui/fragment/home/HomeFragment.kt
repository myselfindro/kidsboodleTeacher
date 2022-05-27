package com.kidsboodle.teacher.ui.fragment.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
import com.kidsboodle.teacher.ui.activity.landing.LandingActivity
import com.kidsboodle.teacher.utility.*
import com.kidsboodle.teacher.utility.ReusableFunctions.getCurrentDate
import com.kidsboodle.teacher.utility.ReusableFunctions.getCurrentTime
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


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
    var rvNotifications: RecyclerView? = null
    private var holidayAdapter: HolidayAdapter? = null
    private var holidaylist: ArrayList<HolidayModel>? = ArrayList()
    private var currentdate: String?= null


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
//        setUpNoticeRecyclerView()
        getDashboardDetails()
        getEventsDateWise()
//        setUpNotificationsRecyclerView()
        setUpCalendar()
        currentmonthholidaylist()

    }

    private fun setUpCalendar() {

        binding.calenderView.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, day ->
            val month_date = SimpleDateFormat("MM")
            val year_date = SimpleDateFormat("yyyy")
            val date_date = SimpleDateFormat("dd")
            val cal = Calendar.getInstance()
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.DATE, day)
            Log.d("month", month_date.format(cal.time))
            Log.d("year", year_date.format(cal.time))
            Log.d("date", date_date.format(cal.time))
            currentdate = year_date.format(cal.time)+"-"+month_date.format(cal.time)+"-"+date_date.format(cal.time)
            Log.d("currentselecteddate-->", currentdate!!)

            currentdateholidaylist()

        })
    }


    private fun setUpItemsRecyclerView(announcementCount: Int) {
        val item1 =
            DashboardItems(
                R.color.light_pink,
                "Announcements",
                R.drawable.ic_announcement,
                announcementCount
            )
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

    private fun setupTeacherInfo(teacherDetails: TeacherDetails) {
        binding.tvName.text = "Hey, " + teacherDetails.firstName + " " + teacherDetails.lastName
        binding.tvDateAndDay.text = getCurrentDate()
        binding.tvTime.text = getCurrentTime()
    }

    private fun setUpNoticeRecyclerView(
        announcementList: List<AnnouncementListItem?>,
        announcementCount: Int
    ) {
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
                                val count: Int? = resource.data.results?.announcementCount
                                Log.d(TAG, "announcemntcount-->" + count)
                                setUpItemsRecyclerView(resource.data.results?.announcementCount!!)
                                setUpNoticeRecyclerView(
                                    resource.data.results?.announcementList!!,
                                    resource.data.results.announcementCount!!
                                )

                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            if (it.message?.contains("401") == true) {
                                PreferenceManager.saveLoginStatus(false, "", requireContext())
                                requireActivity().goToActivity(LandingActivity::class.java, true)
                                binding.root.showSnackBar("Your session expired. Login back!")
                            } else
                                binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })
    }

    fun getEventsDateWise() {
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


    private fun currentmonthholidaylist() {

        val monthFormat: DateFormat = SimpleDateFormat("MM")
        val yearFormat: DateFormat = SimpleDateFormat("yyyy")
        val month = Date()
        val year = Date()
        Log.d("currentMonth", monthFormat.format(month))
        Log.d("currentYear", yearFormat.format(year))

        val loader: KProgressHUD? = null
        val jsonRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "http://3.17.248.213:8002/teacher_holiday_list/?page_size=0&month=" + monthFormat.format(
                    month
                ) + "&year=" + yearFormat.format(year),
                null,
                { response ->
                    Log.i("Response-->", java.lang.String.valueOf(response))
                    try {
                        val result = JSONObject(java.lang.String.valueOf(response))
                        val response_data: JSONArray = result.getJSONArray("results")
                        for (i in 0 until response_data.length()) {
                            holidaylist = java.util.ArrayList<HolidayModel>()
                            val HolidayModel = HolidayModel()
                            val responseobj = response_data.getJSONObject(i)
                            HolidayModel.date = responseobj.getString("date")
                            HolidayModel.leavetype = responseobj.getString("leave_type")
                            holidaylist?.add(HolidayModel)
                        }

                        holidayAdapter = HolidayAdapter(requireContext(), holidaylist)
                        binding.rvNotifications.setAdapter(holidayAdapter)
                        binding.rvNotifications.setLayoutManager(
                            LinearLayoutManager(
                                requireContext(), LinearLayoutManager.VERTICAL,
                                false
                            )
                        )

                        if (holidaylist?.size == 0) {
                            tvNoevents.visibility = View.VISIBLE
                            rvNotifications?.visibility = View.GONE
                        } else {

                            tvNoevents.visibility = View.GONE
                            rvNotifications?.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    hideLoader(loader)

                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                    return params
                }
            }

        Volley.newRequestQueue(context).add(jsonRequest)


    }

    private fun currentdateholidaylist(){

        val loader: KProgressHUD? = null
        val jsonRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "https://kidboodle.com/api/teacher_holiday_list/?page_size=0&date="+currentdate,
                null,
                { response ->
                    Log.i("Response-->", java.lang.String.valueOf(response))
                    try {
                        val result = JSONObject(java.lang.String.valueOf(response))
                        val response_data: JSONArray = result.getJSONArray("results")
                        for (i in 0 until response_data.length()) {
                            holidaylist = java.util.ArrayList<HolidayModel>()
                            val HolidayModel = HolidayModel()
                            val responseobj = response_data.getJSONObject(i)
                            HolidayModel.date = responseobj.getString("date")
                            HolidayModel.leavetype = responseobj.getString("leave_type")
                            holidaylist?.add(HolidayModel)
                        }

                        val adapter = HolidayAdapter(
                            requireContext(),
                            holidaylist
                        )
                        binding.rvNotifications.adapter = adapter
                        binding.rvNotifications.layoutManager =
                            LinearLayoutManager(requireContext())

                        if (holidaylist?.size == 0) {
                            tvNoevents.visibility = View.VISIBLE
                            rvNotifications?.visibility = View.GONE
                        } else {

                            tvNoevents.visibility = View.GONE
                            rvNotifications?.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    hideLoader(loader)

                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                    return params
                }
            }

        Volley.newRequestQueue(context).add(jsonRequest)
        


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (context as MainActivity).changeToolbarTitle(
            PreferenceManager.getUserSchoolName(
                requireContext()
            )!!
        )
    }
}