package com.shiraj.gui.live_lesson.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.databinding.TileCorouselBinding
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
internal class LessonCarouselAdapter @Inject constructor() :
    RecyclerView.Adapter<LessonCarouselAdapter.CarouselBannerVH>() {

    internal var banners: List<PromotedLesson> by Delegates.observable(arrayListOf()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselBannerVH(
        TileCorouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CarouselBannerVH, position: Int) {
        holder.bind(banners[position])
    }

    override fun getItemCount(): Int = banners.size

    inner class CarouselBannerVH(private val binding: TileCorouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                tvModuleTitle.text = carouselBanner.topic
                tvTutorName.text = carouselBanner.subject
            }
        }
    }
}