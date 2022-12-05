package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Gargoyle extends SimplePiece {

    public Gargoyle(Color color) {
        super("gargoyle", Abilities.NO_ABILITY, color, Patterns.GARGOYLE_PATTERN, IPieceType.DEMONIC);
    }
}
