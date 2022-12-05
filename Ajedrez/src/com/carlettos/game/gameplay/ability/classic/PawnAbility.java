package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Bishop;
import com.carlettos.game.gameplay.piece.classic.Knight;
import com.carlettos.game.gameplay.piece.classic.Queen;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.gameplay.piece.demonic.Ogre;
import com.carlettos.game.gameplay.piece.demonic.Spider;
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
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.LogHelper;

public class PawnAbility extends Ability<Piece> {
    public static final List<Piece> POSSIBLE_PROMOTIONS = new ArrayList<>();
    static {
        POSSIBLE_PROMOTIONS.addAll(List.of(new Bishop(Color.GRAY), new Knight(Color.GRAY), new Queen(Color.GRAY),
                new Rook(Color.GRAY), new Archer(Color.GRAY), new Ballista(Color.GRAY), new Builder(Color.GRAY),
                new Cannon(Color.GRAY), new Catapult(Color.GRAY), new CrazyPawn(Color.GRAY), new Ram(Color.GRAY),
                new ShieldBearer(Color.GRAY), new Ship(Color.GRAY), new SuperPawn(Color.GRAY),
                new TeslaTower(Color.GRAY), new Warlock(Color.GRAY), new Spider(Color.GRAY), new Ogre(Color.GRAY)));
    }

    public PawnAbility() {
        super("pawn", Time.ZERO, 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        var p = (Piece) info.getValue();
        p.setColor(board.getPiece(start).getColor());
        board.set(start, p);
        p.setIsMoved(true);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Piece.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Piece info) {
        if (board.getPiece(start).getColor().equals(Color.WHITE)) {
            if (!board.contains(start.add(0, 1))) {
                return true;
            } else {
                return false;
            }
        } else if (board.getPiece(start).getColor().equals(Color.BLACK)) {
            if (start.y == 0) {
                return true;
            } else {
                return false;
            }
        }
        LogHelper.LOG.severe("INTENTANDO CORONAR CON OTRO COLOR");
        return false;
    }

    @Override
    public List<Piece> getInfos(AbstractBoard board) {
        return POSSIBLE_PROMOTIONS;
    }
}
