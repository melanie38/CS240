package edu.byu.cs.superasteroids.base;

import android.graphics.Rect;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MiniMap;
import edu.byu.cs.superasteroids.model.Projectile;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.model.AsteroidGame;
import edu.byu.cs.superasteroids.model.ViewPort;

/**
 * Created by Melanie on 27/05/16.
 */
public class MyGameDelegate implements IGameDelegate {

    Ship ship;
    Level level;
    ViewPort viewPort;
    MiniMap miniMap;
    boolean drawCircle;
    int counter;


    public MyGameDelegate(){
        ship = AsteroidGame.SINGLETON.getShip();
        miniMap = new MiniMap();
        drawCircle = false;
        counter = 0;
    }

    public void checkCollisions() {

        ArrayList<Projectile> projectiles = ship.getCannon().getListOfProjectiles();
        ArrayList<AsteroidType> asteroids = level.getLevelAsteroids();

        ArrayList<Integer> asteroToDelete = new ArrayList<>();
        ArrayList<Integer> projToDelete = new ArrayList<>();
        int j = -1;
        int k = -1;

        for (AsteroidType astero : asteroids) {
            j++;
            for (Projectile proj : projectiles) {
                k++;

                if (proj.touch(astero)) {
                    asteroToDelete.add(j);
                    projToDelete.add(k);
                }
            }
            k = 0;

            ship.setBounds(ship.generateBounds());

            if (ship.touch(astero)) {
                drawCircle = true;
                counter = 150;
            }
        }

        if (asteroToDelete.size() != 0) {
            for (int i : asteroToDelete) {
                if (i < asteroids.size()) {
                    asteroids.remove(i);
                }
            }
        }
/*
        if (projToDelete.size() != 0) {
            for (int i : projToDelete) {
                projectiles.remove(i);
            }
        }
        */

    }

    @Override
    public void update(double elapsedTime) {
        ship.update(elapsedTime);
        viewPort.update();
        level.update(elapsedTime);
        miniMap.update();
        checkCollisions();
        if (counter > 0) {
            counter --;
        }
        if (counter == 0) {
            drawCircle = false;
        }
    }

    @Override
    public void loadContent(ContentManager content) {
        loadCurrentLevel();
        loadViewPort();
    }

    public void loadCurrentLevel() {
        AsteroidGame.SINGLETON.setCurrentLevel(AsteroidGame.SINGLETON.getListOfLevels().get(0));
        AsteroidGame.SINGLETON.populateLevelObject();
        AsteroidGame.SINGLETON.populateLevelAsteroid();
        level = AsteroidGame.SINGLETON.getCurrentLevel();
    }
    public void loadViewPort() {

        //ship.setPosition(new PointF(level.getWidth()/2, level.getHeight()/2));

        viewPort = new ViewPort();
    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {

        //ship.setScreenPosition(new PointF(DrawingHelper.getGameViewWidth()/2, DrawingHelper.getGameViewHeight()/2));

        viewPort.draw();

        level.draw();

        ship.draw();

        miniMap.draw();

        if (drawCircle == true) {
            DrawingHelper.drawFilledCircle(ViewPort.converts(ship.getPosition()), (float)100, -16711936, 100);
        }

        DrawingHelper.drawRectangle(new Rect((int)ship.getBounds().left, (int)ship.getBounds().top, (int)ship.getBounds().right, (int)ship.getBounds().bottom), -16711936, 255);

    }
}
