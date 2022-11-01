package ru.netology.nmedia

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post (
    val id: Long,
    val author: String,
    var content: String,
    var videoContent: String,
    var videoPreview: String,
    val published: String,
    var likes: Int = 1800,
    var shares: Int = 550,
    var views: Int = 3000,
    val likedByMe: Boolean
): Parcelable