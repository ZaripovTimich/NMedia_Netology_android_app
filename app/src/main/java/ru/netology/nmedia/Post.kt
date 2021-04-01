package ru.netology.nmedia

data class Post (
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likes: Int = 1950,
        var shares: Int = 1949,
        var likedByMe: Boolean
)