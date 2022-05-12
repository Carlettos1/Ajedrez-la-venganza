package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.CardOnBoard;
import com.carlettos.game.gameplay.card.upgrade.Fire;
import com.carlettos.game.gameplay.card.upgrade.Ice;
import com.carlettos.game.gameplay.effect.FireEffect;
import com.carlettos.game.gameplay.effect.IceEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityMagician extends AbilityNoInfo {
    private static final Pattern action = new PatternCannonAttack() {};
    private static final CardOnBoard ice = new Ice();
    private static final CardOnBoard fire = new Fire();

    public AbilityMagician() {
        super("magician", 6, 2);
    } 

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (board instanceof Board b) {
            var hasIce = b.getClock().boardContainsCard(ice);
            var hasFire = b.getClock().boardContainsCard(fire);
            return ActionResult.fromBoolean(hasIce || hasFire);
        }
        return ActionResult.FAIL;
    }

    //todo: ampliar el sistema y poder quitar efectos
    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var b = (Board) board;
        var hasIce = b.getClock().boardContainsCard(ice);
        var hasFire = b.getClock().boardContainsCard(fire);
        var pieces = b.getMatchingEscaques(action, start);
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
