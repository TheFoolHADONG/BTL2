package uet.oop.bomberman.LevelLoader;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.ImageLoader;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    private int width, height;
    private int levelnumber;
    private int playerx, playery;

    public Bomber getPlayer() {
        return new Bomber(playerx, playery, Sprite.player_right.getFxImage());
    }

    public int getWidth() {
        return Sprite.SCALED_SIZE * width;
    }

    public int getHeight() {
        return Sprite.SCALED_SIZE * height;
    }

    public int getLevelnumber() {
        return levelnumber;
    }

    public void loadLevel(int level, List<Entity> entities, List<Entity> stillObjects) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");//doc tep luu map
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
                //doc file txt luu vao list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        levelnumber = Integer.parseInt(arrays[0]);
        height = Integer.parseInt(arrays[1]);
        width = Integer.parseInt(arrays[2]);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(level==1) stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                if(level==2) stillObjects.add(new Grass(j, i, ImageLoader.Sand.getImage()));
                if(level==3) stillObjects.add(new Grass(j, i, ImageLoader.Lava_rock.getImage()));
                switch (list.get(i+1).charAt(j)) {
                    case '#':
                        if(level == 1) stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        if(level == 2) stillObjects.add(new Wall(j, i, ImageLoader.Water.getImage()));
                        if(level == 3) stillObjects.add(new Wall(j, i, ImageLoader.Lava.getImage()));
                        break;
                    // Th??m Portal
                    case 'x':
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Th??m brick
                    case '*':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Th??m Bomber
                     case 'p':
                        playerx = j;
                        playery = j;
                        break;

                    // Th??m balloon
                    case '1':
                        entities.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    // Th??m oneal
                    case '2':
                        entities.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    // Th??m doll
                    case '3':
                        entities.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                        break;
                    case '4':
                        entities.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                        break;
                    case '5':
                        entities.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                        break;

                    //Th??m BomItem
                    case 'b':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Th??m SpeedItem
                    case 's':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Th??m FlameItem
                    case 'f':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;

                }

            }
        }
    }


}
