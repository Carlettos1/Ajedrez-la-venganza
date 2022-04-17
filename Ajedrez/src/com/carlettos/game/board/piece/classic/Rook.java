package com.carlettos.game.board.piece.classic;

import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.classic.PatternRook;
import com.carlettos.game.board.property.ability.InfoNESW;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadNESW;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rook extends SimplePiece<PatternRook> {

    public static final Ability<Rook, Direction, InfoNESW> HABILIDAD_TORRE = new HabilidadTorre<>();

    public Rook(Color color) {
        super("Torre", "T", HABILIDAD_TORRE, color, new PatternRook(){}, PieceType.ESTRUCTURA);
    }

    public static class HabilidadTorre<P extends Piece> extends Ability<P, Direction, InfoNESW> implements HabilidadNESW {

        public HabilidadTorre() {
            super("Muro de Berlín",
                    "\"Lanza\" todas las torres contiguas en una dirección y se detienen "
                    + "si y solo si se cumple alguna de las siguientes condicioens: "
                    + "1.- Alcanza el borde del tablero"
                    + "2.- Comen una pieza enemiga"
                    + "3.- Colisionan con una pieza aliada.",
                    10,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            if (!this.commonCanUsar(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            return ActionResult.fromBoolean(tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            Board t;
            if(tablero instanceof Board){
                t = (Board) tablero;
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            
            List<Escaque> escaquesTorres = new ArrayList<>(getEscaqueTorresAdyacentes(tablero, pieza, inicio));
            escaquesTorres.add(tablero.getEscaque(inicio));

            boolean seHaEncontradoNuevaTorre = true;
            while (seHaEncontradoNuevaTorre) {
                List<Escaque> tmp = new ArrayList<>();
                seHaEncontradoNuevaTorre = false;
                for (Escaque escaqueTorre : escaquesTorres) {
                    for (Escaque escaqueTorreAdyacente : getEscaqueTorresAdyacentes(tablero, pieza, escaqueTorre.getPos())) {
                        if (!(escaquesTorres.contains(escaqueTorreAdyacente) || tmp.contains(escaqueTorreAdyacente))) {
                            tmp.add(escaqueTorreAdyacente);
                            seHaEncontradoNuevaTorre = true;
                        }
                    }
                }
                if (!tmp.isEmpty()) {
                    escaquesTorres.addAll(tmp);
                }
            }

            escaquesTorres = HabilidadTorre.this.ordenarEscaquesTorres(escaquesTorres, tablero, info.getValor());

            switch (info.getValor()) {
                case S -> escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false); //por conveniencia
                        for (int y = 0; y < tablero.filas; y++) {
                            if (y == escaqueTorre.getPos().y) {
                                break;
                            }
                            Point puntoFinal = new Point(escaqueTorre.getPos().x, y);
                            if (t.intentarComerPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            } else if (t.intentarMoverPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            }
                        }
                    });
                case E -> escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false); //por conveniencia
                        for (int x = tablero.columnas - 1; x >= 0; x--) {
                            if (x == escaqueTorre.getPos().x) {
                                break;
                            }
                            Point puntoFinal = new Point(x, escaqueTorre.getPos().y);
                            if (t.intentarComerPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            } else if (t.intentarMoverPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            }
                        }
                    });
                case N -> escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false); //por conveniencia
                        for (int y = tablero.filas - 1; y >= 0; y--) {
                            if (y == escaqueTorre.getPos().y) {
                                break;
                            }
                            Point puntoFinal = new Point(escaqueTorre.getPos().x, y);
                            if (t.intentarComerPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            } else if (t.intentarMoverPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            }
                        }
                    });
                case W -> escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false); //por conveniencia
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (x == escaqueTorre.getPos().y) {
                                break;
                            }
                            Point puntoFinal = new Point(x, escaqueTorre.getPos().y);
                            if (t.intentarComerPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            } else if (t.intentarMoverPieza(escaqueTorre.getPos(), puntoFinal).isPositive()) {
                                break;
                            }
                        }
                    });
            }
            for (Escaque escaque : escaquesTorres) {
                this.commonUsar(tablero, escaque.getPieza());
            }
        }

        protected List<Escaque> getEscaqueTorresAdyacentes(AbstractBoard tablero, P pieza, Point inicio) {
            return Arrays.<Escaque>asList(tablero.getEscaquesCercanos(tablero.getEscaque(inicio)).stream().filter((escaque) -> {
                return escaque.getPieza() instanceof Rook && pieza.getColor().equals(escaque.getPieza().getColor());
            }).toArray(Escaque[]::new));
        }

        protected List<Escaque> ordenarEscaquesTorres(List<Escaque> torres, AbstractBoard tablero, Direction dir) {
            List<Escaque> lista = new ArrayList<>();
            switch (dir) {
                case S -> {for (int y = 0; y < tablero.filas; y++) {
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }}
                case E -> {for (int x = tablero.columnas - 1; x >= 0; x--) {
                        for (int y = 0; y < tablero.filas; y++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }}
                case N ->{for (int y = tablero.filas - 1; y >= 0; y--) {
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }}
                case W ->{for (int x = 0; x < tablero.columnas; x++) {
                        for (int y = 0; y < tablero.filas; y++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }}
            }
            return lista;
        }
    }
}
