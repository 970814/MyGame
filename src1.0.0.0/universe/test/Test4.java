package universe.test;


import com.sun.media.jfxmedia.MediaManager;
import javafx.scene.media.AudioClip;


/**
 * Created by Administrator on 2016/4/20.
 */
public class Test4 {
    public static void main(String[] args) {
        String[] ts = MediaManager.getSupportedContentTypes();
        for (int i = 0; i < ts.length; i++) {
            System.out.println(ts[i]);
        }

        AudioClip audioClip = new AudioClip("file:///C:/Users/Administrator/IdeaProjects/untitled/a.wav");
        System.out.println(audioClip.isPlaying());
        audioClip.play();
        System.out.println(audioClip.getVolume());
        System.out.println(audioClip.isPlaying());
        audioClip.balanceProperty();
        audioClip.play();
    }
}
