package com.kidsboodle.teacher.utility

import android.content.Context

object PreferenceManager {


    private const val TOKEN_PREFERENCES = "TokenPreferences"
    private const val USER_TOKEN = "UserToken"

    private const val LOGIN_STATUS_PREFERENCES = "LoginStatusPreferences"
    private const val IS_LOGGED_IN = "IsLoggedIn"
    private const val LOGIN_TOKEN_EXPIRY_TIME = "TokenExpiryTime"

    private const val USER_DETAILS_PREFERENCES = "UserDetailsPreferences"
    private const val USER_SESSION_PREFERENCE = "UserSessionPreferences"
    private const val USER_SCHOOL_NAME = "UserSchoolName"
    private const val USER_SESSION_ID = "UserSessionId"
    private const val SCHOOL_ATTENDANCE_TYPE = "SchoolAttendanceType"



    fun saveToken(token:String,context: Context){
        val sharedPref =
            context.getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        val tok="Token $token"
        editor.putString(USER_TOKEN,tok)
        editor.apply()
    }

    fun getUserToken(context: Context):String{
        val sharedPref =
            context.getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPref.getString(USER_TOKEN,"")!!
    }

    fun saveLoginStatus(status:Boolean,tokenExpiryTime:String,context: Context){
        val sharedPref =
            context.getSharedPreferences(LOGIN_STATUS_PREFERENCES, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putBoolean(IS_LOGGED_IN,status)
        editor.putString(LOGIN_TOKEN_EXPIRY_TIME,tokenExpiryTime)
        editor.apply()
    }

    fun getLoginStatus(context: Context):Boolean{
        val sharedPref =
            context.getSharedPreferences(LOGIN_STATUS_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPref.getBoolean(IS_LOGGED_IN,false)
    }

    fun getTokenExpiryTime(context: Context):String? {
        val sharedPref =
            context.getSharedPreferences(LOGIN_STATUS_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPref.getString(LOGIN_TOKEN_EXPIRY_TIME,null)
    }

    fun saveUserDetails(schoolName:String,schoolattendanceType:String,context: Context){
        val sharedPref =
            context.getSharedPreferences(USER_DETAILS_PREFERENCES, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString(USER_SCHOOL_NAME,schoolName)
        editor.putString(SCHOOL_ATTENDANCE_TYPE,schoolattendanceType)
        editor.apply()
    }
    fun saveUserSession(sessionID:String,context: Context){
        val sharedPref =
            context.getSharedPreferences(USER_SESSION_PREFERENCE, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString(USER_SESSION_ID,sessionID)
        editor.apply()
    }

    fun getUserSchoolName(context: Context):String?{
        val sharedPref =
            context.getSharedPreferences(USER_DETAILS_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPref.getString(USER_SCHOOL_NAME,null)
    }


    fun getSchoolAttendanceType(context: Context):String?{
        val sharedPref =
            context.getSharedPreferences(USER_DETAILS_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPref.getString(SCHOOL_ATTENDANCE_TYPE,null)
    }
    fun getSessionId(context: Context):String?{
        val sharedPref =
            context.getSharedPreferences(USER_SESSION_PREFERENCE, Context.MODE_PRIVATE)

        return sharedPref.getString(USER_SESSION_ID,null)
    }
}