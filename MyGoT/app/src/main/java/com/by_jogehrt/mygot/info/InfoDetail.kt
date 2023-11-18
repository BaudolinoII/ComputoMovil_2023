package com.by_jogehrt.mygot.info

import com.google.gson.annotations.SerializedName

data class InfoDetail(
    @SerializedName("id")
    val id: String?,
    @SerializedName("firstName")
    val f_name: String?,
    @SerializedName("lastName")
    val l_name: String?,
    @SerializedName("fullName")
    val fl_name: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("family")
    val family: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageUrl")
    val image_url: String?)
