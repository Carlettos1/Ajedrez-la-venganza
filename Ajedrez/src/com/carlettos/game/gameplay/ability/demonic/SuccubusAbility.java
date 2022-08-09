package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class SuccubusAbility extends Ability {
    public SuccubusAbility(String name, int cooldown, int manaCost) {
        super(name, cooldown, manaCost);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return this.commonCanUse(board, piece) && info.isType(Point.class) && board.getEscaque((Point)info.getValue()).hasPiece();
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        board.getPiece((Point)info.getValue()).getEffectManager().addEffect(new DeactivateEffect(6));
        this.commonUse(board, piece);
    }

    @Override
    public Point[] getValues(AbstractSquareBoard board, Point start) {
        //TODO: externalize the pattern
        return board.getMatchingEscaques((board1, start1, end) -> true, start).stream().filter(e -> e.hasPiece()).map(e -> e.getPos()).toArray(Point[]::new);
    }
}
