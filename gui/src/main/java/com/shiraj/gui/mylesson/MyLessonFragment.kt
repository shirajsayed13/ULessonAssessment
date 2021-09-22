package com.shiraj.gui.mylesson

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.WebServiceFailure
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentMyLessonBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyLessonFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_my_lesson

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentMyLessonBinding::inflate

    override val binding: FragmentMyLessonBinding
        get() = super.binding as FragmentMyLessonBinding

    @Inject
    internal lateinit var myLessonAdapter: MyLessonAdapter

    private val viewModel: MyLessonViewModel by viewModels()

    override fun onInitView() {
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(lessonsMe, ::showLessonsMe)
            loadLessonsMe()
            setupRecyclerView()
        }
    }


    private fun setupRecyclerView() = with(binding.rvMyLesson) {
        setHasFixedSize(true)
        adapter = myLessonAdapter
    }

    private fun showLessonsMe(lessonsMe: List<PromotedLesson>) {
        myLessonAdapter.myLessons = lessonsMe
        binding.pbLoading.visibility = GONE
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