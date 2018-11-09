package com.hax.bestbot.bot.listener;

import com.hax.bestbot.bot.Executor;
import com.hax.bestbot.bot.Main;
import com.hax.bestbot.bot.entities.Listener;
import com.hax.bestbot.bot.entities.PERMS;
import com.hax.bestbot.bot.entities.Plugin;
import com.hax.bestbot.bot.events.PluginEvent;
import com.hax.bestbot.bot.events.TelegramEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

public class CommandListener extends Listener {

    private Main main;

    public CommandListener(Main main) {
        this.main = main;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()&&update.getMessage().hasText()) {
            Plugin plugin = getPlugin(update.getMessage().getText().replaceFirst(main.getConfig().getPrefix(), "").replaceFirst("/", "").replaceFirst("@BestBotOnTheFuckingWorld_Bot ", ""));
            if (plugin!=null) {
                if (plugin.perms() == PERMS.USER||main.getConfig().getBotownersAsList().contains(String.valueOf(update.getMessage().getChatId())))
                    plugin.onCommand(new PluginEvent(main.getPlugins(), main.getConfig(), new Executor(new TelegramEvent(update, main.getTelegramBot()))));
            }
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            Plugin plugin = getPlugin(event.getMessage().getContentRaw().replaceFirst(main.getConfig().getPrefix(), "").replaceFirst("<@508331605337833497> ", ""));
            if (plugin!=null) {
                if (plugin.perms() == PERMS.USER||main.getConfig().getBotownersAsList().contains(event.getAuthor().getId()))
                    plugin.onCommand(new PluginEvent(main.getPlugins(), main.getConfig(), new Executor(event)));
            }
        }
    }

    private Plugin getPlugin(String message) {
        for (Plugin plugin:main.getPlugins()) {
            for (String label:plugin.labels()) {
                if (message.startsWith(label)) {
                    return plugin;
                }
            }
        }
        return null;
    }
}
