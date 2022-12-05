package com.carlettos.game.gameplay.pattern;

import java.util.function.Function;

import com.carlettos.game.gameplay.pattern.classic.BishopPattern;
import com.carlettos.game.gameplay.pattern.classic.KingPattern;
import com.carlettos.game.gameplay.pattern.classic.KnightPattern;
import com.carlettos.game.gameplay.pattern.classic.PawnMovePattern;
import com.carlettos.game.gameplay.pattern.classic.PawnTakePattern;
import com.carlettos.game.gameplay.pattern.classic.QueenPattern;
import com.carlettos.game.gameplay.pattern.classic.RookPattern;
import com.carlettos.game.gameplay.pattern.demonic.MermaidPattern;
import com.carlettos.game.gameplay.pattern.demonic.OniPattern;
import com.carlettos.game.gameplay.pattern.demonic.WitchPattern;
import com.carlettos.game.gameplay.pattern.starting.ArcherMovePattern;
import com.carlettos.game.gameplay.pattern.starting.BallistaAttackPattern;
import com.carlettos.game.gameplay.pattern.starting.CannonAttackPattern;
import com.carlettos.game.gameplay.pattern.starting.CrazyPawnPattern;
import com.carlettos.game.gameplay.pattern.starting.LeechTakePattern;
import com.carlettos.game.gameplay.pattern.starting.MagicianMovePattern;
import com.carlettos.game.gameplay.pattern.starting.RangePattern;
import com.carlettos.game.gameplay.pattern.starting.StructureMovePattern;
import com.carlettos.game.gameplay.pattern.starting.SuperPawnMovePattern;
import com.carlettos.game.gameplay.pattern.starting.SuperPawnTakePattern;
import com.carlettos.game.util.enums.Color;

public class Patterns {
    private Patterns() {}

    public static final BishopPattern BISHOP_PATTERN = new BishopPattern() {};
    public static final KingPattern KING_PATTERN = new KingPattern() {};
    public static final KnightPattern KNIGHT_PATTERN = new KnightPattern() {};

    public static final Function<Color, PawnMovePattern> PAWN_MOVE_PATTERN = c -> new PawnMovePattern() {
        @Override
        public Color getColor() {
            return c;
        }
    };
    public static final Function<Color, PawnTakePattern> PAWN_TAKE_PATTERN = c -> new PawnTakePattern() {
        @Override
        public Color getColor() {
            return c;
        }
    };

    public static final QueenPattern QUEEN_PATTERN = new QueenPattern() {};
    public static final RookPattern ROOK_PATTERN = new RookPattern() {};

    public static final RangePattern ARCHER_ATTACK_PATTERN = new RangePattern(4);
    public static final ArcherMovePattern ARCHER_MOVE_PATTERN = new ArcherMovePattern() {};
    public static final BallistaAttackPattern BALLISTA_ATTACK_PATTERN = new BallistaAttackPattern() {};
    public static final CannonAttackPattern CANNON_ATTACK_PATTERN = new CannonAttackPattern() {};
    public static final CrazyPawnPattern CRAZY_PAWN_PATTERN = CrazyPawnPattern.STANDARD_PATTERN;
    public static final LeechTakePattern LEECH_TAKE_PATTERN = new LeechTakePattern() {};
    public static final MagicianMovePattern MAGICIAN_MOVE_PATTERN = new MagicianMovePattern() {};
    public static final StructureMovePattern STRUCTURE_MOVE_PATTERN = new StructureMovePattern() {};

    public static final Function<Color, SuperPawnMovePattern> SUPER_PAWN_MOVE_PATTERN = c -> new SuperPawnMovePattern() {
        @Override
        public Color getColor() {
            return c;
        }
    };
    public static final Function<Color, SuperPawnTakePattern> SUPER_PAWN_TAKE_PATTERN = c -> new SuperPawnTakePattern() {
        @Override
        public Color getColor() {
            return c;
        }
    };

    public static final MermaidPattern MERMAID_PATTERN = new MermaidPattern();
    public static final OniPattern ONI_PATTERN = new OniPattern();
    public static final WitchPattern WITCH_PATTERN = new WitchPattern();
    public static final RangePattern GARGOYLE_PATTERN = new RangePattern(5); // TODO: cambiar por un pattern en linea
                                                                             // recta
}
