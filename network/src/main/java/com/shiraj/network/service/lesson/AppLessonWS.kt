package com.shiraj.network.service.lesson

import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.LessonWS
import com.shiraj.network.response.toPromoteLesson
import com.shiraj.network.service.networkCall
import javax.inject.Inject

internal class AppLessonWS @Inject constructor(
    private val lessonWebService: RetrofitLessonWebService
) : LessonWS {

    override suspend fun getLessonWS(): List<PromotedLesson> = networkCall(
        { lessonWebService.fetchPromotedLesson() },
        { response -> response.data.map { it.toPromoteLesson() } }
    )
}