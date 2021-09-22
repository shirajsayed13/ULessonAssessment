package com.shiraj.gui.live_lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.WebServiceFailure
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentLiveLessonBinding
import com.shiraj.gui.live_lesson.carousel.CarouselBannersAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

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

    override fun onInitView() {
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(lesson, ::showLesson)
            loadLesson()
            setupRecyclerView()
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
    }

    private fun showLesson(list: List<PromotedLesson>) {
        liveLessonAdapter.myLessons = list
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

}