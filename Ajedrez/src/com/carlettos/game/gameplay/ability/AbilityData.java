package com.carlettos.game.gameplay.ability;

import com.carlettos.game.util.resource.TranslateResource;

/**
 * Its the data about the ability.
 *
 * @author Carlettos
 */
public record AbilityData(TranslateResource name, TranslateResource description, int cooldown, int manaCost) {

    public AbilityData(String name, int cooldown, int manaCost) {
        this(new TranslateResource("ability.name", name), new TranslateResource("ability.description", name), cooldown,
                manaCost);
    }

    public String getName() {
        return this.name().getTranslated();
    }

    public String getDescription() {
        return this.description().getTranslated();
    }
}
