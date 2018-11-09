package com.hax.bestbot.bot;

import com.hax.bestbot.bot.events.TelegramEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.Consumer;

public class Executor {

    private TelegramEvent telegramEvent;
    private GuildMessageReceivedEvent event;

    public Executor(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public Executor(TelegramEvent telegramEvent) {
        this.telegramEvent = telegramEvent;
    }

    public void executeDiscord(Consumer<GuildMessageReceivedEvent> discord) {
        execute(discord, null);
    }

    public void executeTelegram(Consumer<TelegramEvent> telegram) {
        execute(null, telegram);
    }

    public void execute(Consumer<GuildMessageReceivedEvent> discord, Consumer<TelegramEvent> telegram) {
        if (event!=null) {
            discord.accept(event);
        } else if (telegramEvent!=null) {
            telegram.accept(telegramEvent);
        }
    }

}
