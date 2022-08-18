package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.starting.Magician;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction.SubDirection;
import com.carlettos.game.util.helper.LogHelper;

public class OniAbility extends Ability<SubDirection> {
    private static final ComplexPredicate c = (b, s, p) -> !p.getColor().equals(b.get(s).getPieceColor())
            && (b.indexOf(e -> e.getPiece() instanceof Magician && p.getColor().equals(e.getPieceColor())) != -1);

    public OniAbility() {
        super("oni", 7, 1);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Player player = board.getClock().getRandomPlayer(p -> c.test(board, start, p));
        var optional = board.stream()
                .filter(e -> e.getPiece() instanceof Magician && e.getPieceColor().equals(player.getColor()))
                .findFirst();
        if (optional.isEmpty()) {
            LogHelper.LOG.severe("Magician not encountered, of player %s" + player);
            return;
        }
        var e = optional.get();
        Point newPos = e.getPos().add(((SubDirection) info.getValue()).toPoint());
        if (board.get(newPos).hasPiece()) {
            board.remove(newPos, true);
        }
        board.set(newPos, board.getPiece(start));
        board.remove(start, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(SubDirection.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, SubDirection info) {
        Player[] players = board.getClock().getPlayers(p -> c.test(board, start, p));
        return players != null && players.length != 0;
    }

    @Override
    public List<SubDirection> getInfos(AbstractBoard board) {
        return List.of(SubDirection.values());
    }

    @FunctionalInterface
    public static interface ComplexPredicate {
        boolean test(AbstractBoard b, Point start, Player p);
    }
}
