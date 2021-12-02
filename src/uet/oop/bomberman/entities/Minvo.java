package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Mob {

    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (up) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (down) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (left) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (right) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, BombermanGame.FPS/2);
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
            setImg(Sprite.minvo_dead.getFxImage());
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
