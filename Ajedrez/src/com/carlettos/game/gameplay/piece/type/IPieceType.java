package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.gameplay.piece.demonic.Necromancer;
import com.carlettos.game.gameplay.piece.property.Properties;
import com.carlettos.game.gameplay.piece.property.Property;
import com.carlettos.game.gameplay.piece.property.PropertyManager;

/**
 * Piece type, can be added or removed from a piece during game. They serve as a
 * way to add shared functionability to pieces, and works as a tag.
 *
 * @author Carlos
 */
public sealed interface IPieceType permits PieceType {

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
     * If a piece is impenetrable, cannot be transpased by abilities.
     */
    public static final IPieceType IMPENETRABLE = new Impenetrable();

    /**
     * An inmune piece cannot recieve abilities (even from allies).
     */
    public static final IPieceType IMMUNE = new Immune();

    /**
     * An heroic piece cannot be attacked (just taken).
     */
    public static final IPieceType HEROIC = new Heroic();

    /**
     * Demonic pieces gives one mana to its player when they are taken.
     */
    public static final IPieceType DEMONIC = new Demonic();

    /**
     * Needs to be taken multiple times. Any piece that uses this
     * {@link IPieceType}, needs to add the {@link Property}
     * {@link Properties#MAX_TAKEN_TIMES} to its {@link PropertyManager}.
     */
    public static final IPieceType TOUGH = new Tough();

    /**
     * A dead piece is a piece in control of a {@link Necromancer}.
     */
    public static final IPieceType DEAD = new Dead();

    /**
     * Its executed every time that a piece with this type tries to makes an action.
     * The other (and its pos) will be null on movement or in some abilities.
     *
     * @param data record with the information.
     */
    boolean can(PieceTypeData data);

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
    boolean canBe(PieceTypeData data);

    /**
     * Its executed every time that some other piece makes an action to a piece with
     * this type. The other (and its pos) will be null in some abilities.
     *
     * @param data record with the information.
     */
    void onBe(PieceTypeData data);
}
