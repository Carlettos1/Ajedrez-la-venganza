package com.carlettos.game.gameplay.piece.property;

import java.util.LinkedHashMap;

public class PropertyManager {
    @SuppressWarnings("rawtypes")
    protected final LinkedHashMap properties;

    @SuppressWarnings("rawtypes")
    public PropertyManager() {
        this.properties = new LinkedHashMap();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Property<T> property) {
        return (T) this.properties.get(property);
    }

    @SuppressWarnings("unchecked")
    public <T> void add(Property<T> property, T defaultValue) {
        this.properties.put(property, defaultValue);
    }

    public <T> boolean has(Property<T> property) {
        return this.properties.containsKey(property);
    }

    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }
}
