package com.carlettos.game.gameplay.pattern;

import java.util.function.Function;

import com.carlettos.game.gameplay.pattern.classic.PatternBishop;
import com.carlettos.game.gameplay.pattern.classic.PatternKing;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnMove;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnTake;
import com.carlettos.game.gameplay.pattern.classic.PatternQueen;
import com.carlettos.game.gameplay.pattern.classic.PatternRook;
import com.carlettos.game.gameplay.pattern.starting.PatternArcherAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternArcherMove;
import com.carlettos.game.gameplay.pattern.starting.PatternBallistaAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternCrazyPawn;
import com.carlettos.game.gameplay.pattern.starting.PatternLeechTake;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnMove;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnTake;
import com.carlettos.game.util.enums.Color;

public class Patterns {
    private Patterns() {}

    public static final PatternBishop BISHOP_PATTERN = new PatternBishop() {};
    public static final PatternKing KING_PATTERN = new PatternKing() {};
    public static final PatternKnight KNIGHT_PATTERN = new PatternKnight() {};

    public static final Function<Color, PatternPawnMove> PAWN_MOVE_PATTERN = c -> new PatternPawnMove() {
        @Override
        public Color getColor() {
            return c;
        }
    };
    public static final Function<Color, PatternPawnTake> PAWN_TAKE_PATTERN = c -> new PatternPawnTake() {
        @Override
        public Color getColor() {
            return c;
        }
    };

    public static final PatternQueen QUEEN_PATTERN = new PatternQueen() {};
    public static final PatternRook ROOK_PATTERN = new PatternRook() {};

    public static final PatternArcherAttack ARCHER_ATTACK_PATTERN = new PatternArcherAttack() {};
    public static final PatternArcherMove ARCHER_MOVE_PATTERN = new PatternArcherMove() {};
    public static final PatternBallistaAttack BALLISTA_ATTACK_PATTERN = new PatternBallistaAttack() {};
    public static final PatternCannonAttack CANNON_ATTACK_PATTERN = new PatternCannonAttack() {};
    public static final PatternCrazyPawn CRAZY_PAWN_PATTERN = PatternCrazyPawn.STANDARD_PATTERN;
    public static final PatternLeechTake LEECH_TAKE_PATTERN = new PatternLeechTake() {};
    public static final PatternMagicianMove MAGICIAN_MOVE_PATTERN = new PatternMagicianMove() {};
    public static final PatternStructureMove STRUCTURE_MOVE_PATTERN = new PatternStructureMove() {};

    public static final Function<Color, PatternSuperPawnMove> SUPER_PAWN_MOVE_PATTERN = c -> new PatternSuperPawnMove() {
        @Override
        public Color getColor() {
            return c;
        }
    };
    public static final Function<Color, PatternSuperPawnTake> SUPER_PAWN_TAKE_PATTERN = c -> new PatternSuperPawnTake() {
        @Override
        public Color getColor() {
            return c;
        }
    };
}
