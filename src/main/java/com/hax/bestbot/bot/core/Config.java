package com.hax.bestbot.bot.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    @SerializedName("TELEGRAM_AUTH")
    @Expose
    private String telegramToken;
    @SerializedName("DISCORD_TOKEN")
    @Expose
    private String discordToken;
    @SerializedName("GITHUB_OAUTH")
    @Expose
    private String githubToken;
    @SerializedName("BOTNAME")
    @Expose
    private String botName;
    @SerializedName("SHARDS")
    @Expose
    private int shards;

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private Gson gson;
    private String configPath;

    private Config(String configPath) {
        this.configPath = configPath;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation().create();
    }

    public static Config loadConfig(String path) {
        var config = new Config(path);
        if (!Files.exists(Paths.get(path))) {
            config.createConfig();
            return null;
        } else {
            try {
                config = config.gson.fromJson(new String(Files.readAllBytes(Paths.get(path))), config.getClass());
                if (config.verifyConfig()) {
                    logger.debug("Config successfully loaded!");
                    return config;
                } else {
                    logger.error("Something in your config is not filled up correctly (Shards count can not be 0)");
                    System.exit(1);
                    return null;
                }
            } catch (IOException e) {
                logger.error("Error while loading config", e);
                System.exit(1);
                return null;
            }
        }
    }

    private void setDefaultValues() {
        this.discordToken = "Your DiscordToken";
        this.telegramToken = "Your TelegramToken";
        this.githubToken = "Your GithubToken";
        this.botName = "Your Botname";
        this.shards = 1;
    }

    private void createConfig() {
        if (!Files.exists(Paths.get(this.configPath))) {
            try {
                this.setDefaultValues();
                var f = Files.createFile(Paths.get(this.configPath));
                var json = gson.toJson(this, Config.class);
                Files.write(f, json.getBytes());
                logger.info("Please fill out the config and restart the Bot!");
                System.exit(1);
            } catch (IOException e) {
                logger.error("Error while creating config", e);
                System.exit(1);
            }
        }
    }

    private boolean verifyConfig() {
        return !(this.discordToken == null || this.telegramToken == null || this.githubToken == null);
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public String getDiscordToken() {
        return discordToken;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public String getBotName() {
        return botName;
    }

    public int getShards() {
        return shards;
    }
}
