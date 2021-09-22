package com.shiraj.gui.mylesson

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.databinding.TileLessonBinding
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
internal class MyLessonAdapter @Inject constructor() :
    RecyclerView.Adapter<MyLessonAdapter.CarouselBannerVH>() {

    var myLessons: List<PromotedLesson> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselBannerVH(
        TileLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CarouselBannerVH, position: Int) {
        holder.bind(myLessons[position])
    }

    override fun getItemCount(): Int = myLessons.size

    inner class CarouselBannerVH(private val binding: TileLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                tvModuleTitle.text = carouselBanner.topic
                tvSubject.text = carouselBanner.subject
                tvLive.text = carouselBanner.status
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

    class PromotedLessonDiffUtil(
        private val new: List<PromotedLesson>,
        private val old: List<PromotedLesson>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            new[newItemPosition] == old[oldItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            new[newItemPosition].id == old[oldItemPosition].id
    }
}