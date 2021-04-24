package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
            Post(
                    id = nextId++,
                    author = "Нетология. Меняем карьеру через образование",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                            "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                            "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                            "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                            "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "24 марта в 23:23",
                    likedByMe = false,
                    likes = 2099,
                    shares = 550,
                    views = 3000
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Меняем карьеру через образование",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                            "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                            "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                            "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                            "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "25 марта в 01:15",
                    likedByMe = false,
                    likes = 450,
                    shares = 20,
                    views = 3000
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Меняем карьеру через образование",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                            "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                            "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                            "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                            "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "25 марта в 10:00",
                    likedByMe = false,
                    likes = 1100,
                    shares = 1000,
                    views = 3000
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Меняем карьеру через образование",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                            "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                            "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                            "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                            "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "25 марта в 12:50",
                    likedByMe = false,
                    likes = 100,
                    shares = 32,
                    views = 3000
            ),
            Post(
                    id = nextId++,
                    author = "Нетология. Меняем карьеру через образование",
                    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.\n" +
                            "         Затем появились курсы по дизайну, разработке, аналитике и управлению.\n" +
                            "         Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n" +
                            "         Мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее.\n" +
                            "         Наша миссия - помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                    published = "25 марта в 21:13",
                    likedByMe = false,
                    likes = 2099,
                    shares = 33,
                    views = 3000
            ),
    ).reversed()

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else {
                /*if (it.likedByMe) {
                    it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                } else {
                    it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
                }*/
                if (it.id != id) it else it.copy(
                        likedByMe = !it.likedByMe,
                        likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }
    override fun save(post: Post) {
        if (post.id == 0L) {
            //TODO: remove hardcoded author & published
            posts = listOf(
                    post.copy(
                            id = nextId++,
                            author = "Me",
                            likedByMe = false,
                            published = "now"
                    )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}