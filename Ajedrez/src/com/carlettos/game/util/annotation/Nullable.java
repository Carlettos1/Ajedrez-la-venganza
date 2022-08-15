package com.carlettos.game.util.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * The parameter annotated with this annotation can and will be null at some
 * point.
 */
@Target({PARAMETER, METHOD})
public @interface Nullable {}
