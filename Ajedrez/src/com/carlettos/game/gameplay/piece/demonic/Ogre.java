package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Ogre extends SimplePiece {
    public Ogre(Color color) {
        super("ogre", ability, color, Patterns.ARCHER_MOVE_PATTERN, PieceType.DEMONIC, PieceType.IMPENETRABLE);
    }
}
