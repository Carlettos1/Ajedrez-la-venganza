package com.carlettos.game.board.piece.classic;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.AbstractPawn;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.starting.Ram;
import com.carlettos.game.board.piece.starting.Archer;
import com.carlettos.game.board.piece.starting.Ballista;
import com.carlettos.game.board.piece.starting.Warlock;
import com.carlettos.game.board.piece.starting.Catapult;
import com.carlettos.game.board.piece.starting.Cannon;
import com.carlettos.game.board.piece.starting.Builder;
import com.carlettos.game.board.piece.starting.ShieldBearer;
import com.carlettos.game.board.piece.starting.Ship;
import com.carlettos.game.board.piece.starting.MadPawn;
import com.carlettos.game.board.piece.starting.SuperPawn;
import com.carlettos.game.board.piece.starting.TeslaTower;
import com.carlettos.game.board.piece.pattern.classic.PatternPawnTake;
import com.carlettos.game.board.piece.pattern.classic.PatternPawnMove;
import com.carlettos.game.board.property.ability.InfoPiece;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityPiece;

/**
 *
 * @author Carlos
 */
public class Pawn extends AbstractPawn<PatternPawnMove, PatternPawnTake> {

    public static final Ability<Pawn, Piece, InfoPiece> ABILITY_PAWN = new AbilityPawn<>();

    public Pawn(Color color) {
        super(()->color, ()->color, "Peón", "P", ABILITY_PAWN, color);
    }

    public static class AbilityPawn<P extends Piece> extends Ability<P, Piece, InfoPiece> implements AbilityPiece {

        public AbilityPawn() {
            super("Coronar",
                    "Al estar en la última fila, puede transformarse en cualquier pieza de las que se permita.",
                    0,
                    0,
                    "El nombre de la pieza a transformar. Ejemplo: Dama.");
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
                new MadPawn(Color.GRAY),
                new SuperPawn(Color.GRAY),
                new TeslaTower(Color.GRAY)};
        }
    }
}
