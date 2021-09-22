package com.shiraj.gui

import androidx.lifecycle.MutableLiveData
import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.LessonWS

class FakeLessonRepository : LessonWS {

    private val lessons = mutableListOf<PromotedLesson>()

    private val observablePromotedLesson = MutableLiveData<List<PromotedLesson>>(lessons)

    fun updateLesson() {
        observablePromotedLesson.postValue(lessons)
    }

    override suspend fun getPromoteWS(): List<PromotedLesson> {
        return lessons
    }

    override suspend fun getLessonsWS(): List<PromotedLesson> {
        return lessons
    }

    override suspend fun getLessonsMeWS(): List<PromotedLesson> {
        return lessons
    }
}