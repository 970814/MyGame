package universe.test;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/21.
 */
    public class Test5 {
    public static void main(String[] args) throws MalformedURLException {
        new File("./yrmd.wav");
        AudioClip audioClip = Applet.newAudioClip(new File("./yrmwd.wav").toURL());
        audioClip.play();
    }
}
