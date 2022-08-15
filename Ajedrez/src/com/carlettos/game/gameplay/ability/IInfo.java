package com.carlettos.game.gameplay.ability;

public interface IInfo {
    default Info toInfo() {
        return Info.getInfo(this);
    }
}
