package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing an asteroid type.
 */
public class AsteroidType extends Movable {
    private String name;
    private String type;

    /**
     *
     */

    public AsteroidType(){}

    public void setName(String name){this.name = name;}
    public void settype(String type){this.type = type;}

    public String getName(){return name;}
    public String getType(){return type;}

    public void update(double elapsedTime) {

        PointF newPosition = GraphicsUtils.moveObject(getPosition(), getBounds(), 100, Math.toRadians(getRotation()), elapsedTime).getNewObjPosition();

        GraphicsUtils.RicochetObjectResult result = GraphicsUtils.ricochetObject(newPosition, getBounds(), Math.toRadians(getRotation()), 3000, 3000);

        setPosition(result.getNewObjPosition());
        setRotation(Math.toDegrees(result.getNewAngleRadians()));

        if (getBounds().right > 3000 || getBounds().left < 0 || getBounds().top < 0 || getBounds().bottom > 3000) {
            setBounds(result.getNewObjBounds());
        }
        else {
            setBounds(generateBounds());
        }

    }

    @Override
    public void draw() {

        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(
                getImage()),
                ViewPort.converts(getPosition()).x,
                ViewPort.converts(getPosition()).y,
                (float)0, (float)1, (float)1, 255);

    }

}
