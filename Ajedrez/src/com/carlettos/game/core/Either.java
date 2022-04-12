package com.carlettos.game.core;

/**
 *
 * @author Carlettos
 */
public class Either<L, R> {
    public final L left;
    public final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public boolean isRight(){
        return this.right != null;
    }
    
    public boolean isLeft(){
        return this.left != null;
    }

    @Override
    public String toString() {
        if(isRight()){
            return this.right.toString();
        } else {
            return this.left.toString();
        }
    }
    
    public static <L, R> Either<L, R> makeRight(R right){
        return new Either<>(null, right);
    }
    
    public static <L, R> Either<L, R> makeLeft(L left){
        return new Either<>(left, null);
    }
}
