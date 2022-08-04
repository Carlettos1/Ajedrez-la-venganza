package com.carlettos.game.util.function;

/**
 * Similar to a java.util.function interface but with a void-returning
 * no-argument function.
 *
 * @author Carlettos
 */
@FunctionalInterface
public interface Action {

    void execute();
}
