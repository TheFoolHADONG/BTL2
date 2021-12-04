package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundPlayer {
    public static MediaPlayer theme;
    public static MediaPlayer soundeffects;

    public static String stage_theme = "stage_theme";

    public static void play(String name_sound) {
        String path = "music/" + name_sound + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        soundeffects = new MediaPlayer(media);
        soundeffects.play();
    }

    public static void playLoop(String name_sound) {
        if(theme != null) theme.stop();
        String path = "music/" + name_sound + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        theme = new MediaPlayer(media);
        theme.setOnEndOfMedia(new Runnable() {
            public void run() {
                theme.seek(Duration.ZERO);
            }
        });
        theme.play();
    }
}
