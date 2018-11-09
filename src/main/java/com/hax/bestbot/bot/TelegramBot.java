package com.hax.bestbot.bot;

import com.hax.bestbot.bot.entities.Config;
import com.hax.bestbot.bot.entities.Listener;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot {

    Config config;
    ArrayList<Listener> listeners;

    public TelegramBot(Config config, ArrayList<Listener> listeners) {
        this.config = config;
        this.listeners = listeners;
    }

    public void onUpdateReceived(Update update) {
        listeners.forEach(listener -> listener.onUpdateReceived(update));
    }

    public <T> T execute(T t) {
        return execute(t);
    }

    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getTelegramToken();
    }

}
