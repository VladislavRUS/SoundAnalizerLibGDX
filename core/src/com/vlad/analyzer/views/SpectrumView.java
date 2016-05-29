package com.vlad.analyzer.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vlad.analyzer.model.Rect;
import com.vlad.analyzer.sound.SpectrumData;

import java.util.HashMap;
import java.util.Map;

public class SpectrumView extends View {
    private final Game program;
    private Map<Float, Float> soundData;
    private Map<Float, Rect> rectangles;
    private ShapeRenderer shapeRenderer;
    private Thread soundCapturing;
    private SpectrumData data;

    public SpectrumView(Game program){
        this.program = program;
    }

	@Override
	public void show () {
        initBatchStageCameraViewport();
		shapeRenderer = new ShapeRenderer();
		soundData = new HashMap<>();
		rectangles = new HashMap<>();

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

        int j = 30;
        for(float i = 1, plus = WIDTH / 342; j < SpectrumData.BUFFER_SIZE; i += plus, j++){
			float freq = j * SpectrumData.SAMPLE_RATE / SpectrumData.BUFFER_SIZE;
			if(freq > SpectrumData.MAX_FREQ)
				break;
            rectangles.put(freq, new Rect(i, 10));
		}

        SpectrumData.ACTIVE = true;
		data = new SpectrumData(soundData);
        soundCapturing = new Thread(data);
		soundCapturing.start();

        //Set magnitude
        new Thread(()->{
            while (true) {
                synchronized (soundData){
                    soundData.forEach((freq, mag) -> rectangles.get(freq).setMagnitude(mag));
                    soundData.notify();
                }
            }
        }).start();
	}


    @Override
    protected void draw(float delta) {
        drawRectList();
        updateRectList(delta);
    }

    public void drawRectList(){
		rectangles.forEach((freq, rect) -> rect.draw(shapeRenderer));
	}

	public void updateRectList(float delta){
		rectangles.forEach((freq, rect) -> rect.update(delta));
	}

    @Override
    protected void viewDispose() {
        data.closeLine();
        SpectrumData.ACTIVE = false;
    }
}
