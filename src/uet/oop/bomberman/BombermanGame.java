package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uet.oop.bomberman.LevelLoader.LevelLoader;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.ImageLoader;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.Sound.SoundPlayer.*;
import static uet.oop.bomberman.graphics.Sprite.DEFAULT_SIZE;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombermanGame extends Application {

    public static final int FPS = 165;
    private final int timeMax = 999;
    private int time = timeMax;
    private int timeSleeper = 5000;
    private int level = 1;

    //private Timer timerCountDown = new Timer();


    public double x = 0;
    public static int score = 0;
    public static int scorestart = 0;
    private boolean paused = true;
    private boolean pausedInGame = false;
    private boolean levelstarted = true;
    private boolean sleep = true;
    private boolean gameScene = false;

    private Scene scene;
    private Scene sceneMenu;
    private GraphicsContext gc;
    private Canvas canvas;
    private GraphicsContext gc2;
    private Canvas canvasMenu;
    private final Font fontlarge  = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 50);
    private final Font fontsmall = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 30);
    private final Font fontmedium = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 40);
    private final Font fontmedium1 = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 35);
    private Text textScore = new Text(20,22,"Score: " + score);
    private Text textTimer = new Text(20,22,"Timer: " + time);
    LevelLoader levelLoader = new LevelLoader();

    public static Bomber bomberman;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> flames= new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static boolean lose = false;
    public static boolean win = false;
    public static boolean iswin = false;


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

    public static boolean getPlayerBombAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Bomb && !((Bomb) entity).isMove())
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

    public static Entity getPlayer() {
        for(Entity entity : entities) {
            if(entity instanceof Bomber)
                return entity;
        }
        return null;
    }

    public static List<Entity> getAllMob() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Mob && !((Mob) entity).isDie()) {
                t.add(entity);
            }
        }
        return t;
    }

    public static List<Entity> getAllBlock() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Brick) {
                t.add(entity);
            }
        }
        for(Entity entity : stillObjects) {
            if(entity instanceof Wall) {
                t.add(entity);
            }
        }
        return t;
    }

    public static List<Entity> getAllPortal() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Portal) {
                t.add(entity);
            }
        }
        return t;
    }

    public static boolean getWallAt(double x, double y) {
        for(Entity entity : stillObjects) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Wall)
                return true;
        }
        return false;
    }

    public static boolean getPortalAt(double x, double y) {
        for(Entity entity : stillObjects) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Portal)
                return true;
        }
        return false;
    }

    public static boolean stillMob() {
        for(Entity entity : entities) {
            if(entity instanceof Mob)
                return true;
        }
        return false;
    }





    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }



    @Override
    public void start(Stage stage) {

        startTimer();

        loadMenuScene(stage);

        stage.setTitle("Bomberman by Haiten Team");
        stage.getIcons().add(ImageLoader.Icon.getImage());
        stage.setResizable(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

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
        textScore.setText("Score  " + score);
        textTimer.setText("Time  " + time);
    }

    public void checkCamera() {

        if(bomberman.getXPixel() > x + (canvas.getHeight() / 2) - 1 && x < canvas.getWidth() - canvas.getHeight() + SCALED_SIZE/2) {
            gc.translate(-165/FPS,0);
            x += 165/FPS;
        }

        if(bomberman.getXPixel() < x + (canvas.getHeight() / 2) - 1 && x > 0) {
            gc.translate(165/FPS,0);
            x -= 165/FPS;
        }
    }

    public void moveCamera() {
        if(x >= 0 && x < canvas.getWidth() - canvas.getHeight() + SCALED_SIZE/2) {
            gc.translate(-165/FPS,0);
            x += 165/FPS;
        }
    }

    public void renderearly() {
        moveCamera();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Level " + level, x + canvas.getHeight()/2,  canvas.getHeight()/2);
        if(level == 1) gc.setFill(Color.LIGHTGREEN);
        if(level == 2) gc.setFill(Color.YELLOW);
        if(level == 3) gc.setFill(Color.RED);
        textScore.setText("Score  " + score);
        textTimer.setText("Time  " + time);
        textTimer.wrappingWidthProperty().bind(new TabPane().widthProperty());
    }

    public void sleeperStart() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(timeSleeper);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                paused = false;
                levelstarted = false;
                gc.translate(x,0);
                x = 0;
            }
        });
        new Thread(sleeper).start();
    }

    public void startTimer() {
        Timer timerCountDown = new Timer();
        timerCountDown.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(!paused && gameScene && !pausedInGame) {
                    if(time > 0 )
                    {
                        time--;
                    }
                    else
                        lose = true;
                }
            }
        }, 1000,1000);
    }


    public void checkWin() {
        if(!stillMob() && !win) {
            win = true;
        }
    }


    public void clear() {
        if(iswin) {
            score += time;
            scorestart = score;
        }
        else score = scorestart;
        time = timeMax;
        entities.clear();
        stillObjects.clear();
        flames.clear();
        paused = true;
        levelstarted = true;
        win = false;
        iswin = false;
        lose = false;
        pausedInGame = false;
    }

    public void loadScene(int level) {
        clear();

        gc.translate(x,0);
        x = 0;

        levelLoader.loadLevel(level,entities,stillObjects);
        bomberman = levelLoader.getPlayer();
        entities.add(bomberman);

        canvas.setWidth(levelLoader.getWidth());
        canvas.setHeight(levelLoader.getHeight());
        textTimer.setX(canvas.getHeight()*0.65);
        sleeperStart();
    }

    public void loadFirstScene(Stage stage ,boolean playOnce) {
        levelLoader.loadLevel(level ,entities,stillObjects);
        bomberman = levelLoader.getPlayer();
        entities.add(bomberman);
        scorestart = score;
        // Tao Canvas
        canvas = new Canvas(levelLoader.getWidth(), levelLoader.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.setFont(fontlarge);

        textScore.setFont(fontsmall);
        textTimer.setFont(fontsmall);

        textTimer.setX(canvas.getHeight()*0.65);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(textScore);
        root.getChildren().add(textTimer);

        // Tao scene
        scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.setWidth(canvas.getHeight());
        stage.show();
        // Them am thanh stage theme
        SoundPlayer.playLoop(stage_theme);

        AnimationTimer timer = new AnimationTimer() {
           @Override
           public void handle(long l) {
               if (levelstarted) {
                   stage.setWidth(canvas.getHeight());
                   stage.setHeight(canvas.getHeight()+39);
                   renderearly();
               } else {
                   render();
                   update();
                   checkWin();
                       if (iswin) {
                           level++;
                           VBox vBox = new VBox();
                           //chat.setPrefSize(400, 400);
                           Label text = new Label("Win");
                           text.setFont(fontlarge);
                           text.setStyle("-fx-text-fill: lightgreen");
                           Label text1 = new Label("Score = " + score);
                           text1.setFont(fontsmall);
                           text1.setStyle("-fx-text-fill: black");
                           Label text2 = new Label("Time  = " + time);
                           text2.setFont(fontsmall);
                           text2.setStyle("-fx-text-fill: black");
                           Label text3 = new Label("Score = " + (time + score));
                           text3.setFont(fontmedium);
                           text3.setStyle("-fx-text-fill: yellow");
                           vBox.getChildren().addAll(text, text1, text2, text3);
                           vBox.setSpacing(15);
                           vBox.setAlignment(Pos.CENTER);
                           vBox.setTranslateY(stage.getHeight()/5);
                           vBox.setTranslateX(stage.getWidth()/4);

                           if(playOnce) {
                               root.getChildren().addAll(vBox, buttonReturntomenu(this, stage));
                           } else root.getChildren().addAll(vBox, buttonReturntomenu(this, stage), buttonRetry(this, stage, root));
                           stop();
                       }
                       if (lose) {
                           TextField text = new TextField("Lose");
                           text.setBackground(Background.EMPTY);
                           text.setFont(fontlarge);
                           text.setTranslateY(stage.getHeight()/4);
                           text.setTranslateX(stage.getWidth()/3);
                           text.setStyle("-fx-text-fill: red");
                           root.getChildren().addAll(buttonReturntomenu(this, stage), buttonRetry(this, stage, root), text);
                           stop();
                   }
               }
           }
       };

       timer.start();
       scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               if(!paused) {
                   bomberman.bombermove(event.getCode(), true);
                   if (event.getCode() == KeyCode.SPACE) {
                       bomberman.placeBomb();
                   }
                   if (event.getCode() == KeyCode.ESCAPE) {
                       if (pausedInGame) {
                           root.getChildren().remove(root.getChildren().size() - 1);
                           root.getChildren().remove(root.getChildren().size() - 1);
                           timer.start();
                           pausedInGame = false;
                       } else {
                           FlowPane pausedFlowPane = volumeFlowPane();
                           pausedFlowPane.setAlignment(Pos.BOTTOM_LEFT);
                           Button pausedButton = buttonReturntomenu(timer, stage);
                           root.getChildren().addAll(pausedFlowPane,pausedButton);
                           gc.drawImage(ImageLoader.Pause.getImage(), x + canvas.getHeight() / 2 - DEFAULT_SIZE * 5, canvas.getHeight() / 2 - DEFAULT_SIZE * 5);
                           timer.stop();
                           pausedInGame = true;
                       }
                   }
                   if (event.getCode() == KeyCode.P) {
                       clear();
                       timer.stop();
                       gameScene = false;
                       x = 0;
                       stage.setScene(sceneMenu);
                   }
               }
           }
       });
       scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               bomberman.bombermove(event.getCode(), false);
           }
       });
       sleeperStart();
    }

    public void loadMenuScene(Stage stage) {
        BorderPane borderPane = new BorderPane();
        FlowPane buttons = new FlowPane(Orientation.VERTICAL);
        // button play
        Button play = new Button("Play");
        play.setFont(fontlarge);
        play.setBackground(null);
        play.setStyle("-fx-text-fill: blue");
        play.setOnMouseEntered(e -> play.setStyle("-fx-text-fill: orange"));
        play.setOnMouseExited(e -> play.setStyle("-fx-text-fill: blue"));
        play.setOnAction(e -> {
            score = 0;
            gameScene = true;
            loadFirstScene(stage, false);
        });

        // text by Haiten Team
        Text text = new Text("by Haiten Team");


        // selectLevel
        Button selectLevel = new Button("Select level");
        selectLevel.setFont(fontlarge);
        selectLevel.setBackground(null);
        selectLevel.setStyle("-fx-text-fill: green");
        selectLevel.setOnMouseEntered(e -> selectLevel.setStyle("-fx-text-fill: cyan"));
        selectLevel.setOnMouseExited(e -> selectLevel.setStyle("-fx-text-fill: green"));
        selectLevel.setOnAction(e -> {
            FlowPane levels = new FlowPane(Orientation.VERTICAL);

            Button level1 = new Button("Level 1");
            level1.setFont(fontlarge);
            level1.setBackground(null);
            level1.setStyle("-fx-text-fill: lightgreen");
            level1.setOnMouseEntered(r -> level1.setStyle("-fx-text-fill: cyan"));
            level1.setOnMouseExited(r -> level1.setStyle("-fx-text-fill: lightgreen"));
            level1.setOnAction(r -> {
                score = 0;
                gameScene = true;
                level = 1;
                loadFirstScene(stage, true);
            });

            Button level2 = new Button("Level 2");
            level2.setFont(fontlarge);
            level2.setBackground(null);
            level2.setStyle("-fx-text-fill: yellow");
            level2.setOnMouseEntered(r -> level2.setStyle("-fx-text-fill: cyan"));
            level2.setOnMouseExited(r -> level2.setStyle("-fx-text-fill: yellow"));
            level2.setOnAction(r -> {
                score = 0;
                gameScene = true;
                level = 2;
                loadFirstScene(stage, true);
            });

            Button level3 = new Button("Level 3");
            level3.setFont(fontlarge);
            level3.setBackground(null);
            level3.setStyle("-fx-text-fill: red");
            level3.setOnMouseEntered(r -> level3.setStyle("-fx-text-fill: cyan"));
            level3.setOnMouseExited(r -> level3.setStyle("-fx-text-fill: red"));
            level3.setOnAction(r -> {
                score = 0;
                gameScene = true;
                level = 3;
                loadFirstScene(stage, true);
            });

            levels.getChildren().addAll(level1,level2,level3);
            levels.setAlignment(Pos.CENTER_RIGHT);

            borderPane.setCenter(levels);
        });

        // button exit
        Button quit = new Button("quit");
        quit.setFont(fontlarge);
        quit.setBackground(null);
        quit.setStyle("-fx-text-fill: black");
        quit.setOnMouseEntered(e -> quit.setStyle("-fx-text-fill: red"));
        quit.setOnMouseExited(e -> quit.setStyle("-fx-text-fill: black"));
        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        buttons.getChildren().addAll( play, selectLevel , quit, volumeFlowPane()) ;
        buttons.setAlignment(Pos.CENTER_LEFT);

        borderPane.setBottom(text);
        BorderPane.setMargin(text, new Insets(12,12,12,12));
        borderPane.setAlignment(text, Pos.CENTER);
        borderPane.setCenter(buttons);


        sceneMenu = new Scene(borderPane);
        stage.setScene(sceneMenu);
        stage.setWidth(15 * SCALED_SIZE);
        stage.setHeight(15 * SCALED_SIZE);
        stage.show();

        sceneMenu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    borderPane.setCenter(buttons);
                }
            }
        });
    }

    public FlowPane volumeFlowPane() {
        FlowPane flowPane = new FlowPane();
        FlowPane flowPane1 = new FlowPane();
        FlowPane flowPane2 = new FlowPane();

        Slider themeSlider = new Slider();
        themeSlider.setMaxWidth(SCALED_SIZE*5);
        themeSlider.setMinHeight(SCALED_SIZE);
        themeSlider.setTranslateX(40);
        themeSlider.setTranslateY(50);
        themeSlider.setValue(themeVolume*100);
        themeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(theme != null) theme.setVolume(themeSlider.getValue()/100);
                themeVolume = themeSlider.getValue()/100;
            }
        });

        Slider soundeffectSlider = new Slider();
        soundeffectSlider.setMaxWidth(SCALED_SIZE*5);
        soundeffectSlider.setMinHeight(SCALED_SIZE);
        soundeffectSlider.setTranslateX(40);
        soundeffectSlider.setTranslateY(50);
        soundeffectSlider.setValue(soundeffectVolume*100);
        soundeffectSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                soundeffectVolume = soundeffectSlider.getValue()/100;
            }
        });

        flowPane1.getChildren().addAll(themeSlider);
        flowPane2.getChildren().addAll(soundeffectSlider);
        flowPane.getChildren().addAll(flowPane1, flowPane2);
        flowPane.setAlignment(Pos.CENTER_LEFT);
        return flowPane;
    }

    public Button buttonReturntomenu(AnimationTimer timer, Stage stage) {
        Button menu = new Button("Return to menu");
        menu.setFont(fontmedium1);
        menu.setBackground(null);
        menu.setStyle("-fx-text-fill: black");
        menu.setOnMouseEntered(e -> menu.setStyle("-fx-text-fill: red"));
        menu.setOnMouseExited(e -> menu.setStyle("-fx-text-fill: black"));
        menu.setTranslateY(stage.getHeight()-100);
        menu.setOnAction(e -> {
            clear();
            level = 1;
            gameScene = false;
            x = 0;
            stage.setScene(sceneMenu);
            theme.stop();
        });
        return menu;
    }

    public Button buttonNextlevel(AnimationTimer timer, Stage stage) {
        Button menu = new Button("Return to menu");
        menu.setFont(fontmedium);
        menu.setBackground(null);
        menu.setStyle("-fx-text-fill: black");
        menu.setOnMouseEntered(e -> menu.setStyle("-fx-text-fill: red"));
        menu.setOnMouseExited(e -> menu.setStyle("-fx-text-fill: black"));
        menu.setTranslateY(stage.getHeight()-100);
        menu.setOnAction(e -> {

        });
        return menu;
    }

    public Button buttonRetry(AnimationTimer timer,Stage stage,Group root) {
        Button retry = new Button("Retry");
        retry.setFont(fontmedium);
        retry.setBackground(null);
        retry.setStyle("-fx-text-fill: lightgreen");
        retry.setOnMouseEntered(e -> retry.setStyle("-fx-text-fill: yellow"));
        retry.setOnMouseExited(e -> retry.setStyle("-fx-text-fill: lightgreen"));
        retry.setTranslateY(stage.getHeight()-100);
        retry.setTranslateX(stage.getWidth()-150);
        retry.setOnAction(e -> {
            SoundPlayer.playLoop(stage_theme);
            loadScene(level);
            timer.start();
            root.getChildren().remove(root.getChildren().size() - 1);
            root.getChildren().remove(root.getChildren().size() - 1);
            root.getChildren().remove(root.getChildren().size() - 1);
        });
        return retry;
    }
}
