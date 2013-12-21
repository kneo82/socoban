package socoban;

import javazoom.jl.player.Player;

/**
 * Created by kneo82 on 21.12.13.
 */
public class AudioThreadPush implements Runnable {
    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            Player p = new Player(/*new FileInputStream*/(getClass()
                    .getClassLoader().getResourceAsStream("res/push.mp3")));
            p.play();
            p.close();

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
