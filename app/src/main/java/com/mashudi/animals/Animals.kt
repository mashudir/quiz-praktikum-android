package com.mashudi.animals

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Animals(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nama: String = "",
    var inggris: String = "",
    var desc: String = "",
    var url: String = "",
    var img: String = ""
) : Parcelable