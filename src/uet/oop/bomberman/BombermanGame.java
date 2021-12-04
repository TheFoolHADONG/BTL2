package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.LevelLoader.LevelLoader;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.Sound.SoundPlayer.stage_theme;

public class BombermanGame extends Application {

    public static final int FPS = 165;


    public double x;
    
    private GraphicsContext gc;
    private Canvas canvas;

    private Bomber bomberman;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Flame> flames= new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static boolean endgame = false;


    public static Entity getAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && !(entity instanceof Bomber))
                return entity;
        }
        return null;
    }
    public static boolean getFlameAt(int x, int y) {
        for(Entity entity : flames) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Flame)
                return true;
        }
        return false;
    }

    public static boolean getBrickAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Brick)
                return true;
        }
        return false;
    }

    public static boolean getMobAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Mob && !((Mob) entity).isDie())
                return true;
        }
        return false;
    }

    public static boolean getWallkAt(int x, int y) {
        for(Entity entity : stillObjects) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Wall)
                return true;
        }
        return false;
    }





    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }



    @Override
    public void start(Stage stage) {
        LevelLoader levelLoader = new LevelLoader();
        levelLoader.loadLevel(1 ,entities,stillObjects);
        // Tao Canvas
        canvas = new Canvas(levelLoader.getWidth(), levelLoader.getHeight());
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);




        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman by");
        stage.setWidth(levelLoader.getHeight());
        stage.show();

        // Them am thanh stage theme
        SoundPlayer.playLoop(stage_theme);



        bomberman = levelLoader.getPlayer();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                bomberman.bombermove(event.getCode(), true);
                if(event.getCode() == KeyCode.SPACE) {
                    bomberman.placeBomb();
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                bomberman.bombermove(event.getCode(), false);
            }
        });

        Label label = new Label();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                render();
                update();
            }
        };

        timer.start();



        entities.add(bomberman);
    }

    private boolean n = false;

    public void update() {
        flames.removeIf(Entity::isRemove);
        flames.forEach(Entity::update);
        entities.removeIf(Entity::isRemove);
        entities.forEach(Entity::update);
    }

    public void render() {
        checkCamera();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
    }

    public void checkCamera() {

        if(bomberman.getXPixel() > x + (canvas.getHeight() / 2) - 1 && x < canvas.getWidth() - canvas.getHeight() + Sprite.SCALED_SIZE/2) {
            gc.translate(-1,0);
            x += 1;
        }

        if(bomberman.getXPixel() < x + (canvas.getHeight() / 2) - 1 && x > 0) {
            gc.translate(1,0);
            x -= 1;
        }
    }
}
