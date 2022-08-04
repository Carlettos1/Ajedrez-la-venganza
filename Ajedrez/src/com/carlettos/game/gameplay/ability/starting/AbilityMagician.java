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
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.helper.CardHelper;

public class AbilityMagician extends AbilityNoInfo {
    public static final Pattern ACTION_PATTER = Patterns.CANNON_ATTACK_PATTERN;

    public AbilityMagician() {
        super("magician", 6, 2);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.fromBoolean(CardHelper.boardHasCards(board, CardsOnBoard.ICE, CardsOnBoard.FIRE));
    }

    // TODO: ampliar el sistema y poder quitar efectos
    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        var hasIce = CardHelper.boardHasCard(board, CardsOnBoard.ICE);
        var hasFire = CardHelper.boardHasCard(board, CardsOnBoard.FIRE);
        var pieces = board.getMatchingEscaques(ACTION_PATTER, start);
        pieces.removeIf(e -> !e.hasPiece());
        pieces.removeIf(e -> e.getPieceColor() == piece.getColor());

        if (hasIce) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new IceEffect()));
        }
        if (hasFire) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new FireEffect()));
        }

        commonUse(board, piece);
    }
}
