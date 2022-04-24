package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class Knight extends SimplePiece<PatternKnight> {

    public static final Ability<Knight, String, InfoNone> ABILITY_KNIGHT = new AbilityKnight<>();

    public Knight(Color color) {
        super("knight", "n", ABILITY_KNIGHT, color, new PatternKnight(){}, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }

    public static class AbilityKnight<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {

        public AbilityKnight() {
            super("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            if (!this.commonCanUse(board, piece)) {
                return ActionResult.FAIL;
            }

            Point p1 = new Point(start.x + 1, start.y);
            Point p2 = new Point(start.x - 1, start.y);

            if (board.getEscaque(p1).hasPiece() || board.getEscaque(p2).hasPiece()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            Point p1 = new Point(start.x + 1, start.y);
            Point p2 = new Point(start.x - 1, start.y);
            board.getEscaque(p1).setPiece(new Pawn(piece.getColor()));
            board.getEscaque(p2).setPiece(new Pawn(piece.getColor()));
            this.commonUse(board, piece);
        }
    }
}
