package ru.netology.nmedia.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import ru.netology.nmedia.OnInteractionListener
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.fragments.NewPostFragment.Companion.textArg
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FeedFragmentBinding.inflate(inflater, container, false).also { binding ->

        val adapter = PostAdapter(object : OnInteractionListener {

            override fun onPlayVideo(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_WEB_SEARCH
                    putExtra(Intent.ACTION_WEB_SEARCH, Uri.parse(post.videoContent))
                }

                val videoIntent = Intent.createChooser(intent, getString(R.string.chooser_app))
                startActivity(videoIntent)
            }

            override fun onClickPost(post: Post) =
                findNavController().navigate(
                    R.id.action_feedFragment_to_detailsFragment,
                    bundleOf(
                        "post" to post
                    )
                )

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
                null,
                navOptions {
                    anim {
                        android.R.anim.fade_in
                        android.R.anim.fade_out
                    }
                }
            )
        }
    }.root
}
