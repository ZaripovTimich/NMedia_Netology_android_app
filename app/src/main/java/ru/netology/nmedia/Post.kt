package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 1800,
    var shares: Int = 1949,
    var views: Int = 3000,
    val likedByMe: Boolean
)