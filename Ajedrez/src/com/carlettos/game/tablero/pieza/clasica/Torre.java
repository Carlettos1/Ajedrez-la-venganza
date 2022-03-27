package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronTorre;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Torre extends PiezaClasica implements PatronTorre{

    public static final Habilidad<Torre> HABILIDAD_TORRE = new HabilidadTorre<>();

    public Torre(Color color) {
        super("Torre", "T", HABILIDAD_TORRE, color, Tipo.ESTRUCTURA);
    }

    public static class HabilidadTorre<P extends Pieza> extends Habilidad<P> {

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
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }

            //TODO: cambiar para que no sea case sensitive
            if (informacionExtra.equals("N")
                    || informacionExtra.equals("E")
                    || informacionExtra.equals("S")
                    || informacionExtra.equals("W")) {
                return ActionResult.PASS;
            } else {
                return ActionResult.FAIL;
            }
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            List<Escaque> escaquesTorres = new ArrayList<>(getEscaqueTorresAdyacentes(tablero, pieza, inicio));
            escaquesTorres.add(tablero.getEscaque(inicio));

            boolean seHaEncontradoNuevaTorre = true;
            while (seHaEncontradoNuevaTorre) {
                List<Escaque> tmp = new ArrayList<>();
                seHaEncontradoNuevaTorre = false;
                for (Escaque escaqueTorre : escaquesTorres) {
                    for (Escaque escaqueTorreAdyacente : getEscaqueTorresAdyacentes(tablero, pieza, escaqueTorre.getLocalizacion())) {
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

            escaquesTorres = ordenarEscaquesTorres(escaquesTorres, tablero, informacionExtra);

            switch (informacionExtra) {
                case "S":
                    escaquesTorres.forEach((escaqueTorre) -> {
                        System.out.println(escaqueTorre.getLocalizacion());
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false);
                        for (int y = 0; y < tablero.filas; y++) {
                            if (y == escaqueTorre.getLocalizacion().y) {
                                escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                                break;
                            }
                            Point puntoFinal = new Point(escaqueTorre.getLocalizacion().x, y);
                            if (tablero.intentarComerPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //comió
                                //TODO: cd y se ha movido y todo
                                break;
                            } else if (tablero.intentarMoverPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //movió
                                break;
                            }
                        }
                    });
                    break;
                case "E":
                    escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false);
                        for (int x = tablero.columnas - 1; x >= 0; x--) {
                            if (x == escaqueTorre.getLocalizacion().x) {
                                escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                                break;
                            }
                            Point puntoFinal = new Point(x, escaqueTorre.getLocalizacion().y);
                            if (tablero.intentarComerPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //comió
                                //TODO: cd y se ha movido y todo
                                break;
                            } else if (tablero.intentarMoverPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //movió
                                break;
                            }
                        }
                    });
                    break;
                case "N":
                    escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false);
                        for (int y = tablero.filas - 1; y >= 0; y--) {
                            if (y == escaqueTorre.getLocalizacion().y) {
                                escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                                break;
                            }
                            Point puntoFinal = new Point(escaqueTorre.getLocalizacion().x, y);
                            if (tablero.intentarComerPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //comió
                                //TODO: cd y se ha movido y todo
                                break;
                            } else if (tablero.intentarMoverPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //movió
                                break;
                            }
                        }
                    });
                    break;
                case "W":
                    escaquesTorres.forEach((escaqueTorre) -> {
                        escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false);
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (x == escaqueTorre.getLocalizacion().y) {
                                escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                                break;
                            }
                            Point puntoFinal = new Point(x, escaqueTorre.getLocalizacion().y);
                            if (tablero.intentarComerPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //comió
                                //TODO: cd y se ha movido y todo
                                break;
                            } else if (tablero.intentarMoverPieza(escaqueTorre.getLocalizacion(), puntoFinal).equals(ActionResult.PASS)) {
                                //movió
                                break;
                            }
                        }
                    });
                    break;
            }
            //TODO: cambiar cd y maná
        }

        protected List<Escaque> getEscaqueTorresAdyacentes(Tablero tablero, P pieza, Point inicio) {
            return Arrays.<Escaque>asList(tablero.getEscaquesCercanos(tablero.getEscaque(inicio)).stream().filter((escaque) -> {
                return escaque.getPieza() instanceof Torre && pieza.getColor().equals(escaque.getPieza().getColor());
            }).toArray(Escaque[]::new));
        }

        protected List<Escaque> ordenarEscaquesTorres(List<Escaque> torres, Tablero tablero, String informacionExtra) {
            List<Escaque> lista = new ArrayList<>();
            switch (informacionExtra) {
                case "S":
                    for (int y = 0; y < tablero.filas; y++) {
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }
                    break;
                case "E":
                    for (int x = tablero.columnas - 1; x >= 0; x--) {
                        for (int y = 0; y < tablero.filas; y++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }
                    break;
                case "N":
                    for (int y = tablero.filas - 1; y >= 0; y--) {
                        for (int x = 0; x < tablero.columnas; x++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }
                    break;
                case "W":
                    for (int x = 0; x < tablero.columnas; x++) {
                        for (int y = 0; y < tablero.filas; y++) {
                            if (torres.contains(tablero.getEscaque(x, y))) {
                                lista.add(tablero.getEscaque(x, y));
                            }
                        }
                    }
                    break;
            }
            return lista;
        }
    }
}
