package com.kidsboodle.teacher.data.model.homefragment.students

import android.os.Parcel
import android.os.Parcelable
import com.kidsboodle.teacher.data.model.homefragment.profile.ExamsItem
import com.kidsboodle.teacher.data.model.homefragment.profile.HobbiesItem

data class StudentViewDetailsItem(
    val studentName: String?,
    val studentClass:String?,
    val classTeacher:String?,
    val fathersName:String?,
    val mothersName:String?,
    val mailId:String?,
    val contactInfo:String?,
    val permanentAddress:String?,
    val secondaryAddress:String?,
    val gender:String?,
    val dateOfBirth:String?,
    val schoolName:String?,
    val dateOfJoining:String?,
    val attendancePerformancePercent:String?,
    val averagePerformance:String?,
    val examsList:List<ExamsItem>?,
    val hobbiesList:List<HobbiesItem>?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(ExamsItem),
        parcel.createTypedArrayList(HobbiesItem)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentName)
        parcel.writeString(studentClass)
        parcel.writeString(classTeacher)
        parcel.writeString(fathersName)
        parcel.writeString(mothersName)
        parcel.writeString(mailId)
        parcel.writeString(contactInfo)
        parcel.writeString(permanentAddress)
        parcel.writeString(secondaryAddress)
        parcel.writeString(gender)
        parcel.writeString(dateOfBirth)
        parcel.writeString(schoolName)
        parcel.writeString(dateOfJoining)
        parcel.writeString(attendancePerformancePercent)
        parcel.writeString(averagePerformance)
        parcel.writeTypedList(examsList)
        parcel.writeTypedList(hobbiesList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentViewDetailsItem> {
        override fun createFromParcel(parcel: Parcel): StudentViewDetailsItem {
            return StudentViewDetailsItem(parcel)
        }

        override fun newArray(size: Int): Array<StudentViewDetailsItem?> {
            return arrayOfNulls(size)
        }
    }
}