package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

/**
 * It's the ability wich doesn't uses any value of input
 */
public abstract class AbilityNoInfo extends Ability {

	protected AbilityNoInfo(String key, int cooldown, int manaCost) {
		super(key, cooldown, manaCost);
	}
	
	@Override
	public final String[] getValues(AbstractBoard board, Point start) { //todo: get real values
		return new String[] {"Usar"};
	}
}
