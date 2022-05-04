package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.PieceType;

public class AbilityTeslaTower extends AbilityNoInfo {
    protected final Pattern abilityPattern = new PatternCannonAttack() {};
    
    public AbilityTeslaTower() {
        super("PEM", 
                "Emite un PEM que desactiva todas las estructuras", 
                20, 
                1);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof Board);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
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
