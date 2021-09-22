package com.shiraj.gui.live_lesson

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.databinding.TileLiveLessonBinding
import com.shiraj.gui.formatDate
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
internal class LiveLessonAdapter @Inject constructor() :
    RecyclerView.Adapter<LiveLessonAdapter.CarouselBannerVH>() {

    internal var onLessonClickListener: (PromotedLesson) -> Unit = {}

    var myLessons: List<PromotedLesson> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselBannerVH(
        TileLiveLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply {
        itemView.setOnClickListener {
            onLessonClickListener(myLessons[adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: CarouselBannerVH, position: Int) {
        holder.bind(myLessons[position])
    }

    override fun getItemCount(): Int = myLessons.size

    inner class CarouselBannerVH(private val binding: TileLiveLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                ivTutorPic.clipToOutline = true
                tvLive.text = carouselBanner.status
                tvTiming.text = formatDate(carouselBanner.createdAt)
                tvModuleTitle.text = carouselBanner.topic
                tvSubject.text = carouselBanner.subject
                tvTutorName.text = carouselBanner.tutor
                when {
                    carouselBanner.status.equals("live", true) -> {
                        tvLive.setBackgroundColor(Color.parseColor("#DA0000"))
                    }
                    carouselBanner.status.equals("upcoming", true) -> {
                        tvLive.setBackgroundColor(Color.parseColor("#606572"))
                    }
                    carouselBanner.status.equals("replay", true) -> {
                        tvLive.setBackgroundColor(Color.parseColor("#F2984D"))
                    }
                }
            }
        }
    }
}