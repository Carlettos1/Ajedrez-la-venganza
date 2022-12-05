package com.carlettos.game.gameplay.piece.property;

import java.util.List;

public class Properties {
    private Properties() {}

    public static final Property<Integer> MAX_TAKEN_TIMES = new Property<>("max_taken_times", Integer.class);
    public static final Property<Integer> TAKEN_TIMES = new Property<>("taken_times", Integer.class);
    @SuppressWarnings("rawtypes")
    public static final Property<List> GENERIC_LIST = new Property<>("generic_list", List.class);
}
