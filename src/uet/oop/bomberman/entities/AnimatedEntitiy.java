package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntitiy extends Entity {
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntitiy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void chooseImage();

    public int get_animate() {
        return _animate;
    }

    public void set_animate(int _animate) {
        this._animate = _animate;
    }

    public void animate() {
        chooseImage();
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }
}
