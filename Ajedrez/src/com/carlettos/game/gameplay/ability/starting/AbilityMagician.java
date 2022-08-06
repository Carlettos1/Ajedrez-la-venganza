package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.FireEffect;
import com.carlettos.game.gameplay.effect.IceEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class AbilityMagician extends AbilityNoInfo {
    public static final Pattern ACTION_PATTERN = Patterns.ARCHER_MOVE_PATTERN;

    public AbilityMagician() {
        super("magician", 6, 2);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return board.getClock().boardContainsAny(CardsOnBoard.ICE, CardsOnBoard.FIRE);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        var hasIce = board.getClock().boardContains(CardsOnBoard.ICE);
        var hasFire = board.getClock().boardContains(CardsOnBoard.FIRE);
        var pieces = board.getMatchingEscaques(ACTION_PATTERN, start);
        pieces.removeIf(e -> !e.hasPiece());
        pieces.removeIf(e -> e.getPieceColor() == piece.getColor());

        if (hasIce) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new IceEffect(5)));
        }
        if (hasFire) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new FireEffect(11)));
        }

        commonUse(board, piece);
    }
}
