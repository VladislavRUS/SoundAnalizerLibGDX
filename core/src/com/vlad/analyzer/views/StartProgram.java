package com.vlad.analyzer.views;

import com.badlogic.gdx.Game;
import com.vlad.analyzer.views.Menu;

public class StartProgram extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this));
    }
}
