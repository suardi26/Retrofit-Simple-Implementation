package com.practice.retrofit_simple_implementation.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val userId : Int,
    val id: Int,
    val title: String?,
    @SerializedName("body")
    val text: String?
) : Parcelable

