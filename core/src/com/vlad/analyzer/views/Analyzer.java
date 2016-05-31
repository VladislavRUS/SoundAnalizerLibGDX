package com.vlad.analyzer.views;

import com.badlogic.gdx.Game;

public class Analyzer extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this));
    }
}
