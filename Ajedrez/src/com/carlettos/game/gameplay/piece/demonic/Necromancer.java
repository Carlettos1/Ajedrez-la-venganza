package com.carlettos.game.gameplay.piece.demonic;

import java.util.LinkedList;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.property.Properties;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

public class Necromancer extends Piece implements IMove {

    public Necromancer(Color color) {
        super("necromancer", Abilities.NECROMANCER_ABILIY, color, IPieceType.DEMONIC);
        this.propertyManager.add(Properties.GENERIC_LIST, new LinkedList<>());
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.MAGICIAN_MOVE_PATTERN;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDeath(AbstractBoard board) {
        this.propertyManager.get(Properties.GENERIC_LIST).forEach(p -> {
            Piece piece = (Piece) p;
            board.remove(piece, true);
        });
    }
}
