package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.FPS;

public class Balloom extends Mob {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (up) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (down) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (left) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (right) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    private int time = 0;
    private int g = new Random().nextInt(4);

    @Override
    public void update() {
        if(!die) {
            animate();
            IsDeath();
        } else {
            setImg(Sprite.balloom_dead.getFxImage());
            if(timedie>0) {
                timedie--;
            } else remove = true;
            if(timedie< FPS) {
                chooseImageDeath(timedie+ FPS);
            }
            animate();
        }
    }
}
