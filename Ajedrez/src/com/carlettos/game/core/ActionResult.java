package com.carlettos.game.core;

/**
 * Son los posibles resultados de una acción, sirve para reaccionar de acuerdo a
 * lo que pueda ocurrir en cualquier méthodo.
 *
 * @author Carlos
 * 
 * @see Action
 */
public enum ActionResult {
    PASS(true),
    FAIL(false);

    private final boolean positive;

    private ActionResult(boolean positive) {
        this.positive = positive;
    }

    public boolean isPositive() {
        return positive;
    }
    
    public static ActionResult max(ActionResult ar1, ActionResult ar2){
        return ar1.ordinal() > ar2.ordinal() ? ar1 : ar2;
    }
    
    public static ActionResult fromBoolean(boolean bool){
        return bool ? PASS : FAIL;
    }
}
