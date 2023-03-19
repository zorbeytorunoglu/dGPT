package com.zorbeytorunoglu.dGPT

import com.theokanning.openai.OpenAiService
import com.zorbeytorunoglu.dGPT.configuration.Data
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import java.time.Duration
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val format = Json { prettyPrint = true }

    val defaultData = Data("","","text-davinci-003",0.7,1.0,0.0,0.0,200,10,60)

    val file = File("config.json")

    if (!file.exists()) {
        if (file.createNewFile()) file.writeText(format.encodeToString(defaultData))
        println("Config file could not be found. Generated a new one. Please restart.")
        exitProcess(0)
    }

    val config = format.decodeFromString<Data>(file.readText())

    val openAiService = OpenAiService(config.chatGPTToken, Duration.ofSeconds(config.requestTimeOut))

    JDABuilder.createDefault(config.discordToken, mutableListOf(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES))
        .addEventListeners(com.zorbeytorunoglu.dGPT.commands.Commands(GPT(openAiService, config))).build()

}

data class GPT(val openAiService: OpenAiService, val data: Data)