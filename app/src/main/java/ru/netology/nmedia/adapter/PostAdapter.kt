package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.BigDecimal
import java.math.RoundingMode

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostAdapter(
        private val onLikeListener: OnLikeListener,
        private val onShareListener: OnShareListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeListener: OnLikeListener,
        private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLike.text = formatOut(post.likes)
            countShare.text = formatOut(post.shares)
            countView.text = formatOut(post.views)
            like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like
            )

            like.setOnClickListener {
                if (post.likedByMe) {
                    post.likes++
                    post.countLikes = post.likes.toString()
                } else {
                    post.likes--
                    post.countLikes = post.likes.toString()
                }
                onLikeListener(post)
            }

            share.setOnClickListener {
                onShareListener(post)
                post.shares++
                post.countShares = post.shares.toString()
            }
        }
    }

    private fun formatOut(count: Int): String {
        return when (count) {
            in 0..999 -> {
                return count.toString()
            }
            in 1000..1099 -> {
                return (count / 1000).toString() + "K"
            }
            in 1100..9999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 10000..10099 -> {
                return (count / 1000).toString() + "K"
            }
            in 10100..99999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 100000..100999 -> {
                return (count / 1000).toString() + "K"
            }
            in 110000..999999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 1000000..1099999 -> {
                return (count / 1000000).toString() + "M"
            }
            in 1100000..9999999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN).toString() + "M"
            }
            else -> "error"
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}