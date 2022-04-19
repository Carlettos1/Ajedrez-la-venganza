package com.carlettos.game.core.function;

/**
 * Similar to java.util.function but with a void-returning no-argument function.
 *
 * @author Carlettos
 */
@FunctionalInterface
public interface Action {

    void execute();
}
