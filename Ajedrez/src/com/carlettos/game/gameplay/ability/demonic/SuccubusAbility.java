package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.util.Point;

public class SuccubusAbility extends Ability<Point> {
    
    public SuccubusAbility(String name, int cooldown, int manaCost) {
        super(name, cooldown, manaCost);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        board.getPiece((Point) info.getValue()).getEffectManager().addEffect(new DeactivateEffect(6));
        this.commonUse(board, start);
    }
    
    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Point.class);
    }
    
    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Point info) {
        return board.get(info).hasPiece();
    }

    @Override
    public List<Point> getInfos(AbstractBoard board) {
        return board.stream().map(e -> e.getPos()).toList();
    }
}
