package com.shiraj.network.service.lesson

import com.shiraj.core.model.PromotedLesson
import com.shiraj.core.webservice.LessonWS
import com.shiraj.network.response.toPromoteLesson
import com.shiraj.network.service.networkCall
import javax.inject.Inject

internal class AppLessonWS @Inject constructor(
    private val lessonWebService: RetrofitLessonWebService
) : LessonWS {

    override suspend fun getPromoteWS(): List<PromotedLesson> = networkCall(
        { lessonWebService.fetchPromoted() },
        { response -> response.data.map { it.toPromoteLesson() } }
    )

    override suspend fun getLessonsWS(): List<PromotedLesson> = networkCall(
        { lessonWebService.fetchLessons() },
        { response -> response.data.map { it.toPromoteLesson() } }
    )

    override suspend fun getLessonsMeWS(): List<PromotedLesson> = networkCall(
        { lessonWebService.fetchLessonsMe() },
        { response -> response.data.map { it.toPromoteLesson() } }
    )
}