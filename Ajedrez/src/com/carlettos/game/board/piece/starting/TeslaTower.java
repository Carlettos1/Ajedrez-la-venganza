package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.manager.clock.event.EventInfo;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.Pattern;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.starting.PatternCannonAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternStructureMove;
import com.carlettos.game.board.piece.pattern.starting.PatternMagicianMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class TeslaTower extends Piece implements IMove<PatternMagicianMove>, ITake<PatternStructureMove> {
    public final static Ability<TeslaTower, String, InfoNone> HABILIDAD_TORRE_TESLA = new HabilidadTorreTesla<>();
    protected final PatternMagicianMove patronMover;
    protected final PatternStructureMove patronComer;

    public TeslaTower(Color color) {
        super("Torre Tesla", "TT", HABILIDAD_TORRE_TESLA, color, PieceType.STRUCTURE);
        this.patronMover = new PatternMagicianMove() {};
        this.patronComer = new PatternStructureMove() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMover(tablero, inicio, info, patronMover);
            case TAKE -> this.canComer(tablero, inicio, info, patronComer);
            case ABILITY -> this.getAbility().canUse(tablero, this, inicio, info);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadTorreTesla<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone{
        protected final Pattern patronHabilidad = new PatternCannonAttack() {};
        
        public HabilidadTorreTesla() {
            super("PEM", 
                    "Emite un PEM que desactiva todas las estructuras", 
                    20, 
                    1, 
                    "ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            if(tablero instanceof Board board){
                board.getClock().addEvent(Event.create(EventInfo.of(board, 2, this.getNombre(), inicio), () -> {
                    board.getMatchingEscaques(patronHabilidad, inicio).stream()
                            .filter(escaque -> escaque.getPiece().isType(PieceType.STRUCTURE))
                            .forEach(escaque -> escaque.getPiece().changeCD(10)); //TODO: que desactive de verdad
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUse(tablero, pieza);
        }
    }
}
