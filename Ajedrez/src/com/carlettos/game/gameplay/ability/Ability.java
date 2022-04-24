package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

/**
 * It's the ability of the piece. It has all the information about itself. Also
 * knows when and how excecute itself.
 *
 * @author Carlos
 *
 * @param <P> piece of which this ability is directed (can be the Piece class).
 * @param <V> value to treat as information.
 * @param <I> information to pass as argument.
 *
 * @see Piece
 */
public abstract non-sealed class Ability<P extends Piece, V, I extends Info<V>> implements InfoUse<V>{

    protected final Data data;

    /**
     * General constructor.
     *
     * @param name name of the ability.
     * @param description description of the ability.
     * @param cooldown cooldown of the ability.
     * @param manaCost cost of mana of the ability.
     *
     * @see Piece
     */
    public Ability(String name, String description, int cooldown, int manaCost) {
        data = new Data(name, description, cooldown, manaCost);
    }

    /**
     * Checks whenever the ability can be used or not.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info information to use the ability
     * @return PASS if can be used, FAIL other case.
     */
    public abstract ActionResult canUse(AbstractBoard board, P piece, Point start, I info);

    /**
     * Excecutes the ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info information to use the ability
     */
    public abstract void use(AbstractBoard board, P piece, Point start, I info);
    
    /**
     * Utility method, checks the cd and the mana acording to the info of
     * this ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @return true if can be used, false other case.
     */
    public boolean commonCanUse(AbstractBoard board, Piece piece){
        boolean nomana = piece.getCD() <= 0 && !piece.isMoved();
        if(board instanceof Board t){
            return nomana && t.getClock().turnOf().getMana() >= this.data.manaCost();
        }
        return nomana;
    }
    
    /**
     * Utility method, adds the cd and removes the mana acording to the info of
     * this ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     */
    public void commonUse(AbstractBoard board, Piece piece){
        piece.setIsMoved(true);
        piece.changeCD(this.data.cooldown());
        if (board instanceof Board t) {
            t.getClock().turnOf().changeMana(-this.data.manaCost());
        }
    }
}
