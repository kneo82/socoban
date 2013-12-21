package socoban;

import javazoom.jl.player.Player;

/**
 * Created by kneo82 on 19.12.13.
 */
public class AudioThread implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {


            while (true) {
                Player p = new Player((getClass()
                        .getClassLoader().getResourceAsStream("res/background.mp3")));
                p.play();
                p.close();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}