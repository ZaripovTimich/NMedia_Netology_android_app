package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Меняем карьеру через образование",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                    "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                    "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                    "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                    "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "24 марта в 23:23",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked_24)
            }
            countLike?.text = formatOut(post.likes)
            countShare?.text = formatOut(post.shares)

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like
                )

                if (post.likedByMe) {
                    post.likes++
                    countLike?.text = formatOut(post.likes)
                } else {
                    post.likes--
                    countLike?.text = formatOut(post.likes)
                }
            }

            share?.setOnClickListener{
                post.shares++
                countShare?.text = formatOut(post.shares)
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
                return BigDecimal(count / 1000).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 10000..10099 -> {
                return (count / 1000).toString() + "K"
            }
            in 10100..99999 -> {
                return BigDecimal(count / 1000).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 100000..100999 -> {
                return (count / 1000).toString() + "K"
            }
            in 110000..999999 -> {
                return BigDecimal(count / 1000).setScale(1, RoundingMode.HALF_EVEN).toString() + "K"
            }
            in 1000000..1099999 -> {
                return (count / 1000000).toString() + "M"
            }
            in 1100000..9999999 -> {
                return BigDecimal(count / 1000).setScale(1, RoundingMode.HALF_EVEN).toString() + "M"
            }
            else -> "error"
        }
    }
}