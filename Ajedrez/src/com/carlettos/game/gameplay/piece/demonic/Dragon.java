package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Dragon extends SimplePiece {

    public Dragon(Color color) {
        super("dragon", ability, color, Patterns.MERMAID_PATTERN, PieceType.IMMUNE, PieceType.HEROIC, PieceType.BIOLOGIC, PieceType.DEMONIC);
    }
}
