package edu.byu.cs.superasteroids.model;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing a power core part of the ship.
 */
public class PowerCore extends Visible {
    private int cannonBoost;
    private int engineBoost;

    public PowerCore(){}

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }
    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
    public void setImage(){}

    public int getCannonBoost(){return cannonBoost;}
    public int getEngineBoost(){return engineBoost;}
}

