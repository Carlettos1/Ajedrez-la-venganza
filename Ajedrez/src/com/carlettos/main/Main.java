package com.carlettos.main;

import com.carlettos.game.display.main.MainMenu;
import com.carlettos.game.util.helper.LogHelper;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... args) {
        LogHelper.startLogger();
        var menu = new MainMenu();
        LogHelper.LOG.info(() -> "" + menu.getHeight());
    }
}
