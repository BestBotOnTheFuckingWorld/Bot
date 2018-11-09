package com.hax.bestbot.bot;

import com.hax.bestbot.bot.entities.Config;
import com.hax.bestbot.bot.entities.Listener;
import com.hax.bestbot.bot.entities.Plugin;
import com.hax.bestbot.bot.listener.CommandListener;
import com.hax.bestbot.bot.plugins.PluginLoader;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private Config config;
    private ShardManager shardManager;
    private Logger logger = LoggerFactory.getLogger(Main.class);
    private List<Plugin> plugins;
    private TelegramBot telegramBot;


    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (Exception e) {
            new Main().logger.error("An Exception occurred", e);
        }
    }

    public void run() throws IOException {
        this.config = Config.loadConfig("config.json");
        File pluginsDirectory = new File(config.getPluginsFolder());
        if(!pluginsDirectory.exists() && !pluginsDirectory.mkdirs()) {
            logger.error("Can't create the plugins Folder...");
            System.exit(1);
        }
        plugins = PluginLoader.loadPlugins(pluginsDirectory);
        ArrayList<Listener> listeners = new ArrayList<>(Arrays.asList(new CommandListener(this)));
        plugins.forEach(plugin -> listeners.addAll(plugin.addListeners()));

        // Telegram Bot
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        telegramBot = new TelegramBot(config, listeners);
        try {
            api.registerBot(telegramBot);
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
        listeners.forEach(listener -> {shardManager.addEventListener(listener);});
    }

    public Config getConfig() {
        return config;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }
}
