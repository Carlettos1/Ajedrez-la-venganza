package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.CardOnBoard;
import com.carlettos.game.gameplay.card.upgrade.Fire;
import com.carlettos.game.gameplay.card.upgrade.Ice;
import com.carlettos.game.gameplay.effect.FireEffect;
import com.carlettos.game.gameplay.effect.IceEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityMagician extends AbilityNoInfo {
    private static final Pattern action = Patterns.CANNON_ATTACK_PATTERN;
    private static final CardOnBoard ice = new Ice();
    private static final CardOnBoard fire = new Fire();

    public AbilityMagician() {
        super("magician", 6, 2);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var hasIce = board.getClock().boardContainsCard(ice);
        var hasFire = board.getClock().boardContainsCard(fire);
        return ActionResult.fromBoolean(hasIce || hasFire);
    }

    // TODO: ampliar el sistema y poder quitar efectos
    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var hasIce = board.getClock().boardContainsCard(ice);
        var hasFire = board.getClock().boardContainsCard(fire);
        var pieces = board.getMatchingEscaques(action, start);
        pieces.removeIf(e -> !e.hasPiece());
        pieces.removeIf(e -> e.getPieceColor() == piece.getColor());

        if (hasIce) {
            pieces.forEach(e -> e.getPiece().addEffect(new IceEffect()));
        }
        if (hasFire) {
            pieces.forEach(e -> e.getPiece().addEffect(new FireEffect()));
        }

        commonUse(board, piece);
    }
}
