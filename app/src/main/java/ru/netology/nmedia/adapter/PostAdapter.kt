package ru.netology.nmedia.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.OnInteractionListener
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.BigDecimal
import java.math.RoundingMode


class PostAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.text = formatOut(post.likes)
            share.text = formatOut(post.shares)
            view.text = formatOut(post.views)
            like.isChecked = post.likedByMe

            if (post.videoContent.isNotEmpty()) {
                flContainerVideo.visibility = View.VISIBLE
                Glide.with(root.context)
                    .load(Uri.parse(post.videoPreview))
                    .override(1800, 700)
                    .into(ivVideoPreview)
            }

            flContainerVideo.setOnClickListener {
                onInteractionListener.onPlayVideo(post)
            }

            content.setOnClickListener {
                onInteractionListener.onClickPost(post)
            }

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
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
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN)
                    .toString() + "K"
            }
            in 10000..10099 -> {
                return (count / 1000).toString() + "K"
            }
            in 10100..99999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN)
                    .toString() + "K"
            }
            in 100000..100999 -> {
                return (count / 1000).toString() + "K"
            }
            in 110000..999999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN)
                    .toString() + "K"
            }
            in 1000000..1099999 -> {
                return (count / 1000000).toString() + "M"
            }
            in 1100000..9999999 -> {
                return BigDecimal(count / 1000.0).setScale(1, RoundingMode.HALF_EVEN)
                    .toString() + "M"
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