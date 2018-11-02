package com.hax.bestbot.bot.core;

import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.security.auth.login.LoginException;

public class Main {

    private Config config;
    private ShardManager shardManager;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        this.config = Config.loadConfig("config.json");

        // Telegram Bot
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new TelegramBot(config));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Discord Bot
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(config.getDiscordToken());
        builder.setShardsTotal(config.getShards());
        try {
            shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
