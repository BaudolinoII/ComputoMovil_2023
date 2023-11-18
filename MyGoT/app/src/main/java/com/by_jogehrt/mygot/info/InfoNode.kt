package com.by_jogehrt.mygot.info

import com.google.gson.annotations.SerializedName

data class InfoNode(
    @SerializedName("id")
    val id: String?,
    @SerializedName("firstName")
    val f_name: String?,
    @SerializedName("lastName")
    val l_name: String?,
    @SerializedName("imageUrl")
    val image_url: String?)
