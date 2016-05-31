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

public class SignalView extends AbstractView {
    private ShapeRenderer shapeRenderer;
    private SignalData signalData;
    private final Game program;
    private byte[] soundData;

    float plus = WIDTH / SignalData.SIGNAL_DATA_SIZE;
    float height = HEIGHT/2;
    float j = 0;

    public SignalView(Game program) {
        this.program = program;
    }

    @Override
    public void show() {
        initBatchStageCameraViewport();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);
        soundData = new byte[SignalData.SIGNAL_DATA_SIZE];
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

        signalData.setActive(true);
        Thread soundCapturing = new Thread(signalData);
        soundCapturing.start();
    }

    @Override
    protected void viewDispose() {
        signalData.closeLine();
        signalData.setActive(false);
    }

    @Override
    protected void draw(float delta) {
        drawBytes();
    }

    private void drawBytes() {
        j = 0;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < SignalData.SIGNAL_DATA_SIZE; i++, j += plus) {
            shapeRenderer.circle(j, soundData[i] + height, 1.5f);
        }
        shapeRenderer.end();
    }
}
