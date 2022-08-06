package com.carlettos.game.gameplay.card;

import com.carlettos.game.util.resource.TranslateResource;

public record CardData(TranslateResource name, TranslateResource description, int manaCost) {
    public CardData(String name, int manaCost) {
        this(new TranslateResource("card.name", name), new TranslateResource("card.description", name), manaCost);
    }

    public String getName() {
        return this.name().getTranslated();
    }

    public String getDescription() {
        return this.description().getTranslated();
    }
}
