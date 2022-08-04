package com.carlettos.game.gameplay.card.onBoard;

public final class CardsOnBoard {
    private CardsOnBoard() {}

    // Magician
    public static final CardOnBoard ICE = new Ice();
    public static final CardOnBoard FIRE = new Fire();

    // Paladin
    public static final CardOnBoard ATTACK_TO_DEMONIC = new AttackToDemonic();
    public static final CardOnBoard INVULNERABILITY = new Invulnerability();
    public static final CardOnBoard REVIVE = new Revive();
}
