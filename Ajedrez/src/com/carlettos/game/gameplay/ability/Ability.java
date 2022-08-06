package com.carlettos.game.gameplay.ability;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;

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
     * @param name      name of the piece of the ability.
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
    public abstract boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info);

    /**
     * Excecutes the ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @param start position of the piece.
     * @param info  information to use the ability
     */
    public abstract void use(AbstractSquareBoard board, Piece piece, Point start, Info info);

    /**
     * Utility method, checks the cd and the mana acording to the info of this
     * ability.
     *
     * @param board board in which the ability is happening.
     * @param piece piece that is using the ability.
     * @return true if can be used, false other case.
     */
    public boolean commonCanUse(AbstractSquareBoard board, Piece piece) {
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
    public void commonUse(AbstractSquareBoard board, Piece piece) {
        piece.setIsMoved(true);
        piece.changeCD(this.data.cooldown());
        board.getClock().turnOf().changeMana(-this.data.manaCost());
    }

    public abstract IInfo[] getValues(AbstractSquareBoard board, Point start);

    /**
     * Returns an oob point in case that there's no end point.
     */
    public Point getEndPointJump(AbstractSquareBoard board, Point start, Direction dir, int range) {
        var ray = board.rayCast(start, dir, range);
        ray = ray.stream().filter(e -> !e.hasPiece()).toList();
        if (ray.isEmpty()) { return new Point(-1, -1); }
        return ray.get(ray.size() - 1).getPos();
    }
    
    /**
     * Returns an oob point in case that there's no end point
     */
    public Point getEndPointNoJump(AbstractSquareBoard board, Point start, Direction dir, int range) {
        List<Escaque> ray = board.rayCast(start, dir, range, false);
        List<Escaque> valids = new ArrayList<>(ray.size());
        for (Escaque r : ray) {
            if (r.hasPiece()) {
                break;
            }
            valids.add(r);
        }
        if (valids.isEmpty()) {
            return new Point(-1, -1);
        }
        return valids.get(valids.size() - 1).getPos();
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
