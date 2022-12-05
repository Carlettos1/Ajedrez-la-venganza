package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Dragon extends SimplePiece {

    public Dragon(Color color) {
        // FIXME no ability
        super("dragon", Abilities.NO_ABILITY, color, Patterns.MERMAID_PATTERN, IPieceType.IMMUNE, IPieceType.HEROIC,
                IPieceType.BIOLOGIC, IPieceType.DEMONIC);
    }
}
