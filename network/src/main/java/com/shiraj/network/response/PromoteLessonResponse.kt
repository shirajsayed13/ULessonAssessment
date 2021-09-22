package com.shiraj.network.response


import com.shiraj.core.model.PromotedLesson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


internal fun PromoteLessonResponse.Data.toPromoteLesson() = PromotedLesson(
    createdAt = createdAt,
    expiresAt = expiresAt,
    id = id,
    imageUrl = imageUrl,
    startAt = startAt,
    status = status,
    subject = subject.name,
    topic = topic,
    tutor = tutorName("${tutor.firstname} ${tutor.lastname}")
)

internal fun tutorName(tutor: String): String {
    return tutor.replace("null", "")
}


@JsonClass(generateAdapter = true)
data class PromoteLessonResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "success")
    val success: Boolean
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "createdAt")
        val createdAt: String,
        @Json(name = "expires_at")
        val expiresAt: String,
        val id: String,
        @Json(name = "image_url")
        val imageUrl: String,
        @Json(name = "start_at")
        val startAt: String,
        val status: String,
        val subject: Subject,
        val topic: String,
        val tutor: Tutor,
    ) {
        @JsonClass(generateAdapter = true)
        data class Subject(
            val name: String
        )

        @JsonClass(generateAdapter = true)
        data class Tutor(
            @Json(name = "firstname")
            val firstname: String?,
            @Json(name = "lastname")
            val lastname: String?,
        )
    }
}