package com.shiraj.core.usecase

import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.LessonWS
import javax.inject.Inject

class GetLessonUseCase @Inject constructor(
    private val lessonWS: LessonWS
) {
    suspend operator fun invoke(): List<PromotedLesson> {
        println("CHECK THIS invoke")
        return lessonWS.getLessonWS()
    }
}