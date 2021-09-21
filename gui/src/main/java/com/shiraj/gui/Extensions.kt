package com.shiraj.gui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.util.*

internal fun getDate(yourDate: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(yourDate)
    val dayWeekText = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
    return dayWeekText.toString()
}

internal fun ImageView.loadUrl(
    url: String,
    @DrawableRes placeholderResId: Int? = null,
    scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
) {
    val builder = Glide.with(this).load(url)
    if (null != placeholderResId) builder.placeholder(placeholderResId)
    builder.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            this@loadUrl.scaleType = scaleType
            return false
        }

    })
        .into(this)

}