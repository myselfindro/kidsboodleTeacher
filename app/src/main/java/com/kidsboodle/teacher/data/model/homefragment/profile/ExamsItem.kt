package com.kidsboodle.teacher.data.model.homefragment.profile

import android.os.Parcel
import android.os.Parcelable

data class ExamsItem(
    val testName:String?,
    val marksObtained:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(testName)
        parcel.writeString(marksObtained)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExamsItem> {
        override fun createFromParcel(parcel: Parcel): ExamsItem {
            return ExamsItem(parcel)
        }

        override fun newArray(size: Int): Array<ExamsItem?> {
            return arrayOfNulls(size)
        }
    }
}