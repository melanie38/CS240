package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Melanie on 07/06/16.
 */
public class Projectile extends Movable {

    private Ship ship;
    private Cannon cannon;
    private double rotation;

    public Projectile(double rotation) {
        super(rotation);

        this.rotation = rotation;

        ship = AsteroidGame.SINGLETON.getShip();
        cannon = AsteroidGame.SINGLETON.getShip().getCannon();
        setImage(cannon.getAttackImage());
        setImageWidth(cannon.getAttackImageWidth());
        setImageHeight(cannon.getAttackImageHeight());

    }

    public void update(double elapsedTime) {

        if (getPosition() == null) {

            float scale = ship.getScale();
            float rotation = (float) ship.getRotation();

            PointF offset = GraphicsUtils.subtract(cannon.getEmitPoint(), new PointF(getImageWidth() / 2, getImageHeight() / 2));
            offset = GraphicsUtils.scale(offset, scale);
            offset = GraphicsUtils.rotate(offset, Math.toRadians(rotation));

            setPosition(ViewPort.convertsViewToWorld(GraphicsUtils.add(partPosition(cannon, scale), offset)));
        }

        setBounds(generateBounds());

        setPosition(

                GraphicsUtils.moveObject(
                        getPosition(),
                        getBounds(),
                        1500,
                        Math.toRadians(rotation) - Math.PI/2,
                        elapsedTime
                ).getNewObjPosition()
        );

        setBounds(generateBounds());

    }

    public void draw() {
        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(this.getImage()), ViewPort.converts(getPosition()).x, ViewPort.converts(getPosition()).y, (float) rotation, ship.getScale(), ship.getScale(), 255);
    }
}
