package com.carlettos.game.gameplay.ability;

import java.util.ArrayList;
import java.util.List;

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
public abstract class Ability<T extends IInfo> {

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
     * Excecutes the ability.
     *
     * @param board board in which the ability is happening.
     * @param start position of the piece.
     * @param info  information to use the ability
     */
    public abstract void use(AbstractBoard board, Point start, Info info);

    /**
     * Checks whenever the ability can be used or not.
     *
     * @param board board in which the ability is happening.
     * @param start position of the piece.
     * @param info  information to use the ability
     * @return PASS if can be used, FAIL other case.
     */
    @SuppressWarnings("unchecked")
    public final boolean canUse(AbstractBoard board, Point start, Info info) {
        return this.commonCanUse(board, board.getPiece(start)) && this.checkTypes(info)
                && this.reducedCanUse(board, start, (T) info.getValue());
    }

    /**
     * Utility method, checks the cd and the mana acording to the info of this
     * ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @return true if can be used, false other case.
     */
    public final boolean commonCanUse(AbstractBoard board, Piece piece) {
        boolean nomana = piece.getCD() <= 0 && !piece.isMoved();
        return nomana && board.getClock().turnOf().getMana() >= this.data.manaCost();
    }

    public abstract boolean checkTypes(Info info);

    /**
     * Upgraded and separated version of canUse, it assumes that the info given is
     * ready to use.
     */
    public abstract boolean reducedCanUse(AbstractBoard board, Point start, T info);

    /**
     * Utility method, adds the cd and removes the mana acording to the info of this
     * ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     */
    public final void commonUse(AbstractBoard board, Point start) {
        board.getPiece(start).setIsMoved(true);
        board.getPiece(start).changeCD(this.data.cooldown());
        board.getClock().turnOf().changeMana(-this.data.manaCost());
    }

    /**
     * Gets all the infos that this ability can possibly have.
     */
    public abstract List<T> getInfos(AbstractBoard board);

    /**
     * Gets all the values of info that the piece in the given point can do.
     */
    public final List<T> getValues(AbstractBoard board, Point start) {
        List<T> all = this.getInfos(board);
        ArrayList<T> approved = new ArrayList<>(all.size());
        all.forEach(t -> {
            if (this.reducedCanUse(board, start, t))
                approved.add(t);
        });
        approved.trimToSize();
        return approved;
    }

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
