package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class TeslaTower extends Piece implements IMove<PatternMagicianMove>, ITake<PatternStructureMove> {
    public static final Ability<TeslaTower, String, InfoNone> ABILITY_TESLA_TOWER = new AbilityTeslaTower<>();
    protected final PatternMagicianMove movePattern;
    protected final PatternStructureMove takePattern;

    public TeslaTower(Color color) {
        super("tesla_tower", "tt", ABILITY_TESLA_TOWER, color, PieceType.STRUCTURE);
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
    
    @Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
    
    @Override
	public int hashCode() {
		return super.hashCode();
	}
    
    public static class AbilityTeslaTower<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        protected final Pattern abilityPattern = new PatternCannonAttack() {};
        
        public AbilityTeslaTower() {
            super("PEM", 
                    "Emite un PEM que desactiva todas las estructuras", 
                    20, 
                    1);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof Board);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            if(board instanceof Board b){
            	//TODO: que desactive de verdad
                b.getClock().addEvent(Event.create(EventInfo.of(b, 2, this.data.name(), start), () -> 
                    b.getMatchingEscaques(abilityPattern, start).stream()
                            .filter(escaque -> escaque.getPiece().isType(PieceType.STRUCTURE))
                            .forEach(escaque -> escaque.getPiece().changeCD(10))));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUse(board, piece);
        }
    }
}
