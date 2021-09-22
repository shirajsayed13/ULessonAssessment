package com.shiraj.gui.live_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.PromotedLesson
import com.shiraj.gui.R
import com.shiraj.gui.databinding.TileLiveLessonBinding
import com.shiraj.gui.formatDate
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class LiveLessonAdapter @Inject constructor() :
    RecyclerView.Adapter<LiveLessonAdapter.LiveLessonVH>() {

    internal var onLessonClickListener: (PromotedLesson) -> Unit = {}

    private val diffCallback = object : DiffUtil.ItemCallback<PromotedLesson>() {
        override fun areItemsTheSame(oldItem: PromotedLesson, newItem: PromotedLesson): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PromotedLesson, newItem: PromotedLesson): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var myLessons: List<PromotedLesson>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LiveLessonVH(
        TileLiveLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply {
        itemView.setOnClickListener {
            onLessonClickListener(myLessons[adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: LiveLessonVH, position: Int) {
        holder.bind(myLessons[position])
    }

    override fun getItemCount(): Int = myLessons.size

    inner class LiveLessonVH(private val binding: TileLiveLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselBanner: PromotedLesson) {
            binding.apply {
                ivTutorPic.loadUrl(carouselBanner.imageUrl)
                ivTutorPic.clipToOutline = true
                tvStatus.text = carouselBanner.status
                tvTiming.text = formatDate(carouselBanner.createdAt)
                tvModuleTitle.text = carouselBanner.topic
                tvSubject.text = carouselBanner.subject
                tvTutorName.text = carouselBanner.tutor
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