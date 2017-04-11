package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Melanie on 20/06/16.
 */
public class MiniMap {

    private PointF ship;
    private ArrayList<PointF> asteroids;

    public MiniMap(){
        asteroids = new ArrayList<>();
    }

    public PointF converts(PointF world) {
        PointF map = new PointF();

        map.x = 1 + world.x * 200 / 3000;
        map.y = 1 + world.y * 100 / 3000;

        return map;
    }

    public void update() {
        PointF shipWorld = AsteroidGame.SINGLETON.getShip().getPosition();
        ship = converts(shipWorld);

        ArrayList<AsteroidType> asteroidsWorld = AsteroidGame.SINGLETON.getCurrentLevel().getLevelAsteroids();

        if (asteroidsWorld != null) {
            for (int i = 0; i < asteroidsWorld.size(); i++) {
                PointF asteroid = converts(asteroidsWorld.get(i).getPosition());
                asteroids.add(asteroid);
            }
        }

    }
    public void draw() {
        DrawingHelper.drawRectangle(new Rect(0,0,202,102), 255, 255);
        DrawingHelper.drawFilledRectangle(new Rect(1,1,202,102), 0, 255);

        if (ship != null) {
            DrawingHelper.drawPoint(ship, 2, -16711936, 255);
        }
        if (asteroids != null) {
            for (PointF toDraw : asteroids) {
                DrawingHelper.drawPoint(toDraw, 4, -65536, 255);
            }
            asteroids.clear();
        }

    }
}
