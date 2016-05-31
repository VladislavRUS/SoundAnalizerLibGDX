package com.vlad.analyzer.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Menu extends AbstractView {
    private final Game program;

    public Menu(Game program){
        this.program = program;
    }

    @Override
    public void show() {
        initBatchStageCameraViewport();

        //Spectrum button
        Texture spectrumTexture = new Texture(Gdx.files.internal("spectrum.jpg"));
        ImageButton spectrum = new ImageButton(new TextureRegionDrawable(new TextureRegion(spectrumTexture)), new TextureRegionDrawable(new TextureRegion(spectrumTexture)));
        spectrum.setSize(200, 100);
        spectrum.setPosition(WIDTH/2 - spectrum.getWidth()/2, HEIGHT/2);
        spectrum.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                program.setScreen(new SpectrumView(program));
                dispose();
            }
        });
        stage.addActor(spectrum);

        //Signal button
        Texture signalTexture = new Texture(Gdx.files.internal("signal.jpg"));
        ImageButton signal = new ImageButton(new TextureRegionDrawable(new TextureRegion(signalTexture)), new TextureRegionDrawable(new TextureRegion(signalTexture)));
        signal.setSize(200, 100);
        signal.setPosition(WIDTH/2 - signal.getWidth()/2, HEIGHT/2 - spectrum.getHeight());
        signal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                program.setScreen(new SignalView(program));
                dispose();
            }
        });
        stage.addActor(signal);
    }

    @Override
    protected void viewDispose() {
    }

    @Override
    protected void draw(float delta) {
    }
}
