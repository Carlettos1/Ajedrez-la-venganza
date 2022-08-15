package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;

/**
 * It's the ability of the piece. It has all the information about itself. Also
 * knows when and how excecute itself.
 *
 * @author Carlos
 *
 * @see Piece
 */
public abstract class Ability {

    protected final AbilityData data;

    /**
     * General constructor.
     *
     * @param name     name of the piece of the ability.
     * @param cooldown cooldown of the ability.
     * @param manaCost cost of mana of the ability.
     *
     * @see Piece
     */
    protected Ability(String name, int cooldown, int manaCost) {
        data = new AbilityData(name, cooldown, manaCost);
    }

    /**
     * Checks whenever the ability can be used or not.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info  information to use the ability
     * @return PASS if can be used, FAIL other case.
     */
    public abstract boolean canUse(AbstractBoard board, Piece piece, Point start, Info info);

    /**
     * Excecutes the ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info  information to use the ability
     */
    public abstract void use(AbstractBoard board, Piece piece, Point start, Info info);

    /**
     * Utility method, checks the cd and the mana acording to the info of this
     * ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @return true if can be used, false other case.
     */
    public boolean commonCanUse(AbstractBoard board, Piece piece) {
        boolean nomana = piece.getCD() <= 0 && !piece.isMoved();
        return nomana && board.getClock().turnOf().getMana() >= this.data.manaCost();
    }

    /**
     * Utility method, adds the cd and removes the mana acording to the info of this
     * ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     */
    public void commonUse(AbstractBoard board, Piece piece) {
        piece.setIsMoved(true);
        piece.changeCD(this.data.cooldown());
        board.getClock().turnOf().changeMana(-this.data.manaCost());
    }

    public abstract IInfo[] getValues(AbstractBoard board, Point start);

    public String formatInfo(Object info) {
        if (info instanceof Point p) {
            return "%s, %s".formatted(p.x, p.y);
        } else if (info instanceof Tuple<?, ?> t) {
            return "%s, %s".formatted(t.x, t.y);
        } else {
            return info.toString();
        }
    }
}
