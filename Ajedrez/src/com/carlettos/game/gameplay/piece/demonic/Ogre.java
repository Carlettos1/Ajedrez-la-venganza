package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Ogre extends SimplePiece {
    public Ogre(Color color) {
        super("ogre", Abilities.OGRE_ABILITY, color, Patterns.ARCHER_MOVE_PATTERN, IPieceType.DEMONIC,
                IPieceType.IMPENETRABLE);
    }
}
