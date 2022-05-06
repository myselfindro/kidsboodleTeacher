package com.kidsboodle.teacher.ui.fragment.home.students

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.CheckStudentRecyclerAdapter
import com.kidsboodle.teacher.data.model.homefragment.students.CheckStudentItem
import com.kidsboodle.teacher.databinding.FragmentStudentCheckBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.utility.PreferenceManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class StudentCheckFragment : Fragment() {

    private var _binding: FragmentStudentCheckBinding? = null

    private val binding get() = _binding!!

    val list:ArrayList<CheckStudentItem> = ArrayList()
    private var StudentAdapter2: StudentAdapter2? = null
    private var studentModelArrayListe: java.util.ArrayList<StudentModel>? = null
//    val token: String = PreferenceManager.getUserToken(requireContext())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
//        (context as MainActivity).changeToolbarTitle("Little Star")
        (context as MainActivity).displayHomeUpORHamburger(false)


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

//        (context as MainActivity).displayHomeUpORHamburger(false)
        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).makeScreenTitleToLeft(true)

        _binding = FragmentStudentCheckBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        makeChecks()
//        setUpRecyclerView()
        studentList()
    }

    private fun studentList(){
        showProgressDialog()
        val jsonRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "http://3.17.248.213:8002/teacher_student_details_get/",
                null,
                { response ->
                    Log.i("Response-->", java.lang.String.valueOf(response))
                    try {
                        val result = JSONObject(java.lang.String.valueOf(response))
                        studentModelArrayListe = java.util.ArrayList<StudentModel>()
                        val response_data: JSONArray = result.getJSONArray("results")
                        for (i in 0 until response_data.length()) {
                            val studentModel = StudentModel()
                            val responseobj = response_data.getJSONObject(i)
                            studentModel.name = responseobj.getString("student_name")
                            studentModel.roll = responseobj.getString("roll_number")
                            studentModel.id = responseobj.getString("student")
                            studentModelArrayListe?.add(studentModel)
                        }
                        StudentAdapter2 = StudentAdapter2(context, studentModelArrayListe)
                        binding.rvCheckStudent.setAdapter(StudentAdapter2)
                        binding.rvCheckStudent.setLayoutManager(
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
                    params["Authorization"] = PreferenceManager.getUserToken(requireContext()).toString()
                    return params
                }
            }

        Volley.newRequestQueue(context).add(jsonRequest)

    }

//    private fun makeChecks() {
//        val item1=CheckStudentItem("Subhajit Das","27", StudentViewDetailsItem(
//            "Subhajit Das","V-A","Subhaj das","XYZZZZ","YYYSSS","XYZ@GMAIL.COM","741023658","KOLKATA PIN-745231","KOLKATA","Male","10-11-2001","Little Star","20.03.2020","50%",
//            "Good",
//            listOf(ExamsItem("Class Test 1","40"),ExamsItem("Half Yearly Exam","80"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Exam","100"),ExamsItem("Annual Exam","100")),
//            listOf(HobbiesItem("Cricket"), HobbiesItem("Football"))
//        ))
//        val item2=CheckStudentItem("Sohom Ghosh","14", StudentViewDetailsItem(
//            "Sohom Ghosh","VI-A","Subhaj das","XYZZZZ","YYYSSS","XYZ@GMAIL.COM","741023658","KOLKATA PIN-745231","KOLKATA","Male","06-08-2002","Little Star","20.03.2020","50%",
//            "Good",
//            listOf(ExamsItem("Class Test 2","40"),ExamsItem("Half Yearly Exam","80"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Test","40"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Test","60"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Test","10")),
//            listOf(HobbiesItem("Kho Kho"),HobbiesItem("Tennis"))
//        ))
//        val item3=CheckStudentItem("Sunidhi Gupta","18", StudentViewDetailsItem(
//            "Sunidhi Gupta","IX-B","Ritika das","XYZZZZ","YYYSSS","XYZ@GMAIL.COM","741023658","KOLKATA PIN-745231","KOLKATA","Female","20-06-2004","Little Star","20.03.2020","50%",
//            "Good",
//            listOf(ExamsItem("Class Test 2","40"),ExamsItem("Half Yearly Exam","80"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Test","50"),ExamsItem("Annual Exam","75"),
//                ExamsItem("Annual Test","80"),ExamsItem("Annual Exam","100"),
//                ExamsItem("Annual Exam","10")),
//            listOf(HobbiesItem("Cricket"),HobbiesItem("Table Tennis"))
//        ))
//
//        if(list.isEmpty())
//        list.addAll(listOf(item1,item2,item3))
//    }
//
//    private fun setUpRecyclerView() {
//        val adapter=CheckStudentRecyclerAdapter(requireContext(),list,findNavController())
//        binding.rvCheckStudent.adapter = adapter
//        binding.rvCheckStudent.layoutManager = LinearLayoutManager(requireContext())
//    }


    var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(requireContext())
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