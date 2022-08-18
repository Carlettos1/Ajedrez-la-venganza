package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Mermaid extends SimplePiece {

    public Mermaid(Color color) {
        super("mermaid", Abilities.MERMAID_ABILITY, color, Patterns.MERMAID_PATTERN, IPieceType.DEMONIC,
                IPieceType.BIOLOGIC);
    }
}
