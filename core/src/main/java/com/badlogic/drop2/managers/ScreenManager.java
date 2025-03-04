package com.badlogic.drop2.managers;

import com.badlogic.drop2.*;
import com.badlogic.drop2.screens.GameScreen;
import com.badlogic.drop2.screens.MenuScreen;
import com.badlogic.drop2.screens.OptionsScreen;
import com.badlogic.drop2.screens.PauseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenManager {

    public enum ScreenType {
        MAIN_MENU,
        GAME,
        OPTIONS,
        PAUSE
    }

    private static Game game;

    public static void initialize(Game gameInstance) {
        game = gameInstance;
    }

    public static void setScreen(ScreenType type, Object... params) {
        Screen screen = null;
        switch (type) {
            case MAIN_MENU:
                screen = new MenuScreen((Main) game);
                break;
            case GAME:
                screen = new GameScreen((Main) game);
                break;
            case OPTIONS:
                screen = new OptionsScreen((Main) game);
                break;
            case PAUSE:
                screen = new PauseScreen((Main) game, (int) params[0]);
                break;
        }
        if (screen != null) {
            game.setScreen(screen);
        }
    }
}
