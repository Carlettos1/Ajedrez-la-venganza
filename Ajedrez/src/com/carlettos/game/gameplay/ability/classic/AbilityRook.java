package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.LogManager;

public class AbilityRook extends Ability {

    public AbilityRook() {
        super("rook", 10, 0);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) { return false; }
        return (info.isType(Direction.class));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        List<Escaque> rooks = new ArrayList<>(getNearbyRookEscaques(board, piece, start));
        rooks.add(board.getEscaque(start));
        this.addAllRookEscaques(rooks, board, piece);
        this.orderEscaques(rooks, dir);
        this.throwRooks(rooks, board, dir);

        for (Escaque escaque : rooks) {
            escaque.getPiece().postAction(Action.ABILITY, board, start, info);
            escaque.getPiece().changeCD(this.data.cooldown());
        }
        board.getClock().turnOf().changeMana(-this.data.manaCost());
    }

    protected void throwRooks(List<Escaque> rooks, AbstractSquareBoard board, Direction dir) {
        rooks.forEach(et -> throwTo(et.getPos(), board, dir));
    }

    /**
     * Throws the tower to the given direction.
     */
    protected void throwTo(Point start, AbstractSquareBoard board, Direction dir) {
        Point end = this.getEndPointNoJump(board, start, dir, -1);
        if (start.equals(end)) { return; }
        Point nextEnd = end.add(dir.toPoint());
        board.getPiece(start).setIsMoved(false);
        boolean done;
        if (!board.shape.isOutOfBorders(nextEnd) && board.tryTo(Action.TAKE, start, nextEnd.toInfo(), true)) {
            done = true;
        } else {
            done = board.tryTo(Action.MOVE, start, end.toInfo(), true);
        }
        if (!done) {
            LogManager.severe("tower didn't make the ability, start: %s, end: %s, dir: %s", start, end, dir);
        }
    }

    /**
     * Connects all towers that are side by side to each other
     */
    protected void addAllRookEscaques(List<Escaque> rooks, AbstractSquareBoard board, Piece piece) {
        var rookFinded = true;
        while (rookFinded) {
            List<Escaque> tmp = new ArrayList<>();
            rookFinded = false;
            for (Escaque escaqueRook : rooks) {
                for (Escaque escaqueRookSide : getNearbyRookEscaques(board, piece, escaqueRook.getPos())) {
                    if (!(rooks.contains(escaqueRookSide) || tmp.contains(escaqueRookSide))) {
                        tmp.add(escaqueRookSide);
                        rookFinded = true;
                    }
                }
            }
            rooks.addAll(tmp);
        }
    }

    protected List<Escaque> getNearbyRookEscaques(AbstractSquareBoard board, Piece piece, Point start) {
        return board.getNearbyEscaques(start).stream()
                .filter(escaque -> escaque.getPiece() instanceof Rook && escaque.isControlledBy(piece.getColor()))
                .toList();
    }

    /**
     * Order the escaques in a way that the first rook being throwed its the closest
     * to the end of the board in the given direction.
     */
    protected void orderEscaques(List<Escaque> rooks, Direction direction) {
        switch (direction) {
            case N -> rooks.sort((e1, e2) -> -Integer.compare(e1.getPos().y, e2.getPos().y));
            case S -> rooks.sort((e1, e2) -> Integer.compare(e1.getPos().y, e2.getPos().y));
            case E -> rooks.sort((e1, e2) -> -Integer.compare(e1.getPos().x, e2.getPos().x));
            case W -> rooks.sort((e1, e2) -> Integer.compare(e1.getPos().x, e2.getPos().x));
        }
    }

    @Override
    public Direction[] getValues(AbstractSquareBoard board, Point start) {
        return Direction.values();
    }
}
