package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing a level background object.
 */
public class LevelObject extends PositionedObject {

    private int objectId;
    private float scale;
    private Objects object;

    public LevelObject(){}

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
    public void setScale(double scale) {
        this.scale = (float) scale;
    }
    public void setObject (Objects object) {this.object = object;}

    public int getObjectId(){return objectId;}
    public float getScale(){return scale;}

    @Override
    public void update() {

    }

    @Override
    public void draw() {

        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(
                object.getPath()),
                ViewPort.converts(getPosition()).x,
                ViewPort.converts(getPosition()).y,
                0, scale, scale, 255);
    }
}

