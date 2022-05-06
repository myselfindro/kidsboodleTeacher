package com.kidsboodle.teacher.ui.fragment.liveclass

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.utility.PreferenceManager
import kotlinx.android.synthetic.main.live_class_fragment.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class LiveClassFragment : Fragment() {

    companion object {
        fun newInstance() = LiveClassFragment()
    }

    private lateinit var viewModel: LiveClassViewModel
    var classModelArrayList: ArrayList<ClassModel> = ArrayList<ClassModel>()
    var sectionModelArrayList: ArrayList<SectionModel> = ArrayList<SectionModel>()
    var routineModelArrayList: ArrayList<RoutineModel> = ArrayList<RoutineModel>()
    private var upcomingLiveAdapter: UpcomingLiveAdapter? = null
    private var upcomingList: java.util.ArrayList<UpcomingModel>? = null
    val classname = java.util.ArrayList<String>()
    val sectionname = java.util.ArrayList<String>()
    val routinename = java.util.ArrayList<String>()
    var classid: String = ""
    var sectionid: String = ""
    var routineid: String = ""
    var MY_SOCKET_TIMEOUT_MS = 90000
    val myCalendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as MainActivity).changeToolbarTitle("Little Star")
        (context as MainActivity).displayHomeUpORHamburger(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveClassViewModel::class.java)
        val btnCreate = view?.findViewById(R.id.btnCreate) as TextView
        val btnHistory = view?.findViewById(R.id.btnHistory) as TextView
        val ll_createclass = view?.findViewById(R.id.ll_createclass) as LinearLayout
        val rvclassHistory = view?.findViewById(R.id.rvclassHistory) as RecyclerView
        val spClass = view?.findViewById(R.id.spClass) as Spinner
        val spSection = view?.findViewById(R.id.spSection) as Spinner
        val startDate = view?.findViewById(R.id.startDate) as EditText
        val spRoutine = view?.findViewById(R.id.spRoutine) as Spinner
        val etZoomlink = view?.findViewById(R.id.etZoomlink) as EditText
        val btnCreateclass = view?.findViewById(R.id.btnCreateclass) as MaterialButton


        spclass()
        upcomingList()



        btnCreate.setOnClickListener {

            rvclassHistory.visibility = View.GONE
            ll_createclass.visibility = View.VISIBLE
            btnCreate.setBackgroundColor(Color.parseColor("#ffa72b"));
            btnHistory.setBackgroundColor(Color.parseColor("#ffffff"));


        }

        btnHistory.setOnClickListener {

            rvclassHistory.visibility = View.VISIBLE
            ll_createclass.visibility = View.GONE
            btnHistory.setBackgroundColor(Color.parseColor("#ffa72b"));
            btnCreate.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        spClass.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    classid = classModelArrayList.get(position - 1).classname
                    Log.d(TAG, "value --->" + classid)
                    spsection()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spSection.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    sectionid = sectionModelArrayList.get(position - 1).section
                    Log.d(TAG, "sectionvalue --->" + sectionid)
                    sprountine()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        spRoutine.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    routineid = routineModelArrayList.get(position - 1).end_time
                    Log.d(TAG, "routinevalue --->" + routineid)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        val eventdate =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                eventdateupdateLabel()
            }


        startDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 0)
            val datePickerDialog = DatePickerDialog(
                requireContext(), eventdate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }


        btnCreateclass.setOnClickListener {

            if (classid.equals("")) {
                Toast.makeText(context, "Select Class", Toast.LENGTH_SHORT).show()
            } else if (sectionid.equals("")) {
                Toast.makeText(context, "Select Section", Toast.LENGTH_SHORT).show()
            } else if (routineid.equals("")) {
                Toast.makeText(context, "Select Routine Slot", Toast.LENGTH_SHORT).show()
            } else if (startDate.text.toString().length == 0) {
                Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show()
            } else if (etZoomlink.text.toString().length == 0) {
                Toast.makeText(context, "Enter Zoom Link", Toast.LENGTH_SHORT).show()
            } else {
                createliveclass()
            }

        }


    }

    private fun eventdateupdateLabel() {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        startDate.setText(sdf.format(myCalendar.time))
    }


    private fun spclass() {

        showProgressDialog()
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, "http://3.17.248.213:8002/teacher_student_class_list/",
            Response.Listener { response ->
                Log.i("Response-->", response.toString())
                classname.clear()
                classModelArrayList.clear()
                classname.add("Select Class")
                try {
                    val result = JSONObject(response.toString())
                    val stat = result.getInt("request_status")
                    if (stat == 1) {
                        classModelArrayList = java.util.ArrayList<ClassModel>()
                        val jsonArray = result.getJSONArray("result")
                        for (i in 0 until jsonArray.length()) {
                            val classobj = jsonArray.getJSONObject(i)
                            val classid = classobj.getString("id")
                            val studentclassname = classobj.getString("class_name")
                            classname.add(studentclassname)
                            val ClassModel = ClassModel(studentclassname, classid)
                            classModelArrayList.add(ClassModel)
                        }
                        val spinnerArrayAdapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                classname
                            )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spClass.setAdapter(spinnerArrayAdapter)
                    } else {
                        Log.d(
                            TAG, "unsuccessfull - " + "Error"
                        )
                        Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                hideProgressDialog()
            },
            Response.ErrorListener { error ->
                hideProgressDialog()
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

    }

    private fun spsection() {

        showProgressDialog()
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, "http://3.17.248.213:8002/teacher_class_section_list/",
            Response.Listener { response ->
                Log.i("Response-->", response.toString())
                sectionname.clear()
                sectionModelArrayList.clear()
                sectionname.add("Select Section")
                try {
                    val result = JSONObject(response.toString())
                    val stat = result.getInt("request_status")
                    if (stat == 1) {
                        sectionModelArrayList = java.util.ArrayList<SectionModel>()
                        val jsonArray = result.getJSONArray("result")
                        for (i in 0 until jsonArray.length()) {
                            val sectionobj = jsonArray.getJSONObject(i)
                            val sectionid = sectionobj.getString("id")
                            val sectionnames = sectionobj.getString("section_name")
                            sectionname.add(sectionnames)
                            val SectionModel = SectionModel(sectionnames, sectionid)
                            sectionModelArrayList.add(SectionModel)
                        }
                        val spinnerArrayAdapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                sectionname
                            )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spSection.setAdapter(spinnerArrayAdapter)
                    } else {
                        Log.d(
                            TAG, "unsuccessfull - " + "Error"
                        )
                        Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                hideProgressDialog()
            },
            Response.ErrorListener { error ->
                hideProgressDialog()
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )


    }

    private fun sprountine() {

        showProgressDialog()
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET,
            "http://3.17.248.213:8002/teacher_routine_list_by_daywise/?scl_class=" + classid + "&cls_section=" + sectionid,
            Response.Listener { response ->
                Log.i("Response-->", response.toString())
                routinename.clear()
                routineModelArrayList.clear()
                routinename.add("Select Routine")
                try {
                    val result = JSONObject(response.toString())
                    val stat = result.getInt("request_status")
                    if (stat == 1) {
                        routineModelArrayList = java.util.ArrayList<RoutineModel>()
                        val jsonArray = result.getJSONArray("result")
                        for (i in 0 until jsonArray.length()) {
                            val routinenobj = jsonArray.getJSONObject(i)
                            val routineid = routinenobj.getString("id")
                            val routineslotstart = routinenobj.getString("start_time")
                            val routineslotend = routinenobj.getString("end_time")
                            val routinetime = routineslotstart + "-" + routineslotend
                            routinename.add(routinetime)
                            val RoutineModel =
                                RoutineModel(routineslotstart, routineslotend, routineid)
                            routineModelArrayList.add(RoutineModel)
                        }
                        val spinnerArrayAdapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                routinename
                            )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spRoutine.setAdapter(spinnerArrayAdapter)
                    } else {
                        Log.d(
                            TAG, "unsuccessfull - " + "Error"
                        )
                        Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                hideProgressDialog()
            }, Response.ErrorListener { error ->
                hideProgressDialog()
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
        stringRequest.retryPolicy = DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

    }

    private fun createliveclass() {
        val date: String = startDate.text.toString()
        val format = SimpleDateFormat("dd-MM-yyyy")
        val newDate = format.parse(date)
        val formatnew = SimpleDateFormat("yyyy-MM-dd")
        val date2 = formatnew.format(newDate)

        showProgressDialog()
        val params = JSONObject()

        try {
            params.put("scl_class", classid)
            params.put("cls_section", sectionid)
            params.put("routine", routineid)
            params.put("date", date2)
            params.put("class_link", etZoomlink.text.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, "http://3.17.248.213:8002/live_class_create/", params,
            Response.Listener { response: JSONObject ->
                Log.i("Response-->", response.toString())
                try {
                    val result = JSONObject(response.toString())
                    val stat = result.getBoolean("is_active")
                    if (stat) {
                        Toast.makeText(
                            context,
                            "Live Class Created Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                hideProgressDialog()
            },
            Response.ErrorListener { error ->
                hideProgressDialog()
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = PreferenceManager.getUserToken(requireContext())
                return params
            }
        }

        Volley.newRequestQueue(context).add(jsonRequest)

    }

    private fun upcomingList() {

        showProgressDialog()
        val jsonRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "http://3.17.248.213:8002/live_class_list/?&date=&scl_class=&cls_section=&routine=",
                null,
                { response ->
                    Log.i("Response-->", java.lang.String.valueOf(response))
                    try {
                        val result = JSONObject(java.lang.String.valueOf(response))
                        upcomingList = java.util.ArrayList<UpcomingModel>()
                        val response_data: JSONArray = result.getJSONArray("results")
                        for (i in 0 until response_data.length()) {
                            val UpcomingModel = UpcomingModel()
                            val responseobj = response_data.getJSONObject(i)
                            val routineObj: JSONObject = responseobj.getJSONObject("routine")
                            val timeslotObj: JSONObject =
                                routineObj.getJSONObject("time_slot_details")
                            UpcomingModel.date = responseobj.getString("date")
                            UpcomingModel.subjectname = routineObj.getString("subject_name")
                            UpcomingModel.startdate = routineObj.getString("start_time")
                            UpcomingModel.enddate = routineObj.getString("end_time")
                            UpcomingModel.zoomlink = responseobj.getString("class_link")
                            upcomingList?.add(UpcomingModel)
                        }
                        upcomingLiveAdapter = UpcomingLiveAdapter(context, upcomingList)
                        rvclassHistory?.setAdapter(upcomingLiveAdapter)
                        rvclassHistory?.setLayoutManager(
                            LinearLayoutManager(
                                context, LinearLayoutManager.VERTICAL,
                                false
                            )
                        )

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    hideProgressDialog()
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
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


    var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(context)
            mProgressDialog!!.setMessage("Loading...")
            mProgressDialog!!.isIndeterminate = true
        }
        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

}