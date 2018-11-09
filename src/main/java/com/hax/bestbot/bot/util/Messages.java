package com.hax.bestbot.bot.util;

import com.hax.bestbot.bot.Executor;
import net.dv8tion.jda.core.EmbedBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Messages {

    public static void sendMessage(Executor executor, String title, String text) {
        executor.execute(
                event -> {
                    event.getChannel().sendMessage(new EmbedBuilder().setTitle(title).setDescription(text).build()).queue();
                },
                telegramEvent -> {
                    try {
                        telegramEvent.getTelegramBot().execute(
                                new SendMessage()
                                        .setChatId(telegramEvent.getUpdate().getMessage().getChatId())
                                        .enableMarkdown(true)
                                        .setText("**"+title+"**\n"+text)
                        );
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
