package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.clock.Time;
import com.carlettos.game.util.resource.TranslateResource;

/**
 * Its the data about the ability.
 *
 * @author Carlettos
 */
public record AbilityData(TranslateResource name, TranslateResource description, Time cooldown, int manaCost) {

    public AbilityData(String name, Time cooldown, int manaCost) {
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
