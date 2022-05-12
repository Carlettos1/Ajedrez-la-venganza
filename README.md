# Ajedrez-la-venganza

WIP readme.

Uses: Gson 2.9.0

### tick cycle

    1. Board
    2. Piece
    3. Clock

The board part does nothing.

The piece part is in a 2-for loop going though all the chess-board-matrix.
Piece::tick

    1. isMoved = false;
    for each effect:
        2. effect::tick
        3. effect::onTick
        4. effect::onExpire (only if applies)
        5. remove if expired

The clock part is simple.

    1. turn++;
    2. movements = 0;
    for each event:
        3. event::tick
        4. event::act (only if applies)
        5. remove if acted
    6. fire up all listeners (wip)






