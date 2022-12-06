package com.carlettos.game.gameplay.ability;

import com.carlettos.game.gameplay.ability.NoInfoAbility.NoInfo;
import com.carlettos.game.gameplay.ability.classic.BishopAbility;
import com.carlettos.game.gameplay.ability.classic.KingAbility;
import com.carlettos.game.gameplay.ability.classic.KnightAbility;
import com.carlettos.game.gameplay.ability.classic.PawnAbility;
import com.carlettos.game.gameplay.ability.classic.QueenAbility;
import com.carlettos.game.gameplay.ability.classic.RookAbility;
import com.carlettos.game.gameplay.ability.demonic.BasiliskAbility;
import com.carlettos.game.gameplay.ability.demonic.ImpAbility;
import com.carlettos.game.gameplay.ability.demonic.ImpAbility.ImpAbilityType;
import com.carlettos.game.gameplay.ability.demonic.MandragoraAbility;
import com.carlettos.game.gameplay.ability.demonic.MermaidAbility;
import com.carlettos.game.gameplay.ability.demonic.NecromancerAbility;
import com.carlettos.game.gameplay.ability.demonic.OgreAbility;
import com.carlettos.game.gameplay.ability.demonic.OniAbility;
import com.carlettos.game.gameplay.ability.demonic.PortalAbility;
import com.carlettos.game.gameplay.ability.demonic.SpiderAbility;
import com.carlettos.game.gameplay.ability.demonic.SuccubusAbility;
import com.carlettos.game.gameplay.ability.demonic.WitchAbility;
import com.carlettos.game.gameplay.ability.starting.BuilderAbility;
import com.carlettos.game.gameplay.ability.starting.CatapultAbility;
import com.carlettos.game.gameplay.ability.starting.CrazyPawnAbility;
import com.carlettos.game.gameplay.ability.starting.MagicianAbility;
import com.carlettos.game.gameplay.ability.starting.PaladinAbility;
import com.carlettos.game.gameplay.ability.starting.PaladinAbility.PaladinHabilityType;
import com.carlettos.game.gameplay.ability.starting.RamAbility;
import com.carlettos.game.gameplay.ability.starting.ShieldBearerAbility;
import com.carlettos.game.gameplay.ability.starting.ShipAbility;
import com.carlettos.game.gameplay.ability.starting.SuperPawnAbility;
import com.carlettos.game.gameplay.ability.starting.TeslaTowerAbility;
import com.carlettos.game.gameplay.ability.starting.WarlockAbility;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;

public final class Abilities {
    private Abilities() {}

    public static final Ability<NoInfo> NO_ABILITY = new AbilityNone();

    public static final Ability<Direction> BISHOP_ABILITY = new BishopAbility();
    public static final Ability<Point> KING_ABILITY = new KingAbility();
    public static final Ability<NoInfo> KNIGHT_ABILITY = new KnightAbility();
    public static final Ability<Piece> PAWN_ABILITY = new PawnAbility();
    public static final Ability<Point> QUEEN_ABILITY = new QueenAbility();
    public static final Ability<Direction> ROOK_ABILITY = new RookAbility();

    public static final Ability<Direction> BUILDER_ABILITY = new BuilderAbility();
    public static final Ability<Tuple<Direction, SubDirection>> CATAPULT_ABILITY = new CatapultAbility();
    public static final Ability<NoInfo> CRAZY_PAWN_ABILITY = new CrazyPawnAbility();
    public static final Ability<NoInfo> MAGICIAN_ABILITY = new MagicianAbility();
    public static final Ability<Tuple<PaladinHabilityType, Point>> PALADIN_ABILITY = new PaladinAbility();
    public static final Ability<Direction> RAM_ABILITY = new RamAbility();
    public static final Ability<NoInfo> SHIELD_BEARER_ABILITY = new ShieldBearerAbility();
    public static final Ability<NoInfo> SHIP_ABILITY = new ShipAbility();
    public static final Ability<NoInfo> SUPER_PAWN_ABILITY = new SuperPawnAbility();
    public static final Ability<NoInfo> TESLA_TOWER_ABILITY = new TeslaTowerAbility();
    public static final Ability<NoInfo> WARLOCK_ABILITY = new WarlockAbility();

    public static final Ability<Piece> PORTAL_ABILITY = new PortalAbility();
    public static final Ability<ImpAbilityType> IMP_ABILITY = new ImpAbility();
    public static final Ability<NoInfo> MANDRAGORA_ABILITY = new MandragoraAbility();
    public static final Ability<NoInfo> MERMAID_ABILITY = new MermaidAbility();
    public static final Ability<SubDirection> ONI_ABILITY = new OniAbility();
    public static final Ability<SubDirection> SPIDER_ABILITY = new SpiderAbility();
    public static final Ability<Point> SUCCUBUS_ABILITY = new SuccubusAbility();
    public static final Ability<NoInfo> WITCH_ABILITY = new WitchAbility();
    public static final Ability<NoInfo> BASILISK_ABILITY = new BasiliskAbility();
    public static final Ability<Direction> OGRE_ABILITY = new OgreAbility();
    public static final Ability<Point> NECROMANCER_ABILIY = new NecromancerAbility();
}
