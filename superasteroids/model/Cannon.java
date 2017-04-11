package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import java.io.IOException;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing a cannon part of the ship.
 */
public class Cannon extends Movable {

    private PointF emitPoint;
    private String attackImage;
    private int attackImageWidth;
    private int attackImageHeight;
    private String attackSound;
    private int damage;

    private ArrayList<Projectile> listOfProjectiles;

    public Cannon() {
        emitPoint = new PointF();
        attackImage = new String();
        attackImageWidth = 0;
        attackImageHeight = 0;
        attackSound = new String();
        damage = 0;

        listOfProjectiles = new ArrayList<>();
    }

    public void setEmitPoint(PointF emitPoint) {
        this.emitPoint = emitPoint;
    }

    public void setAttackImage(String attackImage) {
        this.attackImage = attackImage;
    }

    public void setAttackImageWidth(int attackImageWidth) { this.attackImageWidth = attackImageWidth; }

    public void setAttackImageHeight(int attackImageHeight) { this.attackImageHeight = attackImageHeight; }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void update(double elapsedTime) {

        if (InputManager.firePressed) {
            listOfProjectiles.add(new Projectile(AsteroidGame.SINGLETON.getShip().getRotation()));
            try {
                ContentManager.getInstance().playSound(ContentManager.getInstance().loadSound(attackSound), 1, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Projectile> toDelete = new ArrayList<>();

        for (Projectile proj : listOfProjectiles) {
            proj.update(elapsedTime);

            if (proj.getPosition().x < 0 || proj.getPosition().x > 3000 || proj.getPosition().y < 0 || proj.getPosition().y > 3000) {
                toDelete.add(proj);
            }
        }

        for (Projectile proj : toDelete) {
            listOfProjectiles.remove(proj);
        }

        setBounds(generateBounds());

    }

    @Override
    public void draw() {
        int rotation = (int)AsteroidGame.SINGLETON.getShip().getRotation();
        float scale = Ship.getScale();
        PointF newCoord = partPosition(AsteroidGame.SINGLETON.getShip().getCannon(), scale);

        DrawingHelper.drawImage(ContentManager.getInstance().loadImage(getImage()), newCoord.x, newCoord.y, rotation, scale, scale, 255);

        for (Projectile proj : listOfProjectiles) {

            proj.draw();

        }
    }

    public PointF getEmitPoint(){return emitPoint;}

    public String getAttackImage(){return attackImage;}

    public String getAttackSOUND(){return attackSound;}

    public int getAttackImageWidth(){return attackImageWidth;}

    public int getAttackImageHeight(){return attackImageHeight;}

    public int getDamage(){return damage;}

    public ArrayList<Projectile> getListOfProjectiles() { return listOfProjectiles; }
}
