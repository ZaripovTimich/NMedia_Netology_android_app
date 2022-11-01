package ru.netology.nmedia

interface OnInteractionListener {
    fun onPlayVideo(post: Post)
    fun onClickPost(post: Post) {}
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onEdit(post: Post)
    fun onRemove(post: Post)
}