package com.pixelforce.pixelforce_widget_demo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "multimedia")
data class Multimedia(
    @PrimaryKey(autoGenerate = true)
    val media_id: Long,

    @SerializedName("type") val media_type: String?,
    @SerializedName("src") val src: String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?
)