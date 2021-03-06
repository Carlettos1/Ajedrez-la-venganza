package ajedrez.carlettos.src.pieza.piezas.clasica;

import ajedrez.carlettos.src.pieza.base.Pieza;
import ajedrez.carlettos.src.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.escaque.Escaque;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.ActionResult;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Torre extends Pieza {

    public static final Habilidad HABILIDAD_TORRE = new Habilidad("Muro de Berlín",
            "\"Lanza\" todas las torres contiguas en una dirección y se detienen "
            + "si y solo si se cumple alguna de las siguientes condicioens: "
            + "1.- Alcanza el borde del tablero"
            + "2.- Comen una pieza enemiga"
            + "3.- Colisionan con una pieza aliada.",
            10,
            0,
            "La dirección cardinal hacia dónde se mueve el alfil (NESW).");

    public Torre(Color color) {
        super("Torre", "T", HABILIDAD_TORRE, color, EnumTipoPieza.ESTRUCTURA);
    }

    @Override
    public boolean canMover(TableroManager tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        if (final_.x != inicio.x && final_.y != inicio.y) {
            return false;
        }

        if (final_.x != inicio.x) { //se mueve el x
            int direccion = final_.x > inicio.x ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPieza()) {
                    return false;
                }
            }
        } else if (final_.y != inicio.y) { //se mueve en y
            int direccion = final_.y > inicio.y ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPieza()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Point inicio, Point final_) {
        if (getColor().equals(tablero.getEscaque(final_).getPieza().getColor())) {
            return false;
        }

        if (!tablero.getEscaque(final_).hasPieza()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        if (final_.x != inicio.x && final_.y != inicio.y) {
            return false;
        }

        if (final_.x != inicio.x) { //se mueve el x
            int direccion = final_.x > inicio.x ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPieza()) {
                    return false;
                }
            }
        } else if (final_.y != inicio.y) { //se mueve en y
            int direccion = final_.y > inicio.y ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPieza()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        if (cdActual > 0) {
            return new Par(false, "Habilidad en cd");
        }

        if (seHaMovidoEsteTurno()) {
            return new Par(false, "Se ha movido este turno");
        }

        if (informacionExtra.equals("N")
                || informacionExtra.equals("E")
                || informacionExtra.equals("S")
                || informacionExtra.equals("W")) {
            return new Par(true, "Todo ok");
        } else {
            return new Par(false, "Parámetros inválidos");
        }
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        List<Escaque> escaquesTorres = new ArrayList<>(getEscaqueTorresAdyacentes(tablero, inicio));
        escaquesTorres.add(tablero.getEscaque(inicio));

        boolean seHaEncontradoNuevaTorre = true;
        while (seHaEncontradoNuevaTorre) {
            List<Escaque> tmp = new ArrayList<>();
            seHaEncontradoNuevaTorre = false;
            for (Escaque escaqueTorre : escaquesTorres) {
                for (Escaque escaqueTorreAdyacente : getEscaqueTorresAdyacentes(tablero, escaqueTorre.getLocalizacion())) {
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
        
        switch(informacionExtra){
            case "S":
                escaquesTorres.forEach((escaqueTorre) -> {
                    System.out.println(escaqueTorre.getLocalizacion());
                    escaqueTorre.getPieza().setSeHaMovidoEsteTurno(false);
                    for (int y = 0; y < tablero.filas; y++) {
                        if(y == escaqueTorre.getLocalizacion().y){
                            escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Point puntoFinal = new Point(escaqueTorre.getLocalizacion().x, y);
                        if(tablero.comerPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
                            //comió
                            //TODO: cd y se ha movido y todo
                            break;
                        } else if (tablero.moverPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
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
                        if(x == escaqueTorre.getLocalizacion().x){
                            escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Point puntoFinal = new Point(x, escaqueTorre.getLocalizacion().y);
                        if(tablero.comerPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
                            //comió
                            //TODO: cd y se ha movido y todo
                            break;
                        } else if (tablero.moverPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
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
                        if(y == escaqueTorre.getLocalizacion().y){
                            escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Point puntoFinal = new Point(escaqueTorre.getLocalizacion().x, y);
                        if(tablero.comerPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
                            //comió
                            //TODO: cd y se ha movido y todo
                            break;
                        } else if (tablero.moverPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
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
                        if(x == escaqueTorre.getLocalizacion().y){
                            escaqueTorre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Point puntoFinal = new Point(x, escaqueTorre.getLocalizacion().y);
                        if(tablero.comerPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
                            //comió
                            //TODO: cd y se ha movido y todo
                            break;
                        } else if (tablero.moverPieza(escaqueTorre.getLocalizacion(), puntoFinal).x.equals(ActionResult.PASS)){
                            //movió
                            break;
                        }
                    }
                });
                break;
        }
        //TODO: cambiar cd y maná
    }

    private List<Escaque> getEscaqueTorresAdyacentes(TableroManager tablero, Point inicio) {
        return Arrays.<Escaque>asList(tablero.getEscaquesCercanos(tablero.getEscaque(inicio)).stream().filter((escaque) -> {
            return escaque.getPieza() instanceof Torre && getColor().equals(escaque.getPieza().getColor());
        }).toArray(Escaque[]::new));
    }

    private List<Escaque> ordenarEscaquesTorres(List<Escaque> torres, TableroManager tablero, String informacionExtra) {
        List<Escaque> lista = new ArrayList<>();
        switch (informacionExtra) {
            case "S":
                for (int y = 0; y < tablero.filas; y++) {
                    for (int x = 0; x < tablero.columnas; x++) {
                        if(torres.contains(tablero.getEscaque(x, y))){
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "E":
                for (int x = tablero.columnas - 1; x >= 0; x--) {
                    for (int y = 0; y < tablero.filas; y++) {
                        if(torres.contains(tablero.getEscaque(x, y))){
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "N":
                for (int y = tablero.filas - 1; y >= 0; y--) {
                    for (int x = 0; x < tablero.columnas; x++) {
                        if(torres.contains(tablero.getEscaque(x, y))){
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "W":
                for (int x = 0; x < tablero.columnas; x++) {
                    for (int y = 0; y < tablero.filas; y++) {
                        if(torres.contains(tablero.getEscaque(x, y))){
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
        }
        return lista;
    }
}
