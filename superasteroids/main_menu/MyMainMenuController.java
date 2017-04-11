package edu.byu.cs.superasteroids.main_menu;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.base.MyGameDelegate;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidGame;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;
import edu.byu.cs.superasteroids.ship_builder.MyShipBuildingController;

/**
 * Created by Melanie on 26/05/16.
 */
public class MyMainMenuController implements IMainMenuController {

    IMainMenuView mainMenuView;
    Ship ship;

    public MyMainMenuController(IMainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;

    }
    @Override
    public void onQuickPlayPressed() {
        AsteroidGame.getInstance();
        buildShip();
        mainMenuView.startGame();
    }

    @Override
    public IView getView() {
        return mainMenuView;
    }

    public void buildShip() {
        MainBody mainBody = AsteroidGame.SINGLETON.getListOfMainBodies().get(1);
        Cannon cannon = AsteroidGame.SINGLETON.getListOfCannons().get(1);
        ExtraPart extraPart = AsteroidGame.SINGLETON.getListOfExtraParts().get(1);
        Engine engine = AsteroidGame.SINGLETON.getListOfEngines().get(1);
        PowerCore powerCore = AsteroidGame.SINGLETON.getListOfPowerCores().get(1);

        ship = new Ship(mainBody, cannon, engine, extraPart, powerCore);

        AsteroidGame.SINGLETON.setShip(ship);
    }

    @Override
    public void setView(IView view) {
        mainMenuView = (IMainMenuView) view;

    }
}
