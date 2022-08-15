package com.carlettos.game.board.deathPile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (this.pile.isEmpty()) { return new Empty(); }
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
    public int hashCode() {
        return Objects.hash(pile);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        BasicDeathPile other = (BasicDeathPile) obj;
        return Objects.equals(pile, other.pile);
    }

    @Override
    public String toString() {
        return "BasicDeathPile [pile=" + pile + "]";
    }
}
