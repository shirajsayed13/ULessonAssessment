package com.shiraj.core.webservice

import com.shiraj.core.model.PromotedLesson


interface LessonWS {
    suspend fun getLessonWS(): List<PromotedLesson>
}