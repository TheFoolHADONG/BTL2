package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Mob {

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (up) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (down) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (left) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (right) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    @Override
    public void update() {
        if(!die) {
            animate();
            IsDeath();
        } else {
            setImg(Sprite.oneal_dead.getFxImage());
            if(timedie>0) {
                timedie--;
            } else remove = true;
            if(timedie<BombermanGame.FPS) {
                chooseImageDeath(timedie+BombermanGame.FPS);
            }
            animate();
        }
    }
}
