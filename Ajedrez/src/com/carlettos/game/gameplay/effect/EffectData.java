package com.carlettos.game.gameplay.effect;

import com.carlettos.game.util.resource.ImageResource;
import com.carlettos.game.util.resource.TranslateResource;

public record EffectData(String name, ImageResource image, TranslateResource translation, int maxTurns) {
    public EffectData(String name, int maxTurns) {
        this(name, new ImageResource("effect", name), new TranslateResource("effect", name), maxTurns);
    }
}
