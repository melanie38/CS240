package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by Melanie on 25/05/16.
 */
public class Ship extends Movable {

    private static MainBody mainBody;
    private static Cannon cannon;
    private static Engine engine;
    private static ExtraPart extraPart;
    private static PowerCore powerCore;
    private static float scale;
    private static PointF newPosition;

    public Ship(MainBody mainBody, Cannon cannon, Engine engine, ExtraPart extraPart, PowerCore powerCore) {

        this.mainBody = mainBody;
        this.cannon = cannon;
        this.engine = engine;
        this.extraPart = extraPart;
        this.powerCore = powerCore;
        scale = (float)0.25;
        setPosition(new PointF(3000/2,3000/2));
    }

    public Ship(PointF worldPosition){
        super(worldPosition);
        scale = (float)0.25;
    }

    public Ship(){
        scale = (float)0.25;
        setPosition(new PointF(3000/2,3000/2));
    }

    public static MainBody getMainBody() {
        return mainBody;
    }
    public static Cannon getCannon() {
        return cannon;
    }
    public static Engine getEngine() {
        return engine;
    }
    public static ExtraPart getExtraPart() {
        return extraPart;
    }
    public static PowerCore getPowerCore() {
        return powerCore;
    }
    public static float getScale() { return scale; }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }
    public void setCannon(Cannon cannon) { this.cannon = cannon; }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }
    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public void update(double elapsedTime) {

        PointF input = InputManager.movePoint;

        if (input != null) {

            double newShipRotation = Math.toRadians(getAngle(input)) - Math.PI / 2;

            newPosition = GraphicsUtils.moveObject(getPosition(), new RectF(0, 0, 0, 0), 500, newShipRotation, elapsedTime).getNewObjPosition();

            int levelWidth = AsteroidGame.SINGLETON.getCurrentLevel().getWidth();
            int levelHeight = AsteroidGame.SINGLETON.getCurrentLevel().getHeight();

            if (newPosition.x > levelWidth) {
                newPosition.x = levelWidth;
            }
            if (newPosition.x < 0) {
                newPosition.x = 0;
            }
            if (newPosition.y > levelHeight) {
                newPosition.y = levelHeight;
            }
            if (newPosition.y < 0) {
                newPosition.y = 0;
            }

            AsteroidGame.SINGLETON.getShip().setPosition(newPosition);

        }

        generateBounds();

        mainBody.update();
        cannon.update(elapsedTime);
        engine.update();
        extraPart.update();

    }

    @Override
    public void clear() {
        mainBody = null;
        cannon = null;
        engine = null;
        extraPart = null;
        powerCore = null;
        scale = 0;
        newPosition = null;
        setRotation(0);
        setPosition(null);

    }

    public void draw() {

        if (mainBody != null) {
            mainBody.draw();
        }
        if (powerCore != null) {
            powerCore.draw();
        }
        if (engine != null) {
            engine.draw();
        }
        if (cannon != null) {
            cannon.draw();
        }
        if (extraPart != null) {
            extraPart.draw();
        }

    }
}
