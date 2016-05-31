package com.vlad.analyzer.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractView extends ScreenAdapter{
    protected static final float WIDTH = 1200;
    protected static final float HEIGHT = 600;

    protected SpriteBatch batch;
    protected Viewport viewport;
    protected Camera camera;
    protected Stage stage;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    public void initBatchStageCameraViewport(){
        camera = new OrthographicCamera();
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        viewport = new ScreenViewport(camera);
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);

        batch.begin();
        batch.end();

        draw(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose(){
        super.dispose();
        stage.dispose();
        viewDispose();
    }

    protected abstract void viewDispose();

    protected abstract void draw(float delta);
}
