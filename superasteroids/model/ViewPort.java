package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Melanie on 28/05/16.
 * Creates the game view port
 */
public class ViewPort {

    private static int viewPortWidth;
    private static int viewPortHeight;
    private static PointF viewPortPosition; // position of the upper Left corner of the viewPort in the universe
    private static Ship ship;
    private static Universe universe;

    public ViewPort(){
        ship = AsteroidGame.SINGLETON.getShip();
        universe = new Universe();
    }

    public static Universe getUniverse() {return universe;}

    public static PointF converts(PointF worldCoord) {
        PointF screenPosition = new PointF();

        screenPosition.x = worldCoord.x - viewPortPosition.x;
        screenPosition.y = worldCoord.y - viewPortPosition.y;

        return screenPosition;
    }
    public static PointF convertsViewToWorld(PointF viewCoord) {
        PointF worldPosition = new PointF();

        worldPosition.x = viewCoord.x + viewPortPosition.x;
        worldPosition.y = viewCoord.y + viewPortPosition.y;

        return worldPosition;
    }

    public static void update(){
        universe.setOrigin();
    }

    public static PointF getViewPortPosition() {
        return viewPortPosition;
    }

    public void draw(){

        viewPortWidth = DrawingHelper.getGameViewWidth();
        viewPortHeight = DrawingHelper.getGameViewHeight();

        PointF shipPosition = ship.getPosition(); //world position, to change later

        float x = shipPosition.x - viewPortWidth/2;
        float y = shipPosition.y - viewPortHeight/2;

        if (shipPosition != null) {

            if (shipPosition.x > 3000 - viewPortWidth/2) {
                x = 3000 - viewPortWidth;
            }
            if (shipPosition.x < 0 + viewPortWidth/2) {
                x = 0;
            }

            if (shipPosition.y > 3000 - viewPortHeight/2) {
                y = 3000 - viewPortHeight;
            }
            if (shipPosition.y < 0 + viewPortHeight/2) {
                y = 0;
            }

        }

        viewPortPosition = new PointF(x,y);

        universe.draw();
    }
}
