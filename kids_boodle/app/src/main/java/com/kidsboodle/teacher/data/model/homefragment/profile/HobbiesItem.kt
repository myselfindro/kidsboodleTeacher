package com.kidsboodle.teacher.data.model.homefragment.profile

import android.os.Parcel
import android.os.Parcelable

data class HobbiesItem(
    val hobbyName:String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hobbyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HobbiesItem> {
        override fun createFromParcel(parcel: Parcel): HobbiesItem {
            return HobbiesItem(parcel)
        }

        override fun newArray(size: Int): Array<HobbiesItem?> {
            return arrayOfNulls(size)
        }
    }
}