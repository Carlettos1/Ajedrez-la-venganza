package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternKnight;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

public class Knight extends SimplePiece<PatternKnight> {

    public static final Ability<Knight, String, InfoNone> ABILITY_KNIGHT = new AbilityKnight<>();

    public Knight(Color color) {
        super("Caballo", "C", ABILITY_KNIGHT, color, new PatternKnight(){}, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }

    public static class AbilityKnight<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {

        public AbilityKnight() {
            super("Bajar Jinetes",
            "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
            10,
            1,
            "No requiere información adicional");
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
