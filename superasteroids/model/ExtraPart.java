package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing an extra part of the ship.
 */
public class ExtraPart extends Movable {

    public ExtraPart(){}

    @Override
    public void draw(){
        int rotation = (int)AsteroidGame.SINGLETON.getShip().getRotation();
        float scale = Ship.getScale();
        PointF newCoord = partPosition(AsteroidGame.SINGLETON.getShip().getExtraPart(), scale);

        DrawingHelper.drawImage((ContentManager.getInstance().loadImage(getImage())), newCoord.x, newCoord.y, rotation, scale, scale, 255);
    }

}

