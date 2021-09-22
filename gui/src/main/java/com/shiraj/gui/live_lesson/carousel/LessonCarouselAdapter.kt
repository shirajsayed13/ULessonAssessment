package com.shiraj.gui.live_lesson.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.R
import com.shiraj.gui.databinding.TileCorouselBinding
import com.shiraj.gui.formatDate
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class LessonCarouselAdapter @Inject constructor() :
    RecyclerView.Adapter<LessonCarouselAdapter.CarouselBannerVH>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PromotedLesson>() {
        override fun areItemsTheSame(oldItem: PromotedLesson, newItem: PromotedLesson): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PromotedLesson, newItem: PromotedLesson): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var banners: List<PromotedLesson>
        get() = differ.currentList
        set(value) = differ.submitList(value)

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
                tvTiming.text = formatDate(carouselBanner.createdAt)
                tvStatus.text = carouselBanner.status
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