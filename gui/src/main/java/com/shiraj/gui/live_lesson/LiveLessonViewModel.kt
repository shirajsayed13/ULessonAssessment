package com.shiraj.gui.live_lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.usecase.GetLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LiveLessonViewModel @Inject constructor(
    private val lessonUseCase: GetLessonUseCase
) : BaseViewModel() {

    private val _lesson: MutableLiveData<List<PromotedLesson>> by lazy { MutableLiveData() }
    internal val lesson: LiveData<List<PromotedLesson>> = _lesson

    internal fun loadLesson() {
        println("CHECK THIS loadLesson")
        launchUseCase {
            _lesson.postValue(lessonUseCase())
        }
    }
}