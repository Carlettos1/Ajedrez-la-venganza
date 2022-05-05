package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.ActionResult;

/**
 * It's the ability of the piece. It has all the information about itself. Also
 * knows when and how excecute itself.
 *
 * @author Carlos
 *
 * @see Piece
 */
public abstract class Ability {

    protected final Data data;

    /**
     * General constructor.
     *
     * @param key name of the piece of the ability.
     * @param cooldown cooldown of the ability.
     * @param manaCost cost of mana of the ability.
     *
     * @see Piece
     */
    protected Ability(String key, int cooldown, int manaCost) {
        data = new Data(new ResourceLocation("ability.name." + key), new ResourceLocation("ability.description." + key), cooldown, manaCost);
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
    public abstract ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info);

    /**
     * Excecutes the ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info information to use the ability
     */
    public abstract void use(AbstractBoard board, Piece piece, Point start, Info info);
    
    /**
     * Utility method, checks the cd and the mana acording to the info of
     * this ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @return true if can be used, false other case.
     */
    public boolean commonCanUse(AbstractBoard board, Piece piece) {
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
    public void commonUse(AbstractBoard board, Piece piece) {
        piece.setIsMoved(true);
        piece.changeCD(this.data.cooldown());
        if (board instanceof Board t) {
            t.getClock().turnOf().changeMana(-this.data.manaCost());
        }
    }

    public abstract Object[] getValues(AbstractBoard board, Point start);
    
    public String formatInfo(Object info) {
    	return switch (info) {
	    	case Point p -> "%s, %s".formatted(p.x, p.y);
	    	case Tuple<?, ?> t -> "%s, %s".formatted(t.x, t.y);
	    	default -> info.toString();
    	};
    }
}
