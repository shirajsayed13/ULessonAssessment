package com.shiraj.gui.mylesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.R
import com.shiraj.gui.databinding.TileLessonBinding
import com.shiraj.gui.formatDate
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
internal class MyLessonAdapter @Inject constructor() :
    RecyclerView.Adapter<MyLessonAdapter.MyLessonVH>() {

    var myLessons: List<PromotedLesson> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyLessonVH(
        TileLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyLessonVH, position: Int) {
        holder.bind(myLessons[position])
    }

    override fun getItemCount(): Int = myLessons.size

    inner class MyLessonVH(private val binding: TileLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                tvModuleTitle.text = carouselBanner.topic
                tvSubject.text = carouselBanner.subject
                tvStatus.text = carouselBanner.status
                tvTiming.text = formatDate(carouselBanner.createdAt)
                when {
                    carouselBanner.status.equals("live", true) -> {
                        tvStatus.setBackgroundResource(R.drawable.bg_live)
                    }
                    carouselBanner.status.equals("upcoming", true) -> {
                        tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_calendar_today,
                            0,
                            0,
                            0
                        );
                        tvStatus.setBackgroundResource(R.drawable.bg_upcoming)
                    }
                    carouselBanner.status.equals("replay", true) -> {
                        tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_play_arrow,
                            0,
                            0,
                            0
                        );
                        tvStatus.setBackgroundResource(R.drawable.bg_replay)
                    }
                }
            }
        }
    }
}