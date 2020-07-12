package com.pixelforce.pixelforce_widget_demo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_link")
data class Link(
    @PrimaryKey(autoGenerate = true)
    val link_id: Long,

    @SerializedName("type") val like_type: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("suggested_link_text") val suggested_link_text: String?
)