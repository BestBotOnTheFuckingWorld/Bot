package com.hax.bestbot.bot.util;

import java.util.List;

public class DescriptionBuilder {

    private String description;
    private List<String> usages;
    private String[] labels;


    public DescriptionBuilder(String description, List<String> usages, String[] labels) {
        this.description = description;
        this.usages = usages;
        this.labels = labels;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsages(List<String> usages) {
        this.usages = usages;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getUsages() {
        return usages;
    }

}
