package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Witch extends SimplePiece {

    public Witch(Color color) {
        super("witch", Abilities.WITCH_ABILITY, color, Patterns.WITCH_PATTERN, IPieceType.DEMONIC);
    }
}
