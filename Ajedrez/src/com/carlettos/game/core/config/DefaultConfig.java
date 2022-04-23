package com.carlettos.game.core.config;

/**
 *
 * @author Carlettos
 */
public class DefaultConfig {

    private int escaque_lenght;
    private int card_width;
    private int card_height;
    private int cards_per_row;
    private int cards_per_column;
    private int color_1_id;
    private int color_2_id;
    private double percentage_1;
    private double percentage_2;
    private double percentage_3;
    private double percentage_4;

    public DefaultConfig() {
    }

    @Override
    public String toString() {
        return "DefaultConfig{" + 
                "escaque_lenght=" + escaque_lenght + 
                ", card_width=" + card_width + 
                ", card_height=" + card_height + 
                ", cards_per_row=" + cards_per_row + 
                ", cards_per_column=" + cards_per_column + 
                ", color_1_id=" + color_1_id + 
                ", color_2_id=" + color_2_id + 
                ", percentage_1=" + percentage_1 + 
                ", percentage_2=" + percentage_2 + 
                ", percentage_3=" + percentage_3 + 
                ", percentage_4=" + percentage_4 + '}';
    }

    public int getEscaque_lenght() {
        return escaque_lenght;
    }

    public int getCard_width() {
        return card_width;
    }

    public int getCard_height() {
        return card_height;
    }

    public int getCards_per_row() {
        return cards_per_row;
    }

    public int getCards_per_column() {
        return cards_per_column;
    }

    public int getColor_1_id() {
        return color_1_id;
    }

    public int getColor_2_id() {
        return color_2_id;
    }

    public double getPercentage_1() {
        return percentage_1;
    }

    public double getPercentage_2() {
        return percentage_2;
    }

    public double getPercentage_3() {
        return percentage_3;
    }

    public double getPercentage_4() {
        return percentage_4;
    }
}
