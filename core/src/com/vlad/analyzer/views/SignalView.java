package com.vlad.analyzer.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vlad.analyzer.sound.SignalData;
import com.vlad.analyzer.sound.SoundData;

public class SignalView extends View {
    private final Game program;
    private byte[] soundData;
    private Thread soundCapturing;
    private ShapeRenderer shapeRenderer;
    private SignalData signalData;

    public SignalView(Game program) {
        this.program = program;
    }

    @Override
    public void show() {
        initBatchStageCameraViewport();
        shapeRenderer = new ShapeRenderer();
        soundData = new byte[SoundData.BUFFER_SIZE];
        signalData = new SignalData(soundData);

        Texture back = new Texture(Gdx.files.internal("back.jpg"));
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)), new TextureRegionDrawable(new TextureRegion(back)));
        backButton.setSize(140, 70);
        backButton.setPosition(WIDTH - backButton.getWidth(), HEIGHT - backButton.getHeight());
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                program.setScreen(new Menu(program));
                dispose();
            }
        });
        stage.addActor(backButton);

        SignalData.ACTIVE = true;
        soundCapturing = new Thread(signalData);
        soundCapturing.start();
    }

    @Override
    protected void viewDispose() {
        signalData.closeLine();
        SignalData.ACTIVE = false;
    }

    @Override
    protected void draw(float delta) {
        drawBytes();
    }

    private void drawBytes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < soundData.length; i++) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(i, soundData[i] + HEIGHT/2, 2);
        }
        shapeRenderer.end();
    }
}
