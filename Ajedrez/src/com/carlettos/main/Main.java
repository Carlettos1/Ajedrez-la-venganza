package com.carlettos.main;

import com.carlettos.game.display.main.MainMenu;
import com.carlettos.game.util.helper.LogManager;

/**
 *
 * @author Carlos
 */
public class Main {

    @SuppressWarnings("unused")
    public static void main(String... args) {
        LogManager.startLogger();
        var menu = new MainMenu();
    }
}
