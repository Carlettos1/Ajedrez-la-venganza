package com.carlettos.game.core;

import com.carlettos.game.board.property.Color;

/**
 * TODO: que sea configurable
 * @author Carlos
 */
public class Constants {

    /** Preferred width and height of the escaque in display.*/
    public static final int ESCAQUE_LENGTH = 30;
    /** Preferred width of the card display.*/
    public static final int CARD_WIDTH = 100;
    /** Preferred height of the card display.*/
    public static final int CARD_HEIGHT = 120;
    /** Maximum ammount of cards per row.*/
    public static final int CARDS_PER_ROW = 3;
    /** Maximum ammount of cards per column.*/
    public static final int CARDS_PER_COLUMN = 5;
    /** First color of the escaques.*/
    public static final Color COLOR1 = Color.BLACK;
    /** Second color of the escaques.*/
    public static final Color COLOR2 = Color.WHITE;
    /** Percentage with 1 or 2 actions.*/
    public static final double PERCENTAGE1 = 0.3;
    /** Percentage with 3 actions.*/
    public static final double PERCENTAGE3 = 0.25;
    /** Percentage with 4 actions.*/
    public static final double PERCENTAGE4 = 0.2;
}
