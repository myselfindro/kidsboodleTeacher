package com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.databinding.FragmentCreateAnnouncementBinding
import com.kidsboodle.teacher.utility.*
import kotlinx.android.synthetic.main.fragment_create_announcement.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private const val CAMERA_REQUEST_PERMISSION_CODE = 1001

class CreateAnnouncementsFragment : Fragment(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {

    private var _binding: FragmentCreateAnnouncementBinding? = null

    private val binding get() = _binding!!

    private var classList: ArrayList<String> = ArrayList()
    private var classListsValue: ArrayList<String> = ArrayList()
    private var sectionList: ArrayList<String> = ArrayList()
    private var sectionListValue: ArrayList<String> = ArrayList()
    private var studentList: ArrayList<String> = ArrayList()
    private var studentListID: ArrayList<String> = ArrayList()
    private lateinit var createAnnouncementViewModel: CreateAnnouncementViewModel;
    private var file:File=File("")
    private lateinit var file1: File
    private lateinit var file2: File
    private lateinit var file3: File


    companion object {
        var selectedClass: String = ""

        var selectedSection: String = ""

        var selectedSubject: String = ""

        var session:String?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAnnouncementBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm: CreateAnnouncementViewModel by viewModels {
            CreateAnnouncementViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        createAnnouncementViewModel = vm

        session=PreferenceManager.getSessionId(requireContext())
        getStudentClassList()
        binding.ivAddImage1.setOnClickListener {
            checkCameraPermissions("1")
        }

        binding.ivAddImage2.setOnClickListener {
            checkCameraPermissions("2")
        }

        binding.ivAddImage3.setOnClickListener {
            checkCameraPermissions("3")
        }

        binding.txtPublish.setOnClickListener {
            publishAnnouncement()
        }
    }

    private fun publishAnnouncement(auth: String = PreferenceManager.getUserToken(requireContext())) {


        var image1: MultipartBody.Part = MultipartBody.Part.createFormData("image1", null, file?.asRequestBody("*/*".toMediaTypeOrNull()))
        var image2: MultipartBody.Part= MultipartBody.Part.createFormData("image2", null, file?.asRequestBody("*/*".toMediaTypeOrNull()))
        var image3: MultipartBody.Part= MultipartBody.Part.createFormData("image3", null, file?.asRequestBody("*/*".toMediaTypeOrNull()))
        if (::file1.isInitialized) {

            image1 = MultipartBody.Part.createFormData(
                "image1",
                file1.name,
                file1.asRequestBody("*/*".toMediaTypeOrNull())
            )
        }

        if (::file2.isInitialized) {

            image2 = MultipartBody.Part.createFormData(
                "image2",
                file2.name,
                file2.asRequestBody("*/*".toMediaTypeOrNull())
            )
        }
        if (::file3.isInitialized) {

            image3 = MultipartBody.Part.createFormData(
                "image3",
                file3.name,
                file3.asRequestBody("*/*".toMediaTypeOrNull())
            )
        }


        var loader: KProgressHUD? = null
        if (!selectedClass.isEmpty()
            && !autoCompleteAnnouncementTitle.text.toString().isEmpty()
            && !edtAnnouncementDescription.text.toString().isEmpty()&&::file3.isInitialized&&::file2.isInitialized&&::file1.isInitialized
        ) {
            val autoCompleteAnnouncementTitleBody: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                autoCompleteAnnouncementTitle.text.toString()
            )
            val edtAnnouncementDescriptionBody: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edtAnnouncementDescription.text.toString()
            )
            val date: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                "2022-03-11"
            )
            val toSchool: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                "1"
            )
            val selectedClassBody: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                selectedClass
            )
            val selectedSectionBody: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                selectedSection
            )
            val selectedSubjectBody: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                selectedSubject
            )
            createAnnouncementViewModel.postAnnouncement(
                auth,
                autoCompleteAnnouncementTitleBody,
                edtAnnouncementDescriptionBody,
                date,
                toSchool,
                selectedClassBody,
                selectedSectionBody,
                selectedSubjectBody,
                image1!!,
                image2!!,
                image3!!
            ).observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            classList.clear()
                            classListsValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                binding.root.showSnackBar(resource.data?.msg)
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
        else if (selectedClass.isEmpty()) {
            autoCompleteClass.setError("Please select Class")
        } else if (autoCompleteAnnouncementTitle.text.toString().isEmpty()) {
            autoCompleteAnnouncementTitle.setError("Please enter Announcement title")
        } else if (edtAnnouncementDescription.text.toString().isEmpty()) {
            edtAnnouncementDescription.setError("Please enter Announcement description")
        }else if(!::file1.isInitialized||!::file2.isInitialized||!::file3.isInitialized){
            binding.root.showSnackBar("Please select images to post")
        }
    }

    private fun getStudentClassList(auth: String = PreferenceManager.getUserToken(requireContext())) {
        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherStudentClassList(auth)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            classList.clear()
                            classListsValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                classList.add("Select All")
                                classListsValue.add("Select All")
                                for (i in it.data?.result!!) {
                                    classList.add("Class - ${i?.className}")
                                    classListsValue.add(i?.id!!.toString())
                                }

                                val classAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    classList
                                )
                                binding.autoCompleteClass.setAdapter(classAdapter)

                                binding.autoCompleteClass.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedClass = classListsValue[position]
                                        getStudentSectionList(
                                            Class = classListsValue[position],
                                            section = session!!,
                                            pageCount = 1,
                                            pageSize = 25
                                        ) //section filter is not working as of now
                                        //section filter is not working as of now
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

    private fun getStudentList(auth:String=PreferenceManager.getUserToken(requireContext())){

        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherStudentList(auth)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)

                            studentList.clear()
                            studentListID.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for (i in it.data?.result!!) {
                                    studentList.add("Student - ${i?.className}")
                                    studentListID.add(i?.className!!)
                                }

                                val classAdapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.dropdown_menu_item,
                                    studentList
                                )
                                binding.autoCompleteSelectStudent.setAdapter(classAdapter)

                                binding.autoCompleteSelectStudent.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position")
                                        selectedSubject = studentListID[position]
                                        getStudentSectionList(
                                            Class = studentListID[position],
                                            section = session!!,
                                            pageCount = 1,
                                            pageSize = 25
                                        ) //section filter is not working as of now
                                        //section filter is not working as of now
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


    fun getStudentSectionList(auth: String = PreferenceManager.getUserToken(requireContext()),
        Class: String,
        section: String,
        pageCount: Int,
        pageSize:Int
    ) {
        var loader: KProgressHUD? = null
        createAnnouncementViewModel.getTeacherClassSectionList(auth, Class, section, pageCount, pageSize)
            .observe(this, {
                it?.let { resource ->
                    Log.d(TAG, "sectionresponse->"+resource.data)
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            sectionList.clear()
                            sectionListValue.clear()
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                for (i in it.data?.result!!) {
                                    sectionList.add("Section - ${i?.sectionName!!}")
                                    sectionListValue.add(i.id.toString())
                                }
                                val sectionAdapter = ArrayAdapter(
                                    requireContext(), R.layout.dropdown_menu_item, sectionList)
                                binding.autoCompleteSection.setAdapter(sectionAdapter)


                                binding.autoCompleteSection.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, _, position, _ ->
                                        println("position clicked= $position ")
                                        selectedSection = sectionListValue[position]
                                        //section filter is not working as of now
                                        getStudentList()
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


    private fun checkCameraPermissions(tag: String) {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
        //Permission is there, do rest of the work


        if (!hasPermission(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        ) {
            checkPermissions(
                CAMERA_REQUEST_PERMISSION_CODE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            return
        }

        val singleSelectionPicker: BSImagePicker =
            BSImagePicker.Builder("com.support.kidBoodle.fileprovider")
                .setMaximumDisplayingImages(24) //Default: Integer.MAX_VALUE. Don't worry about performance :)
                .setSpanCount(3) //Default: 3. This is the number of columns
                .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in pixel.
                .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of the dialog.
                .setTag(tag) //Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
                .build()

        singleSelectionPicker.show(childFragmentManager, "picker");
//        } else {
//            //Ask for permission
//
//            ActivityCompat.requestPermissions(
//                requireContext() as Activity,
//                arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_PERMISSION_CODE
//            )
//
//        }
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        System.out.println("This is selected uri : --->> ${uri.toString()} and tag ==$tag ")
        val file = File(uri?.path)
        when (tag) {
            "1" -> {
                file1 = File(getRealPathFromURI(uri)!!)
                binding.ivAddImage1.setImageURI(uri)
                binding.ivAddImage1.scaleType = ImageView.ScaleType.FIT_XY
            }
            "2" -> {
                file2 = File(getRealPathFromURI(uri)!!)
                binding.ivAddImage2.setImageURI(uri)
                binding.ivAddImage2.scaleType = ImageView.ScaleType.FIT_XY
            }
            "3" -> {
                file3 = File(getRealPathFromURI(uri)!!)
                binding.ivAddImage3.setImageURI(uri)
                binding.ivAddImage3.scaleType = ImageView.ScaleType.FIT_XY
            }
        }
    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView?) {
        if (ivImage != null) {
            Glide.with(requireContext()).load(imageUri).into(ivImage)
        }
    }


    private fun checkPermissions(callbackId: Int, vararg permissionsId: String) {
        when {
            !hasPermission(*permissionsId) -> try {
                ActivityCompat.requestPermissions(requireActivity(), permissionsId, callbackId)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun hasPermission(vararg permissionsId: String): Boolean {
        var hasPermission = true

        permissionsId.forEach { permission ->
            hasPermission = hasPermission
                    && ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        return hasPermission
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (requireActivity().contentResolver != null) {
            val cursor: Cursor? =
                requireActivity().contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }


}