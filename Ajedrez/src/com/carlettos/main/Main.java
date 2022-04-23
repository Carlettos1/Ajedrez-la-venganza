package com.carlettos.main;

import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.helper.ConfigHelper;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.main.MainMenu;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... a) {
        var menu = new MainMenu();
        System.out.println(ConfigHelper.getInstance());
        /*var board = Board.getDefaultInstance();
        
        BoardDisplay tv = new BoardDisplay(board);
        tv.mostrar();*/
    }
}
