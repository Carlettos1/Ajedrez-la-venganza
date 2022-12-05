package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Succubus extends SimplePiece {

    public Succubus(Color color) {
        super("succubus", Abilities.SUCCUBUS_ABILITY, color, Patterns.BISHOP_PATTERN, IPieceType.BIOLOGIC,
                IPieceType.DEMONIC, IPieceType.TRANSPORTABLE);
    }
}
