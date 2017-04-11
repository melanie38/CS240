package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;


/**
 * Created by Melanie on 21/05/16.
 * Objects that are visible and have a position.
 */
public class PositionedObject extends Visible {

    private PointF worldPosition; // to change later
    private PointF screenPosition;

    public PositionedObject(PointF worldPosition) {
        this.worldPosition = worldPosition;
    }

    public PositionedObject(){}

    public void setPosition(PointF worldPosition) {
        this.worldPosition = worldPosition;
    }
    public void setScreenPosition(PointF screenPosition) { this.screenPosition = screenPosition; }

    public PointF getPosition(){return worldPosition;}
    public PointF getScreenPosition() { return screenPosition; }

    @Override
    public void update(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(worldPosition.x + "," + worldPosition.y);
        return sb.toString();
    }

    @Override
    public void clear() {

        worldPosition = null;
        screenPosition = null;

    }
}
