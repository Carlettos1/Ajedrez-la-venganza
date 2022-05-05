package com.carlettos.game.gameplay.ability;

import com.carlettos.game.util.ResourceLocation;

/**
 * Its the data about the ability. YES I WANTED TO USE A RECORD SO I USE
 * IT HERE.
 *
 * @author Carlettos
 */
public record Data(ResourceLocation name, ResourceLocation description, int cooldown, int manaCost) {
    public String getName() {
        return this.name().getTranslated();
    }
    
    public String getDescription() {
        return this.description().getTranslated();
    }
}
