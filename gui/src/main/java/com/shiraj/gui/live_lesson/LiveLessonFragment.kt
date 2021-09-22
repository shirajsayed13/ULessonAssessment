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
import androidx.viewpager2.widget.ViewPager2
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
            observe(promote, ::showPromote)
            loadLesson()
            loadPromote()
            setupPromoteCarousel()
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
            toolbar.setNavigationOnClickListener { requireActivity().finish() }
        }
    }

    private fun showPromote(promotes: List<PromotedLesson>) {
        lessonCarouselAdapter.banners = promotes
    }

    private fun setupRecyclerView() = with(binding.rvMyLesson) {
        setHasFixedSize(true)
        adapter = liveLessonAdapter
    }

    private fun setupPromoteCarousel() = with(binding.vpCarousel) {
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

    private fun showLesson(lessons: List<PromotedLesson>) {
        liveLessonAdapter.myLessons = lessons
        binding.pbLoading.visibility = View.GONE
    }

    private val actionSwitchLessonCarousel: Runnable = Runnable {
        binding.vpCarousel.apply {
            currentItem =
                if (currentItem == lessonCarouselAdapter.banners.size - 1) 0 else currentItem + 1
        }
        scheduleCarouselBannerSwitch()
    }

    private val carouselPageChangeCallback: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> scheduleCarouselBannerSwitch()
                    else -> binding.vpCarousel.removeCallbacks(actionSwitchLessonCarousel)
                }
            }
        }

    override fun onPause() {
        binding.vpCarousel.apply {
            removeCallbacks(actionSwitchLessonCarousel)
            unregisterOnPageChangeCallback(carouselPageChangeCallback)
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        scheduleCarouselBannerSwitch()
        binding.vpCarousel.apply {
            registerOnPageChangeCallback(carouselPageChangeCallback)
        }
    }

    private fun scheduleCarouselBannerSwitch() {
        binding.vpCarousel.apply {
            removeCallbacks(actionSwitchLessonCarousel)
            postDelayed(actionSwitchLessonCarousel, BANNER_SWITCH_TIME_MS)
        }
    }

    companion object {
        const val BANNER_SWITCH_TIME_MS = 5000L
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