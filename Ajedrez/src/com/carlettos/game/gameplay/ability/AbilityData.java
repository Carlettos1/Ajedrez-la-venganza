package com.carlettos.game.gameplay.ability;

import com.carlettos.game.util.ResourceLocation;

/**
 * Its the data about the ability. YES I WANTED TO USE A RECORD SO I USE
 * IT HERE.
 *
 * @author Carlettos
 */
public record AbilityData(String key, ResourceLocation name, ResourceLocation description, int cooldown, int manaCost) {
    
    public AbilityData(String key, int cooldown, int manaCost) {
        this(key, new ResourceLocation("ability.name.".concat(key)), new ResourceLocation("ability.description.".concat(key)), cooldown, manaCost);
    }
    
    public String getName() {
        return this.name().getTranslated();
    }
    
    public String getDescription() {
        return this.description().getTranslated();
    }
}
