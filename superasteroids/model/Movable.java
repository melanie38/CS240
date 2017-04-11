package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by Melanie on 21/05/16.
 * Objects that are visible, are positioned and can move
 */
public class Movable extends PositionedObject {

    private double rotation; // in degrees
    private RectF bounds;

    public Movable(PointF position) {
        super(position);
    }
    public Movable(double rotation) { this.rotation = rotation; }
    public Movable(){}

    public void setRotation(double rotation) {this.rotation = rotation;}
    public void setBounds(RectF bounds) { this.bounds = bounds; }

    public double getRotation() { return rotation; }
    public double getAngle(PointF input) {

        PointF movePointWorld = ViewPort.convertsViewToWorld(input);
        PointF ref = new PointF(movePointWorld.x - getPosition().x, movePointWorld.y - getPosition().y);

        rotation = 0;

        if ((double) ref.x != 0 && (double) ref.y != 0) {
            rotation = Math.atan2(ref.y, ref.x);
        } else if ((double) ref.x != 0) {
            rotation = ((ref.x > 0) ? 0 : Math.PI);
        } else if ((double) ref.y != 0) {
            rotation = ((ref.y > 0) ? (0.5 * Math.PI) : (1.5 * Math.PI));
        }

        setRotation((int)Math.toDegrees(rotation) + 90);

        return rotation;
    }
    public RectF getBounds() {
        if (bounds == null) {
           bounds = generateBounds();
        }
        return bounds; }

    public static PointF generateRandomPosition() {
        Random rand = new Random();
        float x = rand.nextInt(3000);
        float y = rand.nextInt(3000);

        return new PointF(x,y);
    }
    public PointF partPosition(Movable part, float scale) {

        PointF position;

        if (AsteroidGame.SINGLETON.getShip().getScreenPosition() != null) {
            position = AsteroidGame.SINGLETON.getShip().getScreenPosition();
        }
        else {
            position = ViewPort.converts(AsteroidGame.SINGLETON.getShip().getPosition());
        }

        int imageWidth = getImageWidth();
        int imageHeight = getImageHeight();
        int mainBodyWidth = Ship.getMainBody().getImageWidth();
        int mainBodyHeight = Ship.getMainBody().getImageHeight();

        PointF partAttach = new PointF();

        if (part instanceof Cannon) {
            partAttach = Ship.getMainBody().getCannonAttach();
        } else if (part instanceof ExtraPart) {
            partAttach = Ship.getMainBody().getExtraAttach();
        } else if (part instanceof Engine) {
            partAttach = Ship.getMainBody().getEngineAttach();
        }

        PointF partPosition = part.getPosition();

        float x = (partAttach.x - mainBodyWidth/2 + imageWidth/2 - partPosition.x) * scale;
        float y = (partAttach.y - mainBodyHeight/2 + imageHeight/2 - partPosition.y) * scale;

        PointF offset = new PointF(x,y);
        offset = GraphicsUtils.rotate(offset, Math.toRadians(AsteroidGame.SINGLETON.getShip().getRotation()));

        return GraphicsUtils.add(position, offset);

    }
    public RectF generateBounds() {

        if (getPosition() != null) {

            float left = getPosition().x - (float) getImageWidth() / 2;
            float top = getPosition().y - (float) getImageHeight() / 2;
            float right = getPosition().x + (float) getImageWidth() / 2;
            float bottom = getPosition().y + (float) getImageHeight() / 2;

            return new RectF(left, top, right, bottom);
        }
        else {
            return null;
        }

    }
    public double generateAngle() {
        Random random = new Random();

        return random.nextInt(360);
    }

    public boolean touch(AsteroidType asteroid) {

        if (this.getBounds().intersect(asteroid.getBounds())) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public void update() {
        setBounds(generateBounds());
    }

    @Override
    public void clear() {

        rotation = 0;
        bounds = null;

    }

}
