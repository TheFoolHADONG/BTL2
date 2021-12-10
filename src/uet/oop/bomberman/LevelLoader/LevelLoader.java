package uet.oop.bomberman.LevelLoader;

import uet.oop.bomberman.entities.*;
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
                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                switch (list.get(i+1).charAt(j)) {
                    case '#':
                        stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                    // Thêm Portal
                    case 'x':
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm brick
                    case '*':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm Bomber
                     case 'p':
                        playerx = j;
                        playery = j;
                        break;

                    // Thêm balloon
                    case '1':
                        entities.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    // Thêm oneal
                    case '2':
                        entities.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    // Thêm doll
                    case '3':
                        entities.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                        break;
                    case '4':
                        entities.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                        break;
                    case '5':
                        entities.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                        break;

            //       Thêm BomItem
            //        case 'b':
            //            LayeredEntity layer = new LayeredEntity(x, y,
            //                    new Grass(x, y, Sprite.grass),
            //                    new BombItem(x, y, Sprite.powerup_bombs),
            //                    new Brick(x, y, Sprite.brick));
            //            _board.addEntity(pos, layer);
            //            break;
            //        // Thêm SpeedItem
            //        case 's':
            //            layer = new LayeredEntity(x, y,
            //                    new Grass(x, y, Sprite.grass),
            //                    new SpeedItem(x, y, Sprite.powerup_speed),
            //                    new Brick(x, y, Sprite.brick));
            //            _board.addEntity(pos, layer);
            //            break;
            //        // Thêm FlameItem
            //        case 'f':
            //            layer = new LayeredEntity(x, y,
            //                    new Grass(x, y, Sprite.grass),
            //                    new FlameItem(x, y, Sprite.powerup_flames),
            //                    new Brick(x, y, Sprite.brick));
            //            _board.addEntity(pos, layer);
            //            break;

                }

            }
        }
    }


}
