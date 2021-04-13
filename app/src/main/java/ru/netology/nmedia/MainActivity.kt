package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this, { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countLike.text = formatOut(post.likes)
                countShare.text = formatOut(post.shares)
                countView.text = formatOut(post.views)
                if (post.likedByMe) {
                    post.likes++
                    countLike.text = formatOut(post.likes)
                } else {
                    post.likes--
                    countLike.text = formatOut(post.likes)
                }

                like.setImageResource(
                        if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like
                )
            }
        })

        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener{
            viewModel.share()
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