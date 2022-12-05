package com.carlettos.game.util.enums;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlos
 */
public enum Action {
    MOVE(Color.CYAN), ATTACK(Color.ORANGE), TAKE(Color.RED), ABILITY(Color.MAGENTA);

    private final Color color;

    private Action(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean needsInfoPoint() {
        return this != ABILITY;
    }

    public void actuate(AbstractBoard board, Point pos, Info info) {
        Piece piece = board.getPiece(pos);
        switch (this) {
            case ATTACK -> board.remove(info.getPointOrSubPoint(), true);
            case MOVE -> {
                board.set(info.getPointOrSubPoint(), piece);
                board.remove(pos, false);
            }
            case TAKE -> {
                board.set(info.getPointOrSubPoint(), piece);
                board.remove(pos, true);
            }
            case ABILITY -> piece.getAbility().use(board, pos, info);
            default -> throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
