package com.carlettos.game.util.enums;

/**
 *
 * @author Carlos
 */
public enum ActionResult {
    PASS(true), FAIL(false);

    private final boolean positive;

    private ActionResult(boolean positive) {
        this.positive = positive;
    }

    public boolean isPositive() {
        return positive;
    }

    public ActionResult or(ActionResult other) {
        return fromBoolean(this.positive || other.positive);
    }

    public ActionResult and(ActionResult other) {
        return fromBoolean(this.positive && other.positive);
    }

    public ActionResult or(boolean bool) {
        return fromBoolean(this.positive || bool);
    }

    public ActionResult and(boolean bool) {
        return fromBoolean(this.positive && bool);
    }

    public static ActionResult max(ActionResult ar1, ActionResult ar2) {
        return ar1.ordinal() > ar2.ordinal() ? ar1 : ar2;
    }

    public static ActionResult fromBoolean(boolean bool) {
        return bool ? PASS : FAIL;
    }
}
