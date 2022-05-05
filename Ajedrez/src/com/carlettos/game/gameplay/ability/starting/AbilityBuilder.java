package com.carlettos.game.gameplay.ability.starting;

import java.util.function.Function;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.starting.Wall;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;

public class AbilityBuilder extends Ability {
    protected final Function<Color, Wall> creator = Wall::new;

    public AbilityBuilder() {
        super("builder", 10, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && info.isType(Direction.class));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(-1, -1);
        Point p3 = new Point(-1, -1);
        
        switch((Direction) info.getValue()){
            case N -> {
                p1 = start.add(1, 1);
                p2 = start.add(0, 1);
                p3 = start.add(-1, 1);
            }
            case S -> {
                p1 = start.add(1, -1);
                p2 = start.add(0, -1);
                p3 = start.add(-1, -1);
            }
            case E -> {
                p1 = start.add(1, 1);
                p2 = start.add(1, 0);
                p3 = start.add(1, -1);
            }
            case W -> {
                p1 = start.add(-1, 1);
                p2 = start.add(-1, 0);
                p3 = start.add(-1, -1);
            }
        }
        if(!board.isOutOfBorder(p1)){
            board.getEscaque(p1).setPieceIfEmpty(creator.apply(piece.getColor()));
        }
        if(!board.isOutOfBorder(p2)){
            board.getEscaque(p2).setPieceIfEmpty(creator.apply(piece.getColor()));
        }
        if(!board.isOutOfBorder(p3)){
            board.getEscaque(p3).setPieceIfEmpty(creator.apply(piece.getColor()));
        }
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        return Direction.values();
    }
}
