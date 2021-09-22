package com.shiraj.gui.live_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.databinding.TileLessonBinding
import com.shiraj.gui.databinding.TileLiveLessonBinding
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
internal class LiveLessonAdapter @Inject constructor(): RecyclerView.Adapter<LiveLessonAdapter.CarouselBannerVH>() {

    var myLessons: List<PromotedLesson> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselBannerVH(
        TileLiveLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CarouselBannerVH, position: Int) {
        holder.bind(myLessons[position])
    }

    override fun getItemCount(): Int = myLessons.size

    inner class CarouselBannerVH(private val binding: TileLiveLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                tvModuleTitle.text = carouselBanner.topic
                tvSubject.text = carouselBanner.subject
            }
        }
    }
}