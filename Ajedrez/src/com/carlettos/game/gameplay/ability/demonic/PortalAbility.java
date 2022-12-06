package com.carlettos.game.gameplay.ability.demonic;

import java.util.LinkedHashMap;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.demonic.Basilisk;
import com.carlettos.game.gameplay.piece.demonic.Dragon;
import com.carlettos.game.gameplay.piece.demonic.Gargoyle;
import com.carlettos.game.gameplay.piece.demonic.Golem;
import com.carlettos.game.gameplay.piece.demonic.Imp;
import com.carlettos.game.gameplay.piece.demonic.Mandragora;
import com.carlettos.game.gameplay.piece.demonic.Mermaid;
import com.carlettos.game.gameplay.piece.demonic.Necromancer;
import com.carlettos.game.gameplay.piece.demonic.Ogre;
import com.carlettos.game.gameplay.piece.demonic.Oni;
import com.carlettos.game.gameplay.piece.demonic.Spider;
import com.carlettos.game.gameplay.piece.demonic.Succubus;
import com.carlettos.game.gameplay.piece.demonic.Witch;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

public class PortalAbility extends Ability<Piece> {
    public static final LinkedHashMap<Piece, Time> POSSIBLE_INVOCATIONS = new LinkedHashMap<>();
    static {
        POSSIBLE_INVOCATIONS.put(new Basilisk(Color.GRAY), Time.movement(20));
        POSSIBLE_INVOCATIONS.put(new Dragon(Color.GRAY), Time.lap(10));
        POSSIBLE_INVOCATIONS.put(new Gargoyle(Color.GRAY), Time.movement(10));
        POSSIBLE_INVOCATIONS.put(new Golem(Color.GRAY), Time.lap(2));
        POSSIBLE_INVOCATIONS.put(new Imp(Color.GRAY), Time.turn(8));
        POSSIBLE_INVOCATIONS.put(new Mandragora(Color.GRAY), Time.lap(3));
        POSSIBLE_INVOCATIONS.put(new Mermaid(Color.GRAY), Time.movement(20));
        POSSIBLE_INVOCATIONS.put(new Necromancer(Color.GRAY), Time.lap(5));
        POSSIBLE_INVOCATIONS.put(new Ogre(Color.GRAY), Time.lap(2));
        POSSIBLE_INVOCATIONS.put(new Oni(Color.GRAY), Time.turn(1));
        POSSIBLE_INVOCATIONS.put(new Spider(Color.GRAY), Time.turn(6));
        POSSIBLE_INVOCATIONS.put(new Succubus(Color.GRAY), Time.lap(3));
        POSSIBLE_INVOCATIONS.put(new Witch(Color.GRAY), Time.movement(25));
    }

    public PortalAbility() {
        super("portal", Time.ZERO, 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        board.getPiece(start).setIsMoved(true);
        board.getPiece(start).removeCD(POSSIBLE_INVOCATIONS.get(info.getValue()));
        board.getClock().addEvent(null);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Piece.class);
    }

    @Override
    public List<Piece> getInfos(AbstractBoard board) {
        return POSSIBLE_INVOCATIONS.keySet().stream().toList();
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Piece info) {
        return true;
    }
}
