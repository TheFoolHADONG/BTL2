package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.graphics.Sprite;


import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.*;
import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.Sound.SoundPlayer.stage_theme;
import static uet.oop.bomberman.Sound.SoundPlayer.theme;
import static uet.oop.bomberman.graphics.Sprite.bomb;


public class Bomber extends AnimatedEntitiy {

    private int time = BombermanGame.FPS*3;

    private int maxBombs = 1;

    private boolean up, down, left, right;
    private boolean die = false;
    private boolean music = false;

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
            if(win) {
                if(getPortalAt(Math.round(getX()),Math.round(getY()))) iswin = true;
            }
        } else {
            if(!music) {
                theme.stop();
                //SoundPlayer.play(stage_theme);
                music = true;
            }
            if(time>0) {
                time--;
                chooseImageDeath(time);
                animate();
            } else {
                lose = true;
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
        if (up) return !checkBlock((int)(getX()+0.7), (int)(getY()+0.15)) && !checkBlock((int)getX(), (int)(getY()+0.15));
        if (down) return !checkBlock((int)(getX()+0.7), (int)(getY()+1)) && !checkBlock((int)(getX()), (int)(getY()+1));
        return true;
    }

    private boolean canvmove() {
        if (left) return !checkBlock((int)(getX()-0.05), (int)(getY()+0.2)) && !checkBlock((int)(getX()-0.05), (int)(getY()+0.99));
        if (right) return !checkBlock((int)(getX()+0.8), (int)(getY()+0.2)) && !checkBlock((int)(getX()+0.8), (int)(getY()+0.99));
        return true;
    }


    public boolean checkBlock(int x,int y) {
        if(BombermanGame.getBrickAt(x,y)) return true;
        if(BombermanGame.getWallAt(x,y)) return true;
        if(BombermanGame.getPlayerBombAt(x,y)) return true;
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

    public void IsDeath() {
        if(intersects(BombermanGame.getAllMob())) die = true;
        if(intersects(flames)) die = true;
    }

    public void chooseImageDeath(int time) {
        set_animate(0);
        Sprite sprite = Sprite.movingSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead1, time, BombermanGame.FPS*3);
        this.setImg(sprite.getFxImage());
    }

    public void placeBomb() {
        Bomb bomb =new Bomb((int)Math.round(getX()), (int)Math.round(getY()), Sprite.bomb.getFxImage());
            if(!BombermanGame.getBombAt((int)Math.round(getX()), (int)Math.round(getY())) && !die)
                BombermanGame.entities.add(bomb);
    }
}