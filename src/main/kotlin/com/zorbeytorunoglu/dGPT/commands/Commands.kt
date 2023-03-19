package com.zorbeytorunoglu.dGPT.commands

import com.theokanning.openai.completion.CompletionRequest
import com.zorbeytorunoglu.dGPT.GPT
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.lang.StringBuilder
import kotlin.concurrent.thread

class Commands(private val gpt: GPT): ListenerAdapter() {

    @Override
    override fun onMessageReceived(event: MessageReceivedEvent) {

        if (event.author.isBot) return

        if (!event.isFromGuild) return

        if (!event.message.contentRaw.startsWith("-dgpt")) return

        kotlin.runCatching {

            val builder = StringBuilder()

            val reply = event.channel.sendMessage("...").complete()

            val message = event.message.contentRaw.substring(6)

            thread {
                var lastReasons = "length"
                var maxAttempt = 0

                while (true) {

                    kotlin.runCatching {

                        gpt.openAiService.createCompletion(CompletionRequest.builder().model(gpt.data.model)
                            .temperature(gpt.data.temperature)
                            .topP(gpt.data.topP)
                            .frequencyPenalty(gpt.data.frequencyPenalty)
                            .presencePenalty(gpt.data.presencePenalty)
                            .maxTokens(gpt.data.maxTokens)
                            .prompt(if (maxAttempt == 0) message else "$message $builder")
                            .build()).choices.forEach {
                            builder.append(it.text)
                            lastReasons = it.finish_reason
                        }
                    }.onFailure {
                        reply.editMessage(builder.toString() + "\n" + it.toString()).queue()
                        return@thread
                    }

                    if (maxAttempt == 0) {

                        reply.editMessage(builder.toString()).queue()

                    } else {

                        reply.editMessage(builder.toString()).queue()

                    }

                    Thread.sleep(300)

                    maxAttempt++

                    if (maxAttempt > gpt.data.maxAttempt || builder.length > 2000 || lastReasons != "length") break

                }
            }

        }

    }

}