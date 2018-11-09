package com.hax.bestbot.bot.events;

import com.hax.bestbot.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramEvent {

    private Update update;
    private TelegramBot telegramBot;

    public TelegramEvent(Update update, TelegramBot telegramBot) {
        this.update = update;
        this.telegramBot = telegramBot;
    }

    public TelegramEvent() {}

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public Update getUpdate() {
        return update;
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }
}
