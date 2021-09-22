package com.shiraj.core.webservice

import com.shiraj.core.model.PromotedLesson


interface LessonWS {
    suspend fun getPromoteWS(): List<PromotedLesson>
    suspend fun getLessonsWS(): List<PromotedLesson>
    suspend fun getLessonsMeWS(): List<PromotedLesson>
}