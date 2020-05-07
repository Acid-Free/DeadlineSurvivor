package com.ds.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ds.game.DeadlineSurvivor;
import com.ds.game.Entities.Player;
import com.ds.game.FinalState;
import com.ds.game.Furnitures.*;
import com.ds.game.HUD.ActiveHUD;
import com.ds.game.HUD.FurnitureHUD;
import com.ds.game.HUD.MainHUD;
import com.ds.game.Requirements.Project;
import com.ds.game.Tools.WorldContactListener;

public class GameScreen implements Screen {

    final DeadlineSurvivor game;

    private Sprite playerSprite;
    private Texture playerImage;

    private Viewport viewport;
    private OrthographicCamera camera;
    private MainHUD mainHud;
    private FurnitureHUD furnitureHud;
    private ActiveHUD activeHud;

    private TmxMapLoader mapLoader;
    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
//    private Box2DDebugRenderer b2dr;

    private final float unitScale = 0.8f;

    private Player player;
    private int playerOrientationX;
    private int playerOrientationY;
    private int lastActiveOrientationX;
    private int lastActiveOrientationY;

    private int dayCycle;
    private float overallTimeOfDay;

    //test
    private Stage stage;
    private Skin skin;

    private Bed bed;
    private Computer computer;
    private Kitchen kitchen;
    private StudyTable studyTable;
    private Television television;

    public static Project mainProject;

    public GameScreen(final DeadlineSurvivor game){
        this.game = game;

        world = new World(new Vector2(0, 0), true);
        game.player = new Player(world);
        player = game.player;

//        game.player.name = "Bob";
//        System.out.println(player.name);
        System.out.println();


        world.setContactListener(new WorldContactListener(player));

        camera = new OrthographicCamera();
        viewport = new FitViewport(DeadlineSurvivor.SCREEN_WIDTH, DeadlineSurvivor.SCREEN_HEIGHT, camera);

        mainHud = new MainHUD(game.batch, player);
        furnitureHud = new FurnitureHUD(game.batch, player);
        activeHud = new ActiveHUD(game.batch, player);

        //test
//        stage = new Stage(viewport, game.batch);
//        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        playerImage = new Texture(Gdx.files.internal("survivors/mainSurvivor.png"));
        playerSprite = new Sprite(playerImage);
        playerSprite.setSize(35, 35);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera.position.set((viewport.getWorldWidth() - 300) / 2, (viewport.getWorldHeight()) / 2, 0);

//        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        //CircleShape shape2 = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create wall fixtures for rectangles
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) * unitScale, (rectangle.getY() + rectangle.getHeight() / 2) * unitScale);

            body = world.createBody(bdef);
            body.setUserData("wall");

            shape.setAsBox(rectangle.getWidth() / 2 * unitScale, rectangle.getHeight() / 2 * unitScale);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create furniture fixtures for rectangles
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            //System.out.println(map.getLayers().get(8).getObjects().get("restRoomArea"));
//            System.out.println(object.getName());

            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) * unitScale, (rectangle.getY() + rectangle.getHeight() / 2) * unitScale);

            body = world.createBody(bdef);
            body.setUserData(object.getName());

//            Fixture f = body.createFixture(fdef);
//            f.setUserData(object.getName());
//            body.setUserData(this);

            shape.setAsBox(rectangle.getWidth() / 2 * unitScale, rectangle.getHeight() / 2 * unitScale);
            fdef.shape = shape;
            body.createFixture(fdef);
//            System.out.println(body.getUserData());
        }

        //create wall fixtures for ellipses: commented out due to the lack of ellipses in the map file
//        for(MapObject object: map.getLayers().get(7).getObjects().getByType(EllipseMapObject.class)){
//            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((ellipse.x + ellipse.width / 2) * unitScale, (ellipse.y + ellipse.height / 2) * unitScale);
//
//            body = world.createBody(bdef);
//
//            shape2.setPosition(new Vector2(ellipse.width / 2 * 1, ellipse.height / 2 * 1));
//            fdef.shape = shape2;
//            body.createFixture(fdef);
//        }
        map.getLayers().get(2).setVisible(true);

        overallTimeOfDay = player.overallTime % 86400;

        if(overallTimeOfDay > 64800 && overallTimeOfDay <= 18000)
            dayCycle = 2;
        else if(overallTimeOfDay > 18000 && overallTimeOfDay < 54000)
            dayCycle = 0;
        else
            dayCycle = 1;

        computer = new Computer();
        kitchen = new Kitchen(MathUtils.random(35, 50));
        studyTable = new StudyTable();
        television = new Television();
        bed = new Bed();

        mainProject = new Project();
    }

    @Override
    public void show() {

//        new Dialog("", skin){
//            {
//                text("This is the introduction.");
//                button("I understand", true);
//                key(Input.Keys.ESCAPE, true);
//                key(Input.Keys.ENTER, true);
//            }
//
//            @Override
//            protected void result(Object object){
//
//            }
//        }.show(stage);
    }

    public void handleInput(float delta){
        playerOrientationX = 0;
        playerOrientationY = 0;

        player.b2body.setLinearVelocity(0,0);
        if((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && (player.b2body.getLinearVelocity()).y <= 600){
            player.b2body.applyLinearImpulse(new Vector2(0, 100f), player.b2body.getWorldCenter(), true);
            if(++playerOrientationY > 1)
                playerOrientationY = 1;
        }
        if((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && (player.b2body.getLinearVelocity()).y >= -600){
            player.b2body.applyLinearImpulse(new Vector2(0, -100f), player.b2body.getWorldCenter(), true);
            if(--playerOrientationY < -1)
                playerOrientationY = -1;
        }
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))&& (player.b2body.getLinearVelocity()).x >= -600){
            player.b2body.applyLinearImpulse(new Vector2(-100f, 0), player.b2body.getWorldCenter(), true);
            if(--playerOrientationX < -1)
                playerOrientationX = -1;
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && (player.b2body.getLinearVelocity()).x <= 600){
            player.b2body.applyLinearImpulse(new Vector2(100f, 0), player.b2body.getWorldCenter(), true);
            if(++playerOrientationX > 1)
                playerOrientationX = 1;
        }
    }

    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6, 2);

        player.overallTime += delta * player.timeScale;

        overallTimeOfDay = player.overallTime % 86400;

        //daycycle check
        if(overallTimeOfDay > 64800 || overallTimeOfDay <= 18000)
            dayCycle = 2;
        else if(overallTimeOfDay > 18000 && overallTimeOfDay < 54000)
            dayCycle = 0;
        else
            dayCycle = 1;

        //manages furniture electricity consumption and fun factor and misc.
        if(Computer.isActive){
            player.currentElectricity += delta * computer.electricityConsumption;
            player.currentFun += delta * computer.fun;
        }
        if(Kitchen.isActive){
            //additional code for eating mechanism
            kitchen.stayingTimeBeforeEating += delta;
            if(kitchen.stayingTimeBeforeEating > 2){
                player.currentSatiety += kitchen.eat();
                kitchen.stayingTimeBeforeEating = 0;
            }

            player.currentElectricity += delta * kitchen.electricityConsumption;
            player.currentFun += delta * kitchen.fun;
        }
        if(StudyTable.isActive){
            mainProject.doRequirement(player, delta);

            player.currentElectricity += delta * studyTable.electricityConsumption;
            player.currentFun += delta * studyTable.fun;
        }
        if(Television.isActive){
            player.currentElectricity += delta * television.electricityConsumption;
            player.currentFun += delta * television.fun;
        }
        if(Bed.isActive){
            bed.timeBeforeSleeping += delta;
            if(bed.timeBeforeSleeping > 3){
                bed.sleep(player);
                bed.timeBeforeSleeping = 0;
            }
        }

        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        player.update(delta);
        mainHud.update();

        //add later
        furnitureHud.update();
        activeHud.update();
        checkGameState();

        Gdx.gl.glClearColor(0.29f,0.29f, 0.29f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int[] essentials = {dayCycle, 3, 4};
        renderer.render(essentials);
        renderer.getBatch().begin();
        for(MapObject object : map.getLayers().get(5).getObjects()){
            TextureMapObject obj = (TextureMapObject) object;
            renderer.getBatch().draw(obj.getTextureRegion(), obj.getX() * unitScale, obj.getY() * unitScale, obj.getOriginX(), obj.getOriginY(),
                    obj.getTextureRegion().getRegionWidth(), obj.getTextureRegion().getRegionHeight(), obj.getScaleX() * unitScale,
                    obj.getScaleY() * unitScale, 0 -obj.getRotation());
        }
        for(MapObject object : map.getLayers().get(6).getObjects()){
            TextureMapObject obj = (TextureMapObject) object;
            renderer.getBatch().draw(obj.getTextureRegion(), obj.getX() * unitScale, obj.getY() * unitScale, obj.getOriginX(), obj.getOriginY(),
                    obj.getTextureRegion().getRegionWidth(), obj.getTextureRegion().getRegionHeight(), obj.getScaleX(),
                    obj.getScaleY(), 0 - obj.getRotation());
        }
        renderer.getBatch().end();
//        b2dr.render(world, camera.combined);

//        game.batch.draw(playerImage, player.b2body.getPosition().x + 135,player.b2body.getPosition().y - 20,
//                30, 30);
        playerSprite.setPosition((int)player.b2body.getPosition().x + 133, (int)player.b2body.getPosition().y - 18);
        //playerSprite.setCenter((int)player.b2body.getPosition().x + 145, (int)player.b2body.getPosition().y - 20);
        playerSprite.setOrigin(18, 18);
        int rotationValue = 0;
        if((playerOrientationX != lastActiveOrientationX || playerOrientationY != lastActiveOrientationY)
            && !(playerOrientationX == 0 && playerOrientationY == 0)) {
            if (playerOrientationX == -1) {
                switch (playerOrientationY) {
                    case -1:
                        rotationValue = 315;
                        break;
                    case 0:
                        rotationValue = 270;
                        break;
                    case 1:
                        rotationValue = 225;
                        break;
                }
            } else if (playerOrientationX == 0) {
                switch (playerOrientationY) {
                    case -1:
                        rotationValue = 0;
                        break;
                    case 0:
                        System.out.println("yeah");
                        break;
                    case 1:
                        rotationValue = 180;
                        break;
                }
            } else if (playerOrientationX == 1) {
                switch (playerOrientationY) {
                    case -1:
                        rotationValue = 45;
                        break;
                    case 0:
                        rotationValue = 90;
                        break;
                    case 1:
                        rotationValue = 135;
                        break;
                }
            }
            playerSprite.setRotation(rotationValue);
            lastActiveOrientationX = playerOrientationX;
            lastActiveOrientationY = playerOrientationY;
        }


        game.batch.begin();
        playerSprite.draw(game.batch);
//        game.font.draw(game.batch, String.format("X: %d : %d", playerOrientationX, lastActiveOrientationX), 0, 40);
//        game.font.draw(game.batch, String.format("Y: %d : %d", playerOrientationY, lastActiveOrientationY), 0, 20);
        game.batch.end();

        game.batch.setProjectionMatrix(mainHud.stage.getCamera().combined);
        mainHud.stage.draw();
        game.batch.setProjectionMatrix(furnitureHud.stage.getCamera().combined);
        furnitureHud.stage.draw();
        game.batch.setProjectionMatrix(activeHud.stage.getCamera().combined);
        activeHud.stage.draw();
    }

    public void checkGameState(){
        //add victory check later
        if(mainProject.requirementDone){
            player.finalState = FinalState.VICTORY;
            player.finalMessage = "You have Completed your Project, CONGRATULATIONS.";
            game.setScreen(new VictoryScreen(game));
            dispose();
        }

        //defeat check
        if(player.currentHealth <= 0){
            player.finalState = FinalState.DEFEAT;
            player.finalMessage = "Your Health reached ZERO, well that means Death...";
            game.setScreen(new DefeatScreen(game));
            dispose();
        }
        else if(player.overallTime > player.deadlineTime + 86400){
            player.finalState = FinalState.DEFEAT;
            player.finalMessage = "The Deadline has PASSED, you didn't fulfill your Promise...";
            game.setScreen(new DefeatScreen(game));
            dispose();
        }
        else if(player.currentElectricity <= 0){
            player.finalState = FinalState.DEFEAT;
            player.finalMessage = "You RAN OUT of Electricity, you DIED from severe Boredom short after...";
            game.setScreen(new DefeatScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
