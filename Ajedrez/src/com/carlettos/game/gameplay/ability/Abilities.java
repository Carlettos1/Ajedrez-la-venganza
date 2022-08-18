package com.carlettos.game.gameplay.ability;

import com.carlettos.game.gameplay.ability.AbilityNoInfo.NoInfo;
import com.carlettos.game.gameplay.ability.classic.AbilityBishop;
import com.carlettos.game.gameplay.ability.classic.AbilityKing;
import com.carlettos.game.gameplay.ability.classic.AbilityKnight;
import com.carlettos.game.gameplay.ability.classic.AbilityPawn;
import com.carlettos.game.gameplay.ability.classic.AbilityQueen;
import com.carlettos.game.gameplay.ability.classic.AbilityRook;
import com.carlettos.game.gameplay.ability.demonic.AbilityPortal;
import com.carlettos.game.gameplay.ability.demonic.ImpAbility;
import com.carlettos.game.gameplay.ability.demonic.ImpAbility.ImpAbilityType;
import com.carlettos.game.gameplay.ability.demonic.MandragoraAbility;
import com.carlettos.game.gameplay.ability.demonic.MermaidAbility;
import com.carlettos.game.gameplay.ability.demonic.OniAbility;
import com.carlettos.game.gameplay.ability.demonic.SpiderAbility;
import com.carlettos.game.gameplay.ability.demonic.SuccubusAbility;
import com.carlettos.game.gameplay.ability.demonic.WitchAbility;
import com.carlettos.game.gameplay.ability.starting.AbilityBuilder;
import com.carlettos.game.gameplay.ability.starting.AbilityCatapult;
import com.carlettos.game.gameplay.ability.starting.AbilityCrazyPawn;
import com.carlettos.game.gameplay.ability.starting.AbilityMagician;
import com.carlettos.game.gameplay.ability.starting.AbilityPaladin;
import com.carlettos.game.gameplay.ability.starting.AbilityPaladin.PaladinHabilityType;
import com.carlettos.game.gameplay.ability.starting.AbilityRam;
import com.carlettos.game.gameplay.ability.starting.AbilityShieldBearer;
import com.carlettos.game.gameplay.ability.starting.AbilityShip;
import com.carlettos.game.gameplay.ability.starting.AbilitySuperPawn;
import com.carlettos.game.gameplay.ability.starting.AbilityTeslaTower;
import com.carlettos.game.gameplay.ability.starting.AbilityWarlock;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;

public final class Abilities {
    private Abilities() {}

    public static final Ability<NoInfo> NO_ABILITY = new AbilityNone();

    public static final Ability<Direction> BISHOP_ABILITY = new AbilityBishop();
    public static final Ability<Point> KING_ABILITY = new AbilityKing();
    public static final Ability<NoInfo> KNIGHT_ABILITY = new AbilityKnight();
    public static final Ability<Piece> PAWN_ABILITY = new AbilityPawn();
    public static final Ability<Point> QUEEN_ABILITY = new AbilityQueen();
    public static final Ability<Direction> ROOK_ABILITY = new AbilityRook();

    public static final Ability<Direction> BUILDER_ABILITY = new AbilityBuilder();
    public static final Ability<Tuple<Direction, SubDirection>> CATAPULT_ABILITY = new AbilityCatapult();
    public static final Ability<NoInfo> CRAZY_PAWN_ABILITY = new AbilityCrazyPawn();
    public static final Ability<NoInfo> MAGICIAN_ABILITY = new AbilityMagician();
    public static final Ability<Tuple<PaladinHabilityType, Point>> PALADIN_ABILITY = new AbilityPaladin();
    public static final Ability<Direction> RAM_ABILITY = new AbilityRam();
    public static final Ability<NoInfo> SHIELD_BEARER_ABILITY = new AbilityShieldBearer();
    public static final Ability<NoInfo> SHIP_ABILITY = new AbilityShip();
    public static final Ability<NoInfo> SUPER_PAWN_ABILITY = new AbilitySuperPawn();
    public static final Ability<NoInfo> TESLA_TOWER_ABILITY = new AbilityTeslaTower();
    public static final Ability<NoInfo> WARLOCK_ABILITY = new AbilityWarlock();

    public static final Ability PORTAL_ABILITY = new AbilityPortal();
    public static final Ability<ImpAbilityType> IMP_ABILITY = new ImpAbility();
    public static final Ability<NoInfo> MANDRAGORA_ABILITY = new MandragoraAbility();
    public static final Ability<NoInfo> MERMAID_ABILITY = new MermaidAbility();
    public static final Ability<SubDirection> ONI_ABILITY = new OniAbility();
    public static final Ability<SubDirection> SPIDER_ABILITY = new SpiderAbility();
    public static final Ability<Point> SUCCUBUS_ABILITY = new SuccubusAbility();
    public static final Ability<NoInfo> WITCH_ABILITY = new WitchAbility();
}
