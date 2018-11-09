package com.hax.bestbot.bot.events;

import com.hax.bestbot.bot.entities.Config;
import com.hax.bestbot.bot.Executor;
import com.hax.bestbot.bot.entities.Plugin;

import java.util.List;

public class PluginEvent  {

    private List<Plugin> plugins;
    private Config config;
    private Executor executor;

    public PluginEvent(List<Plugin> plugins, Config config, Executor executor) {
        this.plugins = plugins;
        this.config = config;
        this.executor = executor;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public Config getConfig() {
        return config;
    }

    public Executor getExecutor() {
        return executor;
    }
}
