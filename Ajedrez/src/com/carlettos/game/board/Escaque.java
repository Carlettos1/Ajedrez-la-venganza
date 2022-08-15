package com.carlettos.game.board;

import java.util.Objects;

import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.LogManager;

/**
 * IT IS THE ENGLISH EQUIVALENT OF THE "SQUARE" OF THE BOARD, BUT I DON'T LIKE
 * THAT WORD, SO IT WILL REMAIN AS "ESCAQUE".
 *
 * @author Carlos
 */
public class Escaque {
    protected boolean magic;
    protected boolean buildable;
    protected final Point pos;
    protected Piece piece;

    /**
     * General constructor.
     *
     * @param magic     true if emits magic, false if not.
     * @param buildable true if admits a {@code Type.ESTRUCTURE} on top.
     * @param pos       pos of the escaque on the board.
     * @param piece     piece of the escaque. CANNOT BE NULL, IT MUST BE AT LEAST A
     *                  {@code Empty}
     */
    public Escaque(boolean magic, boolean buildable, Point pos, Piece piece) {
        this.magic = magic;
        this.buildable = buildable;
        this.pos = pos;
        this.piece = piece;
    }

    /**
     * Constructs a escaque without magic, buildable and with an empty piece.
     *
     * @param pos pos of the escaque on the board.
     */
    public Escaque(Point pos) {
        this(false, true, pos, new Empty());
    }

    /**
     * Returns color of the piece on top.
     *
     * @return color of the piece on top (GRAY if empty, which is their color).
     */
    public Color getPieceColor() {
        return getPiece().getColor();
    }

    /**
     * Checks if the piece on top is of the color provided.
     *
     * @param color color to check.
     * @return true if the color provided is the same of the piece on top. False
     *         otherwise.
     */
    public boolean isControlledBy(Color color) {
        return getPiece().getColor().equals(color);
    }

    /**
     * Checks if the piece on top is not an empty one.
     *
     * @return true if there is a piece on top, false if not.
     */
    public boolean hasPiece() {
        return !piece.isEmpty();
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (this.hasPiece()) {
            LogManager.fine("Changing %s for %s", this.piece, piece);
        }
        this.piece = piece;
    }

    public void setPieceIfEmpty(Piece pieza) {
        if (!this.hasPiece()) {
            this.piece = pieza;
        }
    }

    public Piece removePiece() {
        Piece previous = this.getPiece();
        this.setPiece(new Empty());
        return previous;
    }

    public Point getPos() {
        return pos;
    }

    public boolean isMagic() {
        return magic;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public void setIsMagic(boolean isMagic) {
        this.magic = isMagic;
    }

    public void setIsBuildable(boolean isBuildable) {
        this.buildable = isBuildable;
    }

    public void copyProperties(Escaque other) {
        this.setPiece(other.getPiece());
        this.buildable = other.buildable;
        this.magic = other.magic;
    }

    public void removeProperties() {
        this.removePiece();
        this.buildable = true;
        this.magic = false;
    }

    @Override
    public String toString() {
        return getPos().toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, pos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Escaque other = (Escaque) obj;
        return Objects.equals(piece, other.piece) && Objects.equals(pos, other.pos);
    }
}
