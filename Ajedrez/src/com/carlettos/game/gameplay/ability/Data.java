package com.carlettos.game.gameplay.ability;

/**
 * Its the data about the ability. YES I WANTED TO USE A RECORD SO I USE
 * IT HERE.
 *
 * @author Carlettos
 */
public record Data(String name, String description, int cooldown, int manaCost, String params) {
}
