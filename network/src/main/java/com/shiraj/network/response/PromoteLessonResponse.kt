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
)


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
        @Json(name = "id")
        val id: String,
        @Json(name = "image_url")
        val imageUrl: String,
        @Json(name = "start_at")
        val startAt: String,
        @Json(name = "status")
        val status: String,
        @Json(name = "subject")
        val subject: Subject,
        @Json(name = "topic")
        val topic: String,
    ) {
        @JsonClass(generateAdapter = true)
        data class Subject(
            @Json(name = "name")
            val name: String
        )
    }
}