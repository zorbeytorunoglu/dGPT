package com.zorbeytorunoglu.dGPT.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val discordToken: String,
    val chatGPTToken: String,
    val model: String,
    val temperature: Double,
    val topP: Double,
    val frequencyPenalty: Double,
    val presencePenalty: Double,
    val maxTokens: Int,
    val maxAttempt: Int,
    val requestTimeOut: Long
)
