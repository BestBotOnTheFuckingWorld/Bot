package com.hax.bestbot.bot.entities;

import com.hax.bestbot.bot.util.DescriptionBuilder;
import com.hax.bestbot.bot.events.PluginEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Plugin {

    void onCommand(PluginEvent event);

    default List<Listener> addListeners() {
        return new ArrayList<>(
                Arrays.asList(

                )
        );
    }

    String[] labels();

    String description();

    DescriptionBuilder detailedDescription();

    PERMS perms();

    boolean ACTIVATED = true;

    default void onEnable() {}

    default void onDisable() {}



}
