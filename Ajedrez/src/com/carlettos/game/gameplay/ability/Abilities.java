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
    //todo: poder actualizar las habilidades y cargarlas mediante un json
	private Abilities() {}

	public static final Ability ABILITY_NONE = new AbilityNone();
	
	public static final Ability ABILITY_BISHOP = new AbilityBishop();
    public static final Ability ABILITY_KING = new AbilityKing();
    public static final Ability ABILITY_KNIGHT = new AbilityKnight();
    public static final Ability ABILITY_PAWN = new AbilityPawn();
    public static final Ability ABILITY_QUEEN = new AbilityQueen();
    public static final Ability ABILITY_ROOK = new AbilityRook();

    public static final Ability ABILITY_BUILDER = new AbilityBuilder();
    public static final Ability ABILITY_CATAPULT = new AbilityCatapult();
    public static final Ability ABILITY_CRAZY_PAWN = new AbilityCrazyPawn();
    public static final Ability ABILITY_MAGICIAN = new AbilityMagician();
    public static final Ability ABILITY_PALADIN = new AbilityPaladin();
    public static final Ability ABILITY_RAM = new AbilityRam();
    public static final Ability ABILITY_SHIELD_BEARER = new AbilityShieldBearer();
    public static final Ability ABILITY_SHIP = new AbilityShip();
    public static final Ability ABILITY_SUPER_PAWN = new AbilitySuperPawn();
    public static final Ability ABILITY_TESLA_TOWER = new AbilityTeslaTower();
    public static final Ability ABILITY_WARLOCK = new AbilityWarlock();
}
