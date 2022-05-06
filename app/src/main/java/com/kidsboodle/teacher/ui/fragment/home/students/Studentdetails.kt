package com.kidsboodle.teacher.ui.fragment.home.students

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.utility.PreferenceManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Studentdetails : AppCompatActivity() {

    var id: String? = null
    var civProfile: ImageView? = null
    var tvStudentName: TextView? = null
    var tvStudentClass: TextView? = null
    var tvGender: TextView? = null
    var tvDateOfBirth: TextView? = null
    var tvFatherName: TextView? = null
    var tvMotherName: TextView? = null
    var tvMailId: TextView? = null
    var tvContactInfo: TextView? = null
    var tvPermanentAddress: TextView? = null
    var tvSchoolName: TextView? = null
    var tvClassTeacher: TextView? = null
    var tvDateOfJoiningInSchool: TextView? = null
    var tvAttendancePerformancePercent: TextView? = null
    var tvAveragePerformance: TextView? = null
    var horizontalProgressBar: ProgressBar? = null
    var ivStar1: ImageView? = null
    var ivStar2: ImageView? = null
    var ivStar3: ImageView? = null
    var ivStar4: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentdetails)
        val intent = intent
        id = intent.getStringExtra("id")
        val btn_back = findViewById(R.id.btn_back) as ImageView
        civProfile = findViewById(R.id.civProfile)
        tvStudentName = findViewById(R.id.tvStudentName)
        tvStudentClass = findViewById(R.id.tvStudentClass)
        tvGender = findViewById(R.id.tvGender)
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth)
        tvFatherName = findViewById(R.id.tvFatherName)
        tvMotherName = findViewById(R.id.tvMotherName)
        tvMailId = findViewById(R.id.tvMailId)
        tvContactInfo = findViewById(R.id.tvContactInfo)
        tvPermanentAddress = findViewById(R.id.tvPermanentAddress)
        tvSchoolName = findViewById(R.id.tvSchoolName)
        tvClassTeacher = findViewById(R.id.tvClassTeacher)
        tvDateOfJoiningInSchool = findViewById(R.id.tvDateOfJoiningInSchool)
        tvAttendancePerformancePercent = findViewById(R.id.tvAttendancePerformancePercent)
        tvAveragePerformance = findViewById(R.id.tvAveragePerformance)
        horizontalProgressBar = findViewById(R.id.horizontalProgressBar)
        ivStar1 = findViewById(R.id.ivStar1)
        ivStar2 = findViewById(R.id.ivStar2)
        ivStar3 = findViewById(R.id.ivStar3)
        ivStar4 = findViewById(R.id.ivStar4)


        btn_back.setOnClickListener(View.OnClickListener { view ->
            onBackPressed()
        })

        studentdetails()

    }

    private fun studentdetails() {

        showProgressDialog()
        val jsonRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "http://3.17.248.213:8002/teacher/student_details_view/?student_id=" + id,
                null,
                { response ->
                    Log.i("Response-->", java.lang.String.valueOf(response))
                    try {
                        val result = JSONObject(java.lang.String.valueOf(response))
                        val response_data: JSONArray = result.getJSONArray("result")
                        for (i in 0 until response_data.length()) {
                            val responseobj = response_data.getJSONObject(i)
                            tvStudentName?.setText(
                                responseobj.getString("student_fname") + " " +
                                        responseobj.getString("student_lname")
                            )
                            tvGender?.setText(responseobj.getString("gender"))
                            tvDateOfBirth?.setText(responseobj.getString("dob"))
                            tvFatherName?.setText(responseobj.getString("father_name"))
                            tvMotherName?.setText(responseobj.getString("mother_name"))
                            tvStudentClass?.setText(responseobj.getString("studentclasssection__scl_class__class_name"))
                            tvPermanentAddress?.setText(
                                responseobj.getString("address1") + ", " +
                                        responseobj.getString("address2")
                            )
                            tvSchoolName?.setText(responseobj.getString("school__school_name"))
                            tvDateOfJoiningInSchool?.setText(responseobj.getString("doj"))
                            horizontalProgressBar?.setProgress(50)
                            ivStar1?.setImageResource(R.drawable.ic_filled_yellow_star)
                            ivStar2?.setImageResource(R.drawable.ic_filled_yellow_star)
                            ivStar3?.setImageResource(R.drawable.ic_filled_yellow_star)
                            ivStar4?.setImageResource(R.drawable.ic_filled_yellow_star)

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    hideProgressDialog()
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@Studentdetails, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] =
                        PreferenceManager.getUserToken(this@Studentdetails).toString()
                    return params
                }
            }

        Volley.newRequestQueue(this).add(jsonRequest)

    }


    var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
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