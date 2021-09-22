package com.shiraj.network.service.lesson

import com.shiraj.network.response.PromoteLessonResponse
import retrofit2.http.GET

internal interface RetrofitLessonWebService {

    @GET("promoted")
    suspend fun fetchPromoted(): PromoteLessonResponse

    @GET("lessons")
    suspend fun fetchLessons(): PromoteLessonResponse

    @GET("lessons/me")
    suspend fun fetchLessonsMe(): PromoteLessonResponse
}