package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.ActionResult;

/**
 * Piece type, can be added or removed from a piece during game. They serve as a
 * way to add shared functionability to pieces, and works as a tag.
 *
 * @author Carlos
 */
public interface IPieceType {

    /**
     * A biologic piece it's a normal piece.
     */
    public static final IPieceType BIOLOGIC = new Biologic();

    /**
     * A structure piece it's a piece that is a structure.
     */
    public static final IPieceType STRUCTURE = new Structure();

    /**
     * If a piece is not transportable, cannot be used for catapult.
     */
    public static final IPieceType TRANSPORTABLE = new Transportable();

    /**
     * An inmune piece cannot recieve abilities (even from allies).
     */
    public static final IPieceType IMMUNE = new Inmune();

    /**
     * An heroic piece cannot be attacked (just taken).
     */
    public static final IPieceType HEROIC = new Heroic();

    /**
     * Demonic pieces gives one mana to its player when they are taken.
     */
    public static final IPieceType DEMONIC = new Demonic();

    /**
     * Its executed every time that a piece with this type tries to makes an action.
     * The other (and its pos) will be null on movement or in some abilities.
     *
     * @param data record with the information.
     */
    ActionResult can(PieceTypeData data);

    /**
     * Its executed every time that a piece with this type makes an action. The
     * other (and its pos) will be null on movement or in some abilities.
     *
     * @param data record with the information.
     */
    void on(PieceTypeData data);

    /**
     * Its executed every time that some other piece tries to make an action to a
     * piece with this type. The other (and its pos) will be null in some abilities.
     *
     * @param data record with the information.
     */
    ActionResult canBe(PieceTypeData data);

    /**
     * Its executed every time that some other piece makes an action to a piece with
     * this type. The other (and its pos) will be null in some abilities.
     *
     * @param data record with the information.
     */
    void onBe(PieceTypeData data);
}
