package com.shiraj.gui.live_lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.snackbar.Snackbar
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.WebServiceFailure
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentLiveLessonBinding
import com.shiraj.gui.live_lesson.carousel.LessonCarouselAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
internal class LiveLessonFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_live_lesson

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentLiveLessonBinding::inflate

    override val binding: FragmentLiveLessonBinding
        get() = super.binding as FragmentLiveLessonBinding

    private val viewModel: LiveLessonViewModel by viewModels()

    @Inject
    internal lateinit var liveLessonAdapter: LiveLessonAdapter

    @Inject
    internal lateinit var lessonCarouselAdapter: LessonCarouselAdapter

    override fun onInitView() {
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(lesson, ::showLesson)
            loadLesson()
            setupRecyclerView()
        }

        liveLessonAdapter.onLessonClickListener = { lesson ->
            if (lesson.status.equals("live", true))
                showSnackbar(lesson.topic)
        }

        binding.apply {
            fab.setOnClickListener {
                findNavController().navigate(LiveLessonFragmentDirections.toMyLessonFragment())
            }
        }
    }

    private fun setupRecyclerView() = with(binding.rvMyLesson) {
        setHasFixedSize(true)
        adapter = liveLessonAdapter
        binding.apply {
            vpCarousel.apply {
                adapter = lessonCarouselAdapter
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                setPageTransformer(CompositePageTransformer().apply {
                    addTransformer(
                        MarginPageTransformer(
                            resources.getDimension(R.dimen.home_carousel_banner_margin).toInt()
                        )
                    )
                    addTransformer { page, position ->
                        page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
                    }
                })
            }
        }
    }

    private fun showLesson(list: List<PromotedLesson>) {
        liveLessonAdapter.myLessons = list
        lessonCarouselAdapter.banners = list
        binding.pbLoading.visibility = View.GONE
    }


    private fun handleFailure(e: Exception?) {
        Timber.v("handleFailure: IN")
        Timber.e(e)
        when (e) {
            is WebServiceFailure.NoNetworkFailure -> showErrorToast("No internet connection!")
            is WebServiceFailure.NetworkTimeOutFailure, is WebServiceFailure.NetworkDataFailure -> showErrorToast(
                "Internal server error!"
            )
            else -> {
                //showErrorScreen()
            }
        }
        Timber.v("handleFailure: OUT")
    }

    private fun Fragment.showErrorToast(msg: String) {
        AppToast.show(requireContext(), msg, Toast.LENGTH_SHORT)
    }

    internal fun showSnackbar(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}