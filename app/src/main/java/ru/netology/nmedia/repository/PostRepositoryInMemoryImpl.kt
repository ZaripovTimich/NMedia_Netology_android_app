package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Меняем карьеру через образование",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "24 марта в 23:23",
        likedByMe = false,
        likes = 2099,
        countLikes = "2099",
        shares = 1949,
        countShares = "1949",
        views = 3000
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
        if (post.likedByMe) {
            post.likes++
            post.countLikes = post.likes.toString()
        } else {
            post.likes--
            post.countLikes = post.likes.toString()
        }
    }
    override fun share() {
        post = post.copy()
        data.value = post
        post.shares++
        post.countShares = post.shares.toString()
    }


}