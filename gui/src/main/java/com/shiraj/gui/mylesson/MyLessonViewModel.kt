package com.shiraj.gui.mylesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.usecase.GetLessonsMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLessonViewModel @Inject constructor(
    private val lessonsMeUseCase: GetLessonsMeUseCase
): BaseViewModel() {

    private val _lessonsMe: MutableLiveData<List<PromotedLesson>> by lazy { MutableLiveData() }
    internal val lessonsMe: LiveData<List<PromotedLesson>> = _lessonsMe

    internal fun loadLessonsMe() {
        launchUseCase {
            _lessonsMe.postValue(lessonsMeUseCase())
        }
    }
}