package com.shiraj.gui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.shiraj.base.activity.BaseActivity
import com.shiraj.gui.databinding.ActivityUlessonAssessmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ULessonAssessmentActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_ulesson_assessment

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = ActivityUlessonAssessmentBinding::inflate

    override val binding: ActivityUlessonAssessmentBinding
        get() = super.binding as ActivityUlessonAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }
    }
}