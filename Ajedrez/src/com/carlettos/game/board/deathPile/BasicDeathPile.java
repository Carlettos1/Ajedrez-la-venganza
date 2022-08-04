package com.carlettos.game.board.deathPile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.enums.Color;

public class BasicDeathPile implements IDeathPile {
    private final List<Piece> pile;

    public BasicDeathPile() {
        this.pile = new ArrayList<>();
    }

    @Override
    public void add(Piece piece) {
        this.pile.add(piece);
    }

    @Override
    public Piece getLast() {
        if (this.pile.isEmpty()) {
            return new Empty();
        }
        return this.pile.remove(pile.size() - 1);
    }

    @Override
    public Piece getLast(Color color) {
        Collections.reverse(pile);
        Piece last = new Empty();
        for (Piece piece : pile) {
            if (piece.getColor().equals(color)) {
                last = piece;
                break;
            }
        }
        this.pile.remove(last);
        Collections.reverse(pile);
        return last;
    }

    @Override
    public List<Piece> getList() {
        return this.pile;
    }

}
