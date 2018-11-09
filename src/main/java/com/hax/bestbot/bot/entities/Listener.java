package com.hax.bestbot.bot.entities;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Listener extends ListenerAdapter {

    public void onUpdateReceived(Update update) {}

}
