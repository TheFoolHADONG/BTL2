package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Mob extends AnimatedEntitiy {

    protected int timedie = BombermanGame.FPS*2;

    protected boolean up, down, left , right;
    protected boolean die = false;

    public boolean isDie() {
        return die;
    }

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    public void chooseImageDeath(int time) {
        Sprite sprite = Sprite.movingSprite(Sprite.mob_dead3, Sprite.mob_dead2, Sprite.mob_dead1, time, BombermanGame.FPS);
        this.setImg(sprite.getFxImage());
    }

    public boolean checkDeath(int x, int y) {
        if(BombermanGame.getFlameAt(x,y)) return true;
        return false;
    }

    public void IsDeath() {
        if(checkDeath((int)getX(),(int)getY())) die = true;
        if(checkDeath((int)(getX()+0.95),(int)getY())) die = true;
        if(checkDeath((int)getX(),(int)(getY()+0.95))) die = true;
        if(checkDeath((int)(getX()+0.95),(int)(getY()+0.95))) die = true;
    }
}
