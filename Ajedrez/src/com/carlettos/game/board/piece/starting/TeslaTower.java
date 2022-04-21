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
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class TeslaTower extends Piece implements IMove<PatternMagicianMove>, ITake<PatternStructureMove> {
    public final static Ability<TeslaTower, String, InfoNone> ABILITY_TESLA_TOWER = new AbilityTeslaTower<>();
    protected final PatternMagicianMove movePattern;
    protected final PatternStructureMove takePattern;

    public TeslaTower(Color color) {
        super("Torre Tesla", "TT", ABILITY_TESLA_TOWER, color, PieceType.STRUCTURE);
        this.movePattern = new PatternMagicianMove() {};
        this.takePattern = new PatternStructureMove() {};
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
    
    public static class AbilityTeslaTower<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        protected final Pattern abilityPattern = new PatternCannonAttack() {};
        
        public AbilityTeslaTower() {
            super("PEM", 
                    "Emite un PEM que desactiva todas las estructuras", 
                    20, 
                    1, 
                    "ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof Board);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            if(board instanceof Board b){
                b.getClock().addEvent(Event.create(EventInfo.of(b, 2, this.data.name(), start), () -> {
                    b.getMatchingEscaques(abilityPattern, start).stream()
                            .filter(escaque -> escaque.getPiece().isType(PieceType.STRUCTURE))
                            .forEach(escaque -> escaque.getPiece().changeCD(10)); //TODO: que desactive de verdad
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUse(board, piece);
        }
    }
}
