package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.Invulnerability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.helper.CardHelper;

@SuppressWarnings("unchecked")
public class AbilityPaladin extends Ability {
    public static final Pattern DEMONIC_ATTACK_PATTERN = Patterns.CANNON_ATTACK_PATTERN;
    public static final Pattern INVULNERABLE_PATTERN = Patterns.ARCHER_MOVE_PATTERN;
    public static final Pattern REVIVE_PATTERN = Patterns.KING_PATTERN;
    public static final Pattern[] PATTERNS = { DEMONIC_ATTACK_PATTERN, INVULNERABLE_PATTERN, REVIVE_PATTERN };

    static {
        Info.register(PaladinHabilityType.class);
    }

    public AbilityPaladin() {
        super("paladin", 8, 2);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!info.isTupleType(PaladinHabilityType.class, Point.class) || !this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }
        Tuple<PaladinHabilityType, Point> tuple = (Tuple<PaladinHabilityType, Point>) info.getValue();
        boolean result = false;

        if (board.getMatchingEscaques(PATTERNS[tuple.x.ordinal()], start).stream().map(e -> e.getPos())
                .anyMatch(tuple.y::equals)) {
            boolean hasPiece = board.getEscaque(tuple.y).hasPiece();
            boolean isEqualColor = board.getEscaque(tuple.y).isControlledBy(piece.getColor());
            result = switch (tuple.x) {
                case ATTACK ->
                    CardHelper.boardHasCard(board, CardsOnBoard.ATTACK_TO_DEMONIC) && hasPiece && !isEqualColor;
                case INVULNERABILITY ->
                    CardHelper.boardHasCard(board, CardsOnBoard.INVULNERABILITY) && hasPiece && isEqualColor;
                case REVIVE -> CardHelper.boardHasCard(board, CardsOnBoard.REVIVE) && !hasPiece;
            };
        }
        return ActionResult.fromBoolean(result);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Tuple<PaladinHabilityType, Point> tuple = (Tuple<PaladinHabilityType, Point>) info.getValue();

        switch (tuple.x) {
            case ATTACK -> board.killPiece(tuple.y);
            case INVULNERABILITY -> board.getPiece(tuple.y).getEffectManager().addEffect(new Invulnerability(5));
            case REVIVE -> board.setPiece(tuple.y, board.getDeathPile().getLast());
        }
    }

    @Override
    public Tuple<PaladinHabilityType, Point>[] getValues(AbstractSquareBoard board, Point start) {
        if (board.getPiece(start) != null) {
            List<Tuple<PaladinHabilityType, Point>> list = new ArrayList<>(30);
            Piece piece = board.getPiece(start);

            for (PaladinHabilityType type : PaladinHabilityType.values()) {
                board.getMatchingEscaques(PATTERNS[type.ordinal()], start).forEach(e -> {
                    boolean hasPiece = e.hasPiece();
                    boolean isEqualColor = e.isControlledBy(piece.getColor());
                    boolean result = switch (type) {
                        case ATTACK ->
                            CardHelper.boardHasCard(board, CardsOnBoard.ATTACK_TO_DEMONIC) && hasPiece && !isEqualColor;
                        case INVULNERABILITY ->
                            CardHelper.boardHasCard(board, CardsOnBoard.INVULNERABILITY) && hasPiece && isEqualColor;
                        case REVIVE -> CardHelper.boardHasCard(board, CardsOnBoard.REVIVE) && !hasPiece;
                    };
                    if (result) {
                        list.add(Tuple.of(type, e.getPos()));
                    }
                });
            }
            return list.toArray(Tuple[]::new);
        }
        return new Tuple[] {};
    }

    public static enum PaladinHabilityType implements IInfo {
        ATTACK, INVULNERABILITY, REVIVE;

        @Override
        public Info toInfo() {
            return Info.getInfo(this);
        }
    }
}
