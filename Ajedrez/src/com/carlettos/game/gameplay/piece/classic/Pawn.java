package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityPiece;
import com.carlettos.game.gameplay.ability.info.InfoPiece;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnMove;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.starting.Archer;
import com.carlettos.game.gameplay.piece.starting.Ballista;
import com.carlettos.game.gameplay.piece.starting.Builder;
import com.carlettos.game.gameplay.piece.starting.Cannon;
import com.carlettos.game.gameplay.piece.starting.Catapult;
import com.carlettos.game.gameplay.piece.starting.CrazyPawn;
import com.carlettos.game.gameplay.piece.starting.Ram;
import com.carlettos.game.gameplay.piece.starting.ShieldBearer;
import com.carlettos.game.gameplay.piece.starting.Ship;
import com.carlettos.game.gameplay.piece.starting.SuperPawn;
import com.carlettos.game.gameplay.piece.starting.TeslaTower;
import com.carlettos.game.gameplay.piece.starting.Warlock;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public class Pawn extends AbstractPawn<PatternPawnMove, PatternPawnTake> {

    public static final Ability<Pawn, Piece, InfoPiece> ABILITY_PAWN = new AbilityPawn<>();

    public Pawn(Color color) {
        super(()->color, ()->color, "pawn", "p", ABILITY_PAWN, color);
    }

    public static class AbilityPawn<P extends Piece> extends Ability<P, Piece, InfoPiece> implements AbilityPiece {

        public AbilityPawn() {
            super("Coronar",
                    "Al estar en la última fila, puede transformarse en cualquier pieza de las que se permita.",
                    0,
                    0);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoPiece info) {
            if(!this.commonCanUse(board, piece)){
                return ActionResult.FAIL;
            }
            if (piece.getColor().equals(Color.WHITE)) {
                if (start.y + 1 == board.rows) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            } else if (piece.getColor().equals(Color.BLACK)) {
                if (start.y == 0) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            }
            System.err.println("INTENTANDO CORONAR CON OTRO COLOR");
            return ActionResult.FAIL;
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoPiece info) {
            Piece p = info.getValue();
            p.setColor(piece.getColor());
            board.getEscaque(start).setPiece(p);
            p.setIsMoved(true);
        }

        @Override
        public Piece[] getPossibleValues(AbstractBoard board, Point start) {
            return new Piece[]{new Bishop(Color.GRAY),
                new Knight(Color.GRAY),
                new Queen(Color.GRAY),
                new Rook(Color.GRAY),
                new Ram(Color.GRAY),
                new Archer(Color.GRAY),
                new Ballista(Color.GRAY),
                new Warlock(Color.GRAY),
                new Catapult(Color.GRAY),
                new Cannon(Color.GRAY),
                new Builder(Color.GRAY),
                new ShieldBearer(Color.GRAY),
                new Ship(Color.GRAY),
                new CrazyPawn(Color.GRAY),
                new SuperPawn(Color.GRAY),
                new TeslaTower(Color.GRAY)};
        }
    }
}
