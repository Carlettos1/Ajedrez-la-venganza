package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.piece.pattern.starting.PatternMagicianMove;
import com.carlettos.game.board.piece.pattern.starting.PatternLeechTake;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoDirection;
import java.util.function.Function;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityDirection;

/**
 *
 * @author Carlettos
 */
public class Builder extends Piece implements IMove<PatternMagicianMove>, ITake<PatternLeechTake> {
    public static final Ability<Builder, Direction, InfoDirection> HABILIDAD_CONSTRUCTOR = new HabilidadConstructor<>();
    protected final PatternMagicianMove patronMover;
    protected final PatternLeechTake patronComer;

    public Builder(Color color) {
        super("Constructor", "CO", HABILIDAD_CONSTRUCTOR, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        this.patronMover = new PatternMagicianMove() {};
        this.patronComer = new PatternLeechTake() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMove(tablero, inicio, info, patronMover);
            case TAKE -> this.canTake(tablero, inicio, info, patronComer);
            case ABILITY -> this.getAbility().canUse(tablero, this, inicio, info);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadConstructor<P extends Piece> extends Ability<P, Direction, InfoDirection> implements AbilityDirection{
        protected final Function<Color, Wall> creator = Wall::new;

        public HabilidadConstructor() {
            super("Construir Barrera", 
                    "Construye 3 muros en la dirección indicada. No reemplaza piezas.", 
                    10, 
                    0, 
                    "Dirección (NESW).");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoDirection info) {
            return ActionResult.fromBoolean(this.commonCanUse(tablero, pieza));
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoDirection info) {
            Point p1 = new Point(-1, -1);
            Point p2 = new Point(-1, -1);
            Point p3 = new Point(-1, -1);
            
            switch(info.getValue()){
                case N -> {
                    p1 = inicio.add(1, 1);
                    p2 = inicio.add(0, 1);
                    p3 = inicio.add(-1, 1);
                }
                case S -> {
                    p1 = inicio.add(1, -1);
                    p2 = inicio.add(0, -1);
                    p3 = inicio.add(-1, -1);
                }
                case E -> {
                    p1 = inicio.add(1, 1);
                    p2 = inicio.add(1, 0);
                    p3 = inicio.add(1, -1);
                }
                case W -> {
                    p1 = inicio.add(-1, 1);
                    p2 = inicio.add(-1, 0);
                    p3 = inicio.add(-1, -1);
                }
            }
            if(!tablero.isOutOfBorder(p1)){
                tablero.getEscaque(p1).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            if(!tablero.isOutOfBorder(p2)){
                tablero.getEscaque(p2).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            if(!tablero.isOutOfBorder(p3)){
                tablero.getEscaque(p3).setPiezaIfEmpty(creator.apply(pieza.getColor()));
            }
            this.commonUse(tablero, pieza);
        }
    }
}
