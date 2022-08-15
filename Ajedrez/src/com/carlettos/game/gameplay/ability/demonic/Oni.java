package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.starting.Magician;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction.SubDirection;
import com.carlettos.game.util.helper.LogManager;

public class Oni extends Ability {
    private static final Contains c = (b,
            p) -> b.indexOf(e -> e.getPiece() instanceof Magician && p.getColor().equals(e.getPieceColor())) != -1;

    public Oni() {
        super("oni", 7, 1);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        Player[] players = board.getClock()
                .getPlayers(p -> !p.getColor().equals(piece.getColor()) && c.contains(board, p));
        if (players == null || players.length == 0) { return false; }
        return this.commonCanUse(board, piece) && info.isType(SubDirection.class);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Player player = board.getClock()
                .getRandomPlayer(p -> !p.getColor().equals(piece.getColor()) && c.contains(board, p));
        var optional = board.stream()
                .filter(e -> e.getPiece() instanceof Magician && e.getPieceColor().equals(player.getColor()))
                .findFirst();
        if (optional.isEmpty()) {
            LogManager.severe("Magician not encountered, of player %s", player);
            return;
        }
        var e = optional.get();
        board.set(e.getPos().add(((SubDirection) info.getValue()).toPoint()), piece);
        board.remove(start, false);
    }

    @Override
    public IInfo[] getValues(AbstractBoard board, Point start) {
        SubDirection[] values = {};
        Player[] players = board.getClock()
                .getPlayers(p -> !p.getColor().equals(board.getPiece(start).getColor()) && c.contains(board, p));
        if (players == null || players.length == 0) { return values; }
        return SubDirection.values();
    }

    @FunctionalInterface
    public static interface Contains {
        boolean contains(AbstractBoard b, Player p);
    }
}
