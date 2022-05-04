package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

public abstract class AbilityNoInfo extends Ability {

	protected AbilityNoInfo(String name, String description, int cooldown, int manaCost) {
		super(name, description, cooldown, manaCost);
	}
	
	@Override
	public final String[] getValues(AbstractBoard board, Point start) { //todo: get real values
		return new String[] {"Usar"};
	}
}
