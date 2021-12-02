package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;


import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.*;
import static uet.oop.bomberman.graphics.Sprite.bomb;


public class Bomber extends AnimatedEntitiy {

    private int time = BombermanGame.FPS*3;

    private List<Bomb> bombs = new ArrayList<>();

    private boolean up, down, left, right;
    private boolean die = false;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(!die) {
            chooseImage();
            animate();
            move();
            IsDeath();
        } else {
            BombermanGame.endgame = true;
            if(time>0) {
                time--;
                chooseImageDeath(time);
                animate();
            } else {
                remove = true;
            }
        }
    }


    public void bombermove(KeyCode e, boolean isPressed) {
        switch (e) {
            case W: up = isPressed; break;
            case S: down = isPressed; break;
            case A: left = isPressed; break;
            case D: right = isPressed; break;
        }
    }

    public void move() {
        double dx = SPEED;
        double dy = SPEED;
        if(!canhmove()) {
            dy = 0;
        }
        if(!canvmove()) {
            dx = 0;
        }
        if (up) y -= dy;
        if (down) y += dy;
        if (left) x -= dx;
        if (right) x += dx;
    }

    private boolean canhmove() {
        if (up) return !checkBlock((int)(getX()+0.75), (int)(getY()-0.05)) && !checkBlock((int)getX(), (int)(getY()-0.05));
        if (down) return !checkBlock((int)(getX()+0.75), (int)(getY()+1)) && !checkBlock((int)(getX()), (int)(getY()+1));
        return true;
    }

    private boolean canvmove() {
        if (left) return !checkBlock((int)(getX()-0.05), (int)getY()) && !checkBlock((int)(getX()-0.05), (int)(getY()+0.95));
        if (right) return !checkBlock((int)(getX()+0.8), (int)getY()) && !checkBlock((int)(getX()+0.8), (int)(getY()+0.95));
        return true;
    }

    private boolean checkBlock(int x,int y) {
        if(BombermanGame.getBrickAt(x,y)) return true;
        if(BombermanGame.getWallkAt(x,y)) return true;
        return false;
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (up) {
                Sprite sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animate, BombermanGame.FPS/4);
                this.setImg(sprite.getFxImage());
            }
            if (down) {
                Sprite sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animate, BombermanGame.FPS/4);
                this.setImg(sprite.getFxImage());
            }
            if (left) {
                Sprite sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animate, BombermanGame.FPS/4);
                this.setImg(sprite.getFxImage());
            }
            if (right) {
                Sprite sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animate, BombermanGame.FPS/4);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    public boolean checkDeath(int x, int y) {
        if(BombermanGame.getFlameAt(x,y)) return true;
        if(BombermanGame.getMobAt(x,y)) return true;
        return false;
    }

    public void IsDeath() {
        if(checkDeath((int)getX(),(int)getY())) die = true;
        if(checkDeath((int)(getX()+0.7),(int)getY())) die = true;
        if(checkDeath((int)getX(),(int)(getY()+0.95))) die = true;
        if(checkDeath((int)(getX()+0.7),(int)(getY()+0.95))) die = true;
    }

    public void chooseImageDeath(int time) {
        set_animate(0);
        Sprite sprite = Sprite.movingSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead1, time, BombermanGame.FPS*3);
        this.setImg(sprite.getFxImage());
    }


    public void placeBomb() {
        Bomb bomb =new Bomb((int)Math.round(getX()), (int)Math.round(getY()), Sprite.bomb.getFxImage());
            if(BombermanGame.getAt((int)Math.round(getX()), (int)Math.round(getY())) == null && !die)
                BombermanGame.entities.add(bomb);
    }
}