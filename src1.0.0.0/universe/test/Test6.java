package universe.test;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Test6 {
    public static void main(String[] args) {
        String ssound = "file:///C:/Users/Administrator/IdeaProjects/untitled/a.wav";
        Media sound = new Media(ssound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
