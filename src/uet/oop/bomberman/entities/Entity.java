package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.FPS;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;


    public static final double SPEED = 165/FPS;

    public static final double MOVECROSSSPEED = SPEED / Math.sqrt(2);

    public boolean isRemove() {
        return remove;
    }

    protected boolean remove = false;

    public double getX() {
        return (double)x/Sprite.SCALED_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }


    public double getY() {
        return (double) y/Sprite.SCALED_SIZE;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

}