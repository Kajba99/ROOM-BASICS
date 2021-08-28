package com.example.roombasics.usermodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "user_table"
)
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val firstName: String,
    val lastName: String,
    val age: Int
): Parcelable
