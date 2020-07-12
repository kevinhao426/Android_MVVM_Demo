package com.kehao.myapplication.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.google.gson.annotations.SerializedName
import com.pixelforce.pixelforce_widget_demo.model.Link
import com.pixelforce.pixelforce_widget_demo.model.Multimedia

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "movie_list")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    val uuid: Int,

    @SerializedName("display_title")
    val display_title: String?,

    @SerializedName("mpaa_rating")
    val mpaa_rating: String?,

    @SerializedName("critics_pick")
    val critics_pick: Int,

    @SerializedName("byline")
    val byline: String?,

    @SerializedName("headline")
    val headline: String?,

    @SerializedName("summary_short")
    val summary_short: String?,

    @SerializedName("publication_date")
    val publication_date: String?,

    @SerializedName("opening_date")
    val opening_date: String?,

    @SerializedName("date_updated")
    val date_updated: String?,

    @Embedded
    val link: Link,

    @Embedded
    val multimedia: Multimedia
)