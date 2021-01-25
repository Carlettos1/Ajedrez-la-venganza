# Ajedrez-la-venganza

Mi ajedrez, de 2 jugadores, multijugador local.

El ajedrez se juega como un ajedrez normal por excepción de todo, a las piezas se les permite usar habilidades que tienen costos de maná asociados. Un ejemplo de esto es la coronación del peón, que funciona igual que siempre, salvo que no es automático; debe efectuarse la coronación, consumiendo un movimiento.

El tablero es de 20x21, por lo tanto, hay posibilidades de aumentar los movimientos por turnos, además de haber +20 nuevas piezas.

También se añaden estructuras, que son, escencialmente, piezas que no se pueden mover. Tienen más utilidades, como impedir que una pieza sea comida si tiene cierta estructura en la misma casilla.

Además no pueden faltar las cartas, que tienen efectos diversos, desde dar maná o movimientos adicionales, o el de colocar una pieza directamente en un espacio del tablero.

Muy bien, review de clases.

Primero, está el TableroManager que sirve como espacio de juego. Esta clase tiene instancias de Escaque, que son las casillas del tablero.

Cada escaque contiene una pieza y una estructura, aunque dependiendo del tipo de pieza y estructura puede que solo haya una de las 2.

Las piezas tienen 4 métodos importantes, canComer, canMover, canUsarHabilidad y habilidad. Los que empiezan por "can" verifica si se puede hacer la acción que procede, y la habilidad simplemente es la ejecución de la habilidad. Los métodos de comer y mover quedan relegado al tablero.

Luego, Está la mano, que son las cartas que tiene un Jugador. Poco más que no sea obvio se puede decir.

Queda como to-do el añadir el reloj, que sea capaz de controlar eventos y movimientos, además de lo obvio, añadir todas las piezas y que se pueda ver.
