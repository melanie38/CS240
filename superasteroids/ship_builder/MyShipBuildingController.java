package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.PointF;
import android.widget.Toast;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.data_access.DAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.importer.ImportActivity;
import edu.byu.cs.superasteroids.importer.MyGameDataImporter;
import edu.byu.cs.superasteroids.model.AsteroidGame;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;
import android.widget.Toast;

/**
 * Created by Melanie on 26/05/16.
 */
public class MyShipBuildingController implements IShipBuildingController {

    private IShipBuildingView shipBuildingView;
    private IShipBuildingView.PartSelectionView currentView;
    boolean first;
    Ship ship;

    private ArrayList<Integer> listOfMainBodiesId = new ArrayList<>();
    private ArrayList<Integer> listOfCannonsId = new ArrayList<>();
    private ArrayList<Integer> listOfExtraPartsId = new ArrayList<>();
    private ArrayList<Integer> listOfPowerCoresId = new ArrayList<>();
    private ArrayList<Integer> listOfEnginesId = new ArrayList<>();

    private int mainBodyId = -1;
    private int cannonId = -1;
    private int extraPartId = -1;
    private int powerCoreId = -1;
    private int engineId = -1;


    public MyShipBuildingController(IShipBuildingView shipBuildingView) {
        this.shipBuildingView = shipBuildingView;
        first = true;
        AsteroidGame.SINGLETON.getShip().clear();
        ship = new Ship();
        AsteroidGame.SINGLETON.setShip(ship);
        //ship = new Ship();
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {

        currentView = partView;

        switch (currentView) {
            case MAIN_BODY:
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "POWER_CORE");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "ENGINE");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "CANNON");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "EXTRA_PART");
                break;
            case POWER_CORE:
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "MAIN_BODY");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                break;
            case ENGINE:
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "MAIN_BODY");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                break;
            case CANNON:
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "MAIN_BODY");
                break;
            case EXTRA_PART:
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "MAIN_BODY");
                shipBuildingView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                break;
        }
    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void loadContent(ContentManager content) {

        if (first) {
            loadMainBodies(content);
            loadCannons(content);
            loadPowerCores(content);
            loadExtraParts(content);
            loadEngines(content);
        }
        first = false;
    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {

        if (ship != null) {
            AsteroidGame.SINGLETON.getShip().setScreenPosition(new PointF(DrawingHelper.getGameViewWidth() / 2, DrawingHelper.getGameViewHeight() / 2));
        }

        if (isShipComplete()) {
            //AsteroidGame.SINGLETON.;
            shipBuildingView.setStartGameButton(true);
        }

        ship.draw();
        AsteroidGame.SINGLETON.getShip().setScreenPosition(null);

    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {

        switch (currentView) {
            case MAIN_BODY:
                switch (direction) {
                     case UP:
                         shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.DOWN);
                         currentView = IShipBuildingView.PartSelectionView.ENGINE;
                         break;
                     case DOWN:
                         shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.UP);
                         currentView = IShipBuildingView.PartSelectionView.POWER_CORE;
                         break;
                     case RIGHT:
                         shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT);
                         currentView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                         break;
                     case LEFT:
                         shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT);
                         currentView = IShipBuildingView.PartSelectionView.CANNON;
                         break;
            } break;
            case POWER_CORE:
                switch (direction) {
                    case UP:
                        shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.DOWN);
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                } break;
            case ENGINE:
                switch (direction) {
                    case DOWN:
                        shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.UP);
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;;
                } break;
            case CANNON:
                switch (direction) {
                    case RIGHT:
                        shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT);
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                } break;
            case EXTRA_PART:
                switch (direction) {
                    case LEFT:
                        shipBuildingView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT);
                        currentView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                } break;
        }

    }

    @Override
    public void onPartSelected(int index) {

        switch (currentView) {
            case MAIN_BODY:
                mainBodyId = listOfMainBodiesId.get(index);
                ship.setMainBody(DAO.SINGLETON.getMainBodies().get(index)); break;
            case POWER_CORE:
                powerCoreId = listOfPowerCoresId.get(index);
                ship.setPowerCore(DAO.SINGLETON.getPowerCore().get(index)); break;
            case ENGINE:
                engineId = listOfEnginesId.get(index);
                ship.setEngine(DAO.SINGLETON.getEngine().get(index)); break;
            case CANNON:
                cannonId = listOfCannonsId.get(index);
                ship.setCannon(DAO.SINGLETON.getCannons().get(index)); break;
            case EXTRA_PART:
                extraPartId = listOfExtraPartsId.get(index);
                ship.setExtraPart(DAO.SINGLETON.getExtraParts().get(index)); break;
        }
    }

    @Override
    public void onStartGamePressed() {

        shipBuildingView.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }

    public void loadMainBodies(ContentManager content) {
        for (MainBody mainBody : DAO.SINGLETON.getMainBodies()) {
            int index = content.loadImage(mainBody.getImage());
            listOfMainBodiesId.add(index);
            mainBody.setId(index);
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, listOfMainBodiesId);
    }
    public void loadCannons(ContentManager content) {
        for (Cannon cannon : DAO.SINGLETON.getCannons()) {
            int index = content.loadImage(cannon.getImage());
            listOfCannonsId.add(index);
            cannon.setId(index);
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, listOfCannonsId);
    }
    public void loadExtraParts(ContentManager content) {
        for (ExtraPart extraPart : DAO.SINGLETON.getExtraParts()) {
            int index = content.loadImage(extraPart.getImage());
            listOfExtraPartsId.add(index);
            extraPart.setId(index);
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, listOfExtraPartsId);
    }
    public void loadPowerCores(ContentManager content) {
        for (PowerCore powerCore : DAO.SINGLETON.getPowerCore()) {
            int index = content.loadImage(powerCore.getImage());
            listOfPowerCoresId.add(index);
            powerCore.setId(index);
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, listOfPowerCoresId);
    }
    public void loadEngines(ContentManager content) {
        for (Engine engine : DAO.SINGLETON.getEngine()) {
            int index = content.loadImage(engine.getImage());
            listOfEnginesId.add(index);
            engine.setId(index);
        }
        shipBuildingView.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, listOfEnginesId);
    }

    public boolean isShipComplete() {
        if (ship.getMainBody() != null &&
                ship.getCannon() != null &&
                ship.getEngine() != null &&
                ship.getExtraPart() != null &&
                ship.getPowerCore() != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
