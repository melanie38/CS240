package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing an engine part of the ship.
 */
public class Engine extends Movable {
    private int baseSpeed;
    private int baseTurnRate;

    public Engine(){}

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }

    public int getBaseSpeed(){return baseSpeed;}
    public int getBaseTurnRate(){return baseTurnRate;}

    @Override
    public void draw() {
        int rotation = (int)AsteroidGame.SINGLETON.getShip().getRotation();
        float scale = Ship.getScale();
        PointF newCoord = partPosition(AsteroidGame.SINGLETON.getShip().getEngine(), scale);

        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(getImage()), newCoord.x, newCoord.y, rotation, scale, scale, 255);
    }

}
