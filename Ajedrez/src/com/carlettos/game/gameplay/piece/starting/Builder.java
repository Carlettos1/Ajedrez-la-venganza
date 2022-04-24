package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityDirection;
import com.carlettos.game.gameplay.ability.info.InfoDirection;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.pattern.starting.PatternLeechTake;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.PieceType;
import java.util.function.Function;

/**
 *
 * @author Carlettos
 */
public class Builder extends Piece implements IMove<PatternMagicianMove>, ITake<PatternLeechTake> {
    public static final Ability<Builder, Direction, InfoDirection> ABILITY_BUILDER = new AbilityBuilder<>();
    protected final PatternMagicianMove movePattern;
    protected final PatternLeechTake takePattern;

    public Builder(Color color) {
        super("builder", "CO", ABILITY_BUILDER, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        this.movePattern = new PatternMagicianMove() {};
        this.takePattern = new PatternLeechTake() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class AbilityBuilder<P extends Piece> extends Ability<P, Direction, InfoDirection> implements AbilityDirection {
        protected final Function<Color, Wall> creator = Wall::new;

        public AbilityBuilder() {
            super("Construir Barrera", 
                    "Construye 3 muros en la dirección indicada. No reemplaza piezas.", 
                    10, 
                    0, 
                    "Dirección (NESW).");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoDirection info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece));
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoDirection info) {
            Point p1 = new Point(-1, -1);
            Point p2 = new Point(-1, -1);
            Point p3 = new Point(-1, -1);
            
            switch(info.getValue()){
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
    }
}
