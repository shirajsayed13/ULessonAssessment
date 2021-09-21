package com.shiraj.gui.mylesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.usecase.GetLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyLessonViewModel @Inject constructor(
    private val lessonUseCase: GetLessonUseCase
): BaseViewModel() {

    private val _lesson: MutableLiveData<List<PromotedLesson>> by lazy { MutableLiveData() }
    internal val lesson: LiveData<List<PromotedLesson>> = _lesson

    internal fun loadLessonUseCase() {
        Timber.d("lessonUseCase: ")
        launchUseCase {
            _lesson.postValue(lessonUseCase())
        }
    }
}