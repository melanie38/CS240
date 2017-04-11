package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Melanie on 28/05/16.
 */
public class Universe {

    private static int width;
    private static int height;
    private static int viewPortWidth;
    private static int viewPortHeight;
    private static AsteroidGame game;
    private static PointF origin;
    private static PointF shipWorldPosition;
    private static Rect source; // portion of the image to draw
    private static Rect dest;

    public Universe(){
        game = AsteroidGame.SINGLETON;
        width = game.getCurrentLevel().getWidth();
        height = game.getCurrentLevel().getHeight();
        shipWorldPosition = game.getShip().getPosition();
    }

    public static void setOrigin() { origin = ViewPort.converts(new PointF(0,0));}

    public static void calculRect() {

        source = new Rect(0, 0, 2048, 2048);
        dest = new Rect(0,0,3000,3000);

        int left = 2048*(int)ViewPort.getViewPortPosition().x/width;
        int top = 2048*(int)ViewPort.getViewPortPosition().y/height;
        int right = 2048*((int)ViewPort.getViewPortPosition().x + viewPortWidth)/width;
        int bottom = 2048*((int)ViewPort.getViewPortPosition().y + viewPortHeight)/height;

        source = new Rect(left, top, right, bottom);
    }

    public static void update() {

    }

    public void draw() {

        viewPortWidth = DrawingHelper.getGameViewWidth();
        viewPortHeight = DrawingHelper.getGameViewHeight();

        calculRect();

        DrawingHelper.drawImage(ContentManager.getInstance().loadImage("images/space.bmp"), source, null);
    }
}
