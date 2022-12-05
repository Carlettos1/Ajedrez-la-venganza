package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.property.Properties;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Golem extends SimplePiece {

    public Golem(Color color) {
        super("golem", Abilities.NO_ABILITY, color, Patterns.ARCHER_MOVE_PATTERN, IPieceType.HEROIC, IPieceType.DEMONIC,
                IPieceType.IMMUNE, IPieceType.STRUCTURE, IPieceType.IMPENETRABLE, IPieceType.TOUGH);
        this.propertyManager.add(Properties.MAX_TAKEN_TIMES, 3);
    }
}
