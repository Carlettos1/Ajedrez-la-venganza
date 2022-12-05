package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.LogHelper;

public class AbilityRook extends Ability<Direction> {

    public AbilityRook() {
        super("rook", Time.lap(10), 0);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Direction.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Direction info) {
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        var dir = (Direction) info.getValue();
        List<Escaque> rooks = new ArrayList<>(getNearbyRookEscaques(board, board.getPiece(start), start));
        rooks.add(board.get(start));
        this.addAllRookEscaques(rooks, board, board.getPiece(start));
        this.orderEscaques(rooks, dir);
        this.throwRooks(rooks, board, dir);

        for (Escaque escaque : rooks) {
            escaque.getPiece().onAction(Action.ABILITY, board, start, info);
            escaque.getPiece().removeCD(this.data.cooldown());
        }
        board.getClock().turnOf().changeMana(-this.data.manaCost());
    }

    protected void throwRooks(List<Escaque> rooks, AbstractBoard board, Direction dir) {
        rooks.forEach(et -> throwTo(et.getPos(), board, dir));
    }

    /**
     * Throws the tower to the given direction.
     */
    protected void throwTo(Point start, AbstractBoard board, Direction dir) {
        var ray = board.rayCast(start, -1, true, dir, e -> e.hasPiece());
        if (ray.isEmpty()) { return; }
        Point end = ray.get(ray.size() - 1).getPos();
        if (start.equals(end)) { return; }
        board.getPiece(start).setIsMoved(false);
        boolean done = board.tryTo(Action.TAKE, start, end.toInfo(), true)
                || board.tryTo(Action.MOVE, start, end.toInfo(), true)
                || board.tryTo(Action.MOVE, start, (end = end.add(dir.toPoint().scale(-1))).toInfo(), true);
        if (!done && !start.equals(end)) {
            LogHelper.LOG
                    .severe("tower didn't make the ability, start: %s, end: %s, dir: %s".formatted(start, end, dir));
        }
    }

    /**
     * Connects all towers that are side by side to each other
     */
    protected void addAllRookEscaques(List<Escaque> rooks, AbstractBoard board, Piece piece) {
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

    protected List<Escaque> getNearbyRookEscaques(AbstractBoard board, Piece piece, Point start) {
        return board.getAll(Patterns.STRUCTURE_MOVE_PATTERN, start).stream()
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
    public List<Direction> getInfos(AbstractBoard board) {
        return List.of(Direction.values());
    }
}
