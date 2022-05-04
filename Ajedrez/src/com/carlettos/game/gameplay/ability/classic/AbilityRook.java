package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;

public class AbilityRook extends Ability {

    public AbilityRook() {
        super("Muro de Berlín",
                "\"Lanza\" todas las torres contiguas en una dirección y se detienen "
                + "si y solo si se cumple alguna de las siguientes condicioens: "
                + "1.- Alcanza el borde del tablero"
                + "2.- Comen una pieza enemiga"
                + "3.- Colisionan con una pieza aliada.",
                10,
                0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(board instanceof Board && info.isType(Direction.class));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var b = (Board) board;
        var dir = (Direction) info.getValue();
        
        List<Escaque> escaquesTorres = new ArrayList<>(getNearbyRookEscaques(board, piece, start));
        escaquesTorres.add(board.getEscaque(start));

        boolean seHaEncontradoNuevaTorre = true;
        while (seHaEncontradoNuevaTorre) {
            List<Escaque> tmp = new ArrayList<>();
            seHaEncontradoNuevaTorre = false;
            for (Escaque escaqueTorre : escaquesTorres) {
                for (Escaque escaqueTorreAdyacente : getNearbyRookEscaques(board, piece, escaqueTorre.getPos())) {
                    if (!(escaquesTorres.contains(escaqueTorreAdyacente) || tmp.contains(escaqueTorreAdyacente))) {
                        tmp.add(escaqueTorreAdyacente);
                        seHaEncontradoNuevaTorre = true;
                    }
                }
            }
            if (!tmp.isEmpty()) {
                escaquesTorres.addAll(tmp);
            }
        }

        escaquesTorres = AbilityRook.this.orderEscaques(escaquesTorres, board, dir);

        switch (dir) {
            case S -> escaquesTorres.forEach((escaqueTorre) -> {
                    escaqueTorre.getPiece().setIsMoved(false); //por conveniencia
                    for (int y = 0; y < board.rows; y++) {
                        if (y == escaqueTorre.getPos().y) {
                            break;
                        }
                        Point puntoFinal = new Point(escaqueTorre.getPos().x, y);
                        if (b.tryTo(Action.TAKE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        } else if (b.tryTo(Action.MOVE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        }
                    }
                });
            case E -> escaquesTorres.forEach((escaqueTorre) -> {
                    escaqueTorre.getPiece().setIsMoved(false); //por conveniencia
                    for (int x = board.columns - 1; x >= 0; x--) {
                        if (x == escaqueTorre.getPos().x) {
                            break;
                        }
                        Point puntoFinal = new Point(x, escaqueTorre.getPos().y);
                        if (b.tryTo(Action.TAKE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        } else if (b.tryTo(Action.MOVE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        }
                    }
                });
            case N -> escaquesTorres.forEach((escaqueTorre) -> {
                    escaqueTorre.getPiece().setIsMoved(false); //por conveniencia
                    for (int y = board.rows - 1; y >= 0; y--) {
                        if (y == escaqueTorre.getPos().y) {
                            break;
                        }
                        Point puntoFinal = new Point(escaqueTorre.getPos().x, y);
                        if (b.tryTo(Action.TAKE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        } else if (b.tryTo(Action.MOVE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        }
                    }
                });
            case W -> escaquesTorres.forEach((escaqueTorre) -> {
                    escaqueTorre.getPiece().setIsMoved(false); //por conveniencia
                    for (int x = 0; x < board.columns; x++) {
                        if (x == escaqueTorre.getPos().y) {
                            break;
                        }
                        Point puntoFinal = new Point(x, escaqueTorre.getPos().y);
                        if (b.tryTo(Action.TAKE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        } else if (b.tryTo(Action.MOVE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive()) {
                            break;
                        }
                    }
                });
        }
        for (Escaque escaque : escaquesTorres) {
            this.commonUse(board, escaque.getPiece());
        }
    }

    protected List<Escaque> getNearbyRookEscaques(AbstractBoard board, Piece piece, Point start) {
        return Arrays.<Escaque>asList(board.getNearbyEscaques(board.getEscaque(start)).stream().filter((escaque) -> {
            return escaque.getPiece() instanceof Rook && piece.getColor().equals(escaque.getPiece().getColor());
        }).toArray(Escaque[]::new));
    }

    protected List<Escaque> orderEscaques(List<Escaque> rooks, AbstractBoard board, Direction direction) {
        List<Escaque> ordered = new ArrayList<>();
        switch (direction) {
            case S -> {for (int y = 0; y < board.rows; y++) {
                    for (int x = 0; x < board.columns; x++) {
                        if (rooks.contains(board.getEscaque(x, y))) {
                            ordered.add(board.getEscaque(x, y));
                        }
                    }
                }}
            case E -> {for (int x = board.columns - 1; x >= 0; x--) {
                    for (int y = 0; y < board.rows; y++) {
                        if (rooks.contains(board.getEscaque(x, y))) {
                            ordered.add(board.getEscaque(x, y));
                        }
                    }
                }}
            case N ->{for (int y = board.rows - 1; y >= 0; y--) {
                    for (int x = 0; x < board.columns; x++) {
                        if (rooks.contains(board.getEscaque(x, y))) {
                            ordered.add(board.getEscaque(x, y));
                        }
                    }
                }}
            case W ->{for (int x = 0; x < board.columns; x++) {
                    for (int y = 0; y < board.rows; y++) {
                        if (rooks.contains(board.getEscaque(x, y))) {
                            ordered.add(board.getEscaque(x, y));
                        }
                    }
                }}
        }
        return ordered;
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        return Direction.values();
    }
}
