package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.enums.Color;

public class Spider extends SimplePiece {

    public Spider(Color color) {
        super("spider", Abilities.SPIDER_ABILITY, color, Patterns.KNIGHT_PATTERN, PieceType.DEMONIC, PieceType.TRANSPORTABLE, PieceType.BIOLOGIC);
    }
}
