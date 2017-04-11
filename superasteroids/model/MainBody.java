package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing a main body part of the ship.
 */
public class MainBody extends Movable {

    private PointF cannonAttach;
    private PointF engineAttach;
    private PointF extraAttach;

    /**
     *
     */
    public MainBody() {
        cannonAttach = new PointF();
        engineAttach = new PointF();
        extraAttach = new PointF();
    }

    public void setCannonAttach(PointF cannonAttach) { this.cannonAttach = cannonAttach; }
    public void setEngineAttach(PointF engineAttach) {
        this.engineAttach = engineAttach;
    }
    public void setExtraAttach(PointF extraAttach) {
        this.extraAttach = extraAttach;
    }

    public PointF getCannonAttach(){return cannonAttach;}
    public PointF getEngineAttach(){return engineAttach;}
    public PointF getExtraAttach(){return extraAttach;}

    @Override
    public void update() {
        setBounds(generateBounds());

    }

    @Override
    public void draw(){


        int rotation = (int)AsteroidGame.SINGLETON.getShip().getRotation();
        float scale = Ship.getScale();
        PointF position;

        if (AsteroidGame.SINGLETON.getShip().getScreenPosition() != null) {
            position = AsteroidGame.SINGLETON.getShip().getScreenPosition();
        }
        else {
            position = ViewPort.converts(AsteroidGame.SINGLETON.getShip().getPosition());
        }


        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(getImage()), position.x, position.y, rotation, scale, scale, 255);
    }

    @Override
    public void clear() {
        cannonAttach = null;
        engineAttach = null;
        extraAttach = null;
    }

}
