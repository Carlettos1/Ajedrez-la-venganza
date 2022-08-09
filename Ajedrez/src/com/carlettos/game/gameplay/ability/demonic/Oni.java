package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
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
    public Oni() {
        super("oni", 7, 1);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Player[] players = board.getClock().getPlayers(p -> !p.getColor().equals(piece.getColor()) && board.hasPiece(Magician.class, p.getColor()));
        if (players == null || players.length == 0) {
            return false;
        }
        return this.commonCanUse(board, piece) && info.isType(SubDirection.class);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Player player = board.getClock().getRandomPlayer(p -> !p.getColor().equals(piece.getColor()) && board.hasPiece(Magician.class, p.getColor()));
        //TODO: pattern
        var optional = board.getMatchingEscaques((board1, start1, end) -> true, null).stream().filter(e -> e.getPiece() instanceof Magician && e.getPiece().getColor().equals(player.getColor())).findFirst();
        if (optional.isEmpty()) {
            LogManager.severe("Magician not encountered, of player %s", player);
            return;
        }
        var e = optional.get();
        board.setPiece(e.getPos().add(((SubDirection)info.getValue()).toPoint()), piece);
        board.removePieceNoDeath(start);
    }

    @Override
    public IInfo[] getValues(AbstractSquareBoard board, Point start) {
        SubDirection[] values = {};
        Player[] players = board.getClock().getPlayers(p -> !p.getColor().equals(board.getPiece(start).getColor()) && board.hasPiece(Magician.class, p.getColor()));
        if (players == null || players.length == 0) {
            return values;
        }
        return SubDirection.values();
    }

}
