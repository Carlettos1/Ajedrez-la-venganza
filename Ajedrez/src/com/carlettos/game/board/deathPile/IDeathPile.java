package com.carlettos.game.board.deathPile;

import java.util.List;

import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.enums.Color;

/**
 * Contains all the functions required to make the death pile work.
 */
public interface IDeathPile {

    void add(Piece piece);

    Piece getLast();

    Piece getLast(Color color);

    List<Piece> getList();
}
