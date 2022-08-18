package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

public class Necromancer extends Piece implements IMove {

    public Necromancer(Color color) {
        super("necromancer", ability, color, PieceType.DEMONIC);
    }
    
    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) 
        return Patterns.MAGICIAN_MOVE_PATTERN;
    }
}
