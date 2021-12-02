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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;


import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int FPS = 165;

    public static final int WIDTH = 40;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;

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

    public static boolean getBombAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Bomb)
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
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

        /*
        Bomb bombtest = new Bomb(2, 2, Sprite.bomb.getFxImage());
        Flame flameinside = new Flame(4,4,Sprite.bomb_exploded.getFxImage(),0);
        Flame flametop = new Flame(4,2,Sprite.explosion_vertical_top_last.getFxImage(),1);
        Flame flamedown = new Flame(4,6,Sprite.explosion_vertical_down_last.getFxImage(),2);
        Flame flameleft = new Flame(2,4,Sprite.explosion_horizontal_left_last.getFxImage(),3);
        Flame flameright = new Flame(6,4,Sprite.explosion_horizontal_right_last.getFxImage(),4);
        Flame flameh1 = new Flame(3,4,Sprite.explosion_horizontal.getFxImage(),6);
        Flame flameh2 = new Flame(5,4,Sprite.explosion_horizontal.getFxImage(),6);
        Flame flamev1 = new Flame(4,3,Sprite.explosion_vertical.getFxImage(),5);
        Flame flamev2 = new Flame(4,5,Sprite.explosion_vertical.getFxImage(),5);
        List<Flame> flames =  new ArrayList<Flame>();
        flames.add(flametop);
        flames.add(flamedown);
        flames.add(flameleft);
        flames.add(flameright);
        flames.add(flameinside);
        flames.add(flamev1);
        flames.add(flamev2);
        flames.add(flameh1);
        flames.add(flameh2);

         */

        Brick brick = new Brick(5,5 ,Sprite.brick.getFxImage());
        Brick brick2 = new Brick(2,4 ,Sprite.brick.getFxImage());
        Brick brick3 = new Brick(2,3,Sprite.brick.getFxImage());


        createMap();

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


        entities.add(brick);
        entities.add(brick2);
        entities.add(brick3);


        /*
        entities.addAll(flames);
        entities.add(bombtest);

         */

        entities.add(bomberman);
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    private boolean n = false;

    public void update() {
        flames.removeIf(Entity::isRemove);
        flames.forEach(Entity::update);
        entities.removeIf(Entity::isRemove);
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
    }
}
