package com.shiraj.core.model


data class PromotedLesson(
    val createdAt: String,
    val expiresAt: String,
    val id: String,
    val imageUrl: String,
    val startAt: String,
    val status: String,
    val subject: String,
    val topic: String,
    val tutor: String
)

