package com.carlettos.game.gameplay.ability;

import com.carlettos.game.gameplay.ability.classic.AbilityBishop;
import com.carlettos.game.gameplay.ability.classic.AbilityKing;
import com.carlettos.game.gameplay.ability.classic.AbilityKnight;
import com.carlettos.game.gameplay.ability.classic.AbilityPawn;
import com.carlettos.game.gameplay.ability.classic.AbilityQueen;
import com.carlettos.game.gameplay.ability.classic.AbilityRook;
import com.carlettos.game.gameplay.ability.starting.AbilityBuilder;
import com.carlettos.game.gameplay.ability.starting.AbilityCatapult;
import com.carlettos.game.gameplay.ability.starting.AbilityCrazyPawn;
import com.carlettos.game.gameplay.ability.starting.AbilityMagician;
import com.carlettos.game.gameplay.ability.starting.AbilityPaladin;
import com.carlettos.game.gameplay.ability.starting.AbilityRam;
import com.carlettos.game.gameplay.ability.starting.AbilityShieldBearer;
import com.carlettos.game.gameplay.ability.starting.AbilityShip;
import com.carlettos.game.gameplay.ability.starting.AbilitySuperPawn;
import com.carlettos.game.gameplay.ability.starting.AbilityTeslaTower;
import com.carlettos.game.gameplay.ability.starting.AbilityWarlock;

public final class Abilities {
    // TODO: poder actualizar las habilidades y cargarlas mediante un json
    private Abilities() {}

    public static final Ability NO_ABILITY = new AbilityNone();

    public static final Ability BISHOP_ABILITY = new AbilityBishop();
    public static final Ability KING_ABILITY = new AbilityKing();
    public static final Ability KNIGHT_ABILITY = new AbilityKnight();
    public static final Ability PAWN_ABILITY = new AbilityPawn();
    public static final Ability QUEEN_ABILITY = new AbilityQueen();
    public static final Ability ROOK_ABILITY = new AbilityRook();

    public static final Ability BUILDER_ABILITY = new AbilityBuilder();
    public static final Ability CATAPULT_ABILITY = new AbilityCatapult();
    public static final Ability CRAZY_PAWN_ABILITY = new AbilityCrazyPawn();
    public static final Ability MAGICIAN_ABILITY = new AbilityMagician();
    public static final Ability PALADIN_ABILITY = new AbilityPaladin();
    public static final Ability RAM_ABILITY = new AbilityRam();
    public static final Ability SHIELD_BEARER_ABILITY = new AbilityShieldBearer();
    public static final Ability SHIP_ABILITY = new AbilityShip();
    public static final Ability SUPER_PAWN_ABILITY = new AbilitySuperPawn();
    public static final Ability TESLA_TOWER_ABILITY = new AbilityTeslaTower();
    public static final Ability WARLOCK_ABILITY = new AbilityWarlock();
}
