package ru.netology.nmedia.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.DetailsFragmentBinding
import ru.netology.nmedia.fragments.NewPostFragment.Companion.textArg
import ru.netology.nmedia.viewmodel.PostViewModel

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private var post: Post? = null

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DetailsFragmentBinding.inflate(inflater, container, false).also { binding ->
        with(binding.detailsPost) {
            post = arguments?.get("post") as Post
            initUi(binding.detailsPost)
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                viewModel.removeById(post?.id ?: 0)
                                findNavController().navigateUp()
                                true
                            }
                            R.id.edit -> {
                                post?.let {
                                    viewModel.edit(it)
                                }
                                findNavController().navigate(
                                    R.id.action_detailsFragment_to_newPostFragment,
                                    Bundle().apply {
                                        textArg = post?.content
                                    }
                                )
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            like.setOnClickListener {
                viewModel.likeById(post?.id ?: 0)
            }
            share.setOnClickListener {
                viewModel.shareById(post?.id ?: 0)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post?.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
            flContainerVideo.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_WEB_SEARCH
                    putExtra(Intent.ACTION_WEB_SEARCH, Uri.parse(post?.videoContent))
                }

                val videoIntent = Intent.createChooser(intent, getString(R.string.chooser_app))
                startActivity(videoIntent)
            }
            viewModel.data.observe(viewLifecycleOwner) {
                post = it.firstOrNull { it.id == post?.id }
                initUi(binding.detailsPost)
            }
        }
    }.root

    private fun initUi(cardPostBinding: CardPostBinding) {
        with(cardPostBinding) {
            author.text = post?.author
            published.text = post?.published
            content.text = post?.content
            like.text = post?.likes.toString()
            share.text = post?.shares.toString()
            view.text = post?.views.toString()

            post?.videoPreview?.let { preview ->
                context?.let {
                    Glide.with(it)
                        .load(preview)
                        .into(ivVideoPreview)
                }
            }
        }
    }
}