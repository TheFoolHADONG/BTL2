package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Bomb extends AnimatedEntitiy {
    protected double _timeToExplode = BombermanGame.FPS*2;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (_timeToExplode > 0 && !(BombermanGame.getFlameAt((int)getX(),(int)getY()))) {
            _timeToExplode--;
            chooseImage();
            animate();
        } else {
            explode();
        }
    }



    public void explode() {
        makeFlames();
        remove = true;
    }

    public void makeFlames() {

        BombermanGame.flames.add(new Flame((int) getX(), (int) getY(), Sprite.bomb_exploded.getFxImage(), 0));

        for (int i = 1; i <= 1; i++) {
            Flame flametop = new Flame((int) getX(), (int) getY() - i, Sprite.explosion_vertical.getFxImage(), 1);
            Flame flamedown = new Flame((int) getX(), (int) getY() + i, Sprite.explosion_horizontal.getFxImage(), 2);
            Flame flameleft = new Flame((int) getX() - i, (int) getY(), Sprite.explosion_horizontal_left_last.getFxImage(), 3);
            Flame flameright = new Flame((int) getX() + i, (int) getY(), Sprite.explosion_horizontal_right_last.getFxImage(), 4);
            BombermanGame.flames.add(flametop);
            BombermanGame.flames.add(flamedown);
            BombermanGame.flames.add(flameleft);
            BombermanGame.flames.add(flameright);
        }
    }


    @Override
    public void chooseImage() {
        Sprite sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, BombermanGame.FPS/2);
        this.setImg(sprite.getFxImage());
    }



}
