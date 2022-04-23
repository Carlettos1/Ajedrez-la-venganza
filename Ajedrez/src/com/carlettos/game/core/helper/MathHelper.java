package com.carlettos.game.core.helper;

/**
 *
 * @author Carlettos
 */
public final class MathHelper {
    private MathHelper(){}
    
    public static final int clamp(int min, int max, int num){
        if(num > max) {
            return max;
        } else if(num < min) {
            return min;
        } else {
            return num;
        }
    }
}
