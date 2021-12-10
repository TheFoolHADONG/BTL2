package uet.oop.bomberman.graphics;


import javafx.scene.image.Image;


public class ImageLoader {

    Image image;

    public static ImageLoader Pause = new ImageLoader("Pause",Sprite.DEFAULT_SIZE*10,Sprite.DEFAULT_SIZE*10);
    public static ImageLoader Icon = new ImageLoader("Icon",Sprite.DEFAULT_SIZE*10,Sprite.DEFAULT_SIZE*10);

    public ImageLoader(String name, int width, int height) {
        image = new Image("/image/" + name + ".png",width,height,false,false);
    }
    public ImageLoader(String name) {
        image = new Image("/image/" + name + ".png");
    }

    public Image getImage() {
        return image;
    }
}
