package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.PieceType;

public class AbilityTeslaTower extends AbilityNoInfo {
    protected final Pattern abilityPattern = Patterns.CANNON_ATTACK_PATTERN;

    public AbilityTeslaTower() {
        super("tesla_tower", 20, 1);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof SquareBoard);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var b = (SquareBoard) board;
        b.getClock()
                .addEvent(Event.create(EventInfo.of(b, 2, this.data.getName(), start),
                        () -> b.getMatchingEscaques(abilityPattern, start).stream()
                                .filter(escaque -> escaque.getPiece().isType(PieceType.STRUCTURE))
                                .filter(escaque -> !escaque.getPieceColor().equals(piece.getColor()))
                                .forEach(escaque -> escaque.getPiece().addEffect(new DeactivateEffect(6)))));
        this.commonUse(board, piece);
    }
}
