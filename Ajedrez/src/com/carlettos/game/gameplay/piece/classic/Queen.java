package com.carlettos.game.gameplay.piece.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityPoint;
import com.carlettos.game.gameplay.ability.info.InfoPoint;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.pattern.classic.PatternQueen;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class Queen extends SimplePiece<PatternQueen> {

    public static final Ability<Queen, Point, InfoPoint> ABILITY_QUEEN = new AbilityQueen<>();

    public Queen(Color color) {
        super("queen", "q", ABILITY_QUEEN, color, new PatternQueen(){}, PieceType.BIOLOGIC, PieceType.HEROIC);
    }

    public static class AbilityQueen<P extends Piece> extends Ability<P, Point, InfoPoint> implements AbilityPoint {

        public AbilityQueen() {
            super("Movimiento Caballístico.",
                    "Permite a la reina moverse como caballo, comiendo cualquier pieza en la que caiga, incluida piezas aliadas.",
                    5,
                    0);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, Piece piece, Point start, InfoPoint info) {
            if (!this.commonCanUse(board, piece)) {
                return ActionResult.FAIL;
            }

            if(!new PatternKnight() {}.match(board, start, info.getValue())){
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void use(AbstractBoard board, Piece piece, Point start, InfoPoint info) {
            board.getEscaque(info.getValue()).setPiece(piece);
            board.removePiece(start);
            this.commonUse(board, piece);
        }

        @Override
        public Point[] getPossibleValues(AbstractBoard board, Point start) {
            List<Point> allValues = new ArrayList<>(8);
            List<Point> values = new ArrayList<>(8);
            allValues.add(new Point(-2, -1));
            allValues.add(new Point(-2, 1));
            allValues.add(new Point(2, -1));
            allValues.add(new Point(2, 1));
            allValues.add(new Point(-1, -2));
            allValues.add(new Point(-1, 2));
            allValues.add(new Point(1, -2));
            allValues.add(new Point(1, 2));
            for (Point v : allValues) {
                if(!board.isOutOfBorder(start.add(v))){
                    values.add(v);
                }
            }
            return values.toArray(Point[]::new);
        }
    }
}
