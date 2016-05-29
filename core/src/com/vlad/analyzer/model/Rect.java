package com.vlad.analyzer.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rect {
    private static final double DOWN_SPEED = 0.70f;
    private static final int WIDTH = 5;
    private float freq;
    private float magnitude;

    public float getFreq() {
        return freq;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public Rect(float freq, float magnitude) {
        this.freq = freq;
        this.magnitude = magnitude;
    }

    public void update(float delta){
        if(magnitude > 0)
            if(magnitude > 1)
                magnitude -= magnitude * DOWN_SPEED * delta;
            else
                magnitude = 0;
    }

    public void setMagnitude(float magnitude) {
        if(magnitude > this.magnitude)
            this.magnitude = magnitude;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(freq, 0, WIDTH, magnitude, Color.RED, Color.BLACK, Color.BLACK, Color.BLACK);
        shapeRenderer.end();
    }
}
