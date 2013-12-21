package socoban;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by kneo82 on 14.12.13.
 */

public class Board extends JPanel {
    private Level level;
    private Image pixInfo = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/gameinfo.png")).getImage();
    private Image pixWall = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/brick.png")).getImage();
    private Image pixBox = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/crate.jpg")).getImage();
    private Image pixGround = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/fill.png")).getImage();
    private Image pixSpotFree = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/spot_free.png")).getImage();
    private Image pixSpotTaken = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/spot_taken.png")).getImage();
    private Image pixMe = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/me_Front.png")).getImage();
    private Image pixMeFront = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/me_Front.png")).getImage();
    private Image pixMeBack = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/me_back.png")).getImage();
    private Image pixMeRight = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/me_right.png")).getImage();
    private Image pixMeLeft = new ImageIcon(getClass()
            .getClassLoader()
            .getResource("res/me_left.png")).getImage();
    private int startX = -1, startY = -1;
    private int sizeSprite = 32;
    private Labirints labs;
    private int levelN = 0;

    private boolean mute = false;

    Thread audioThread = new Thread(new AudioThread());

    public Board() {
        Loader l = new Loader();
        labs = l.getLabs();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        levelN = 0;
        level = new Level(labs, levelN);

        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        setVolume(0.1f);
        audioThread.start();
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(this.getVisibleRect().x, this.getVisibleRect().y, this.getVisibleRect().width, this.getVisibleRect().height);
        if (startX < 0 && startY < 0) {
            startX = (this.getVisibleRect().width / 2 - (level.getLev()[0].length / 2) * sizeSprite) + this.getVisibleRect().x;
            startY = (this.getVisibleRect().height / 2 - (level.getLev().length / 2) * sizeSprite) + this.getVisibleRect().y;

        }


        for (int i = 0; i < level.getLev()[0].length; i++) {
            for (int j = 0; j < level.getLev().length; j++) {
                int x = startX + i * sizeSprite;
                int y = startY + j * sizeSprite;
                switch (level.getLev()[j][i]) {
                    case Level.WALL:
                        g.drawImage(pixWall, x, y, null);
                        break;
                    case Level.GROUND:
                        g.drawImage(pixGround, x, y, null);
                        break;
                    case Level.EMPTY:
                        break;
                    case Level.LOADER:
                        g.drawImage(pixGround, x, y, null);
                        g.drawImage(pixMe, x, y, null);
                        break;
                    case Level.BOX:
                        g.drawImage(pixBox, x, y, null);
                        break;
                    case Level.SPOT_FREE:
                        g.drawImage(pixGround, x, y, null);
                        g.drawImage(pixSpotFree, x, y, null);
                        break;
                    case Level.SPOT_TAKEN:
                        g.drawImage(pixBox, x, y, null);
                        g.drawImage(pixSpotTaken, x, y, null);
                        break;

                }
                g.drawImage(pixInfo, 0, 0, null);
                g.setColor(Color.BLACK);
                Font font = new Font(Font.SERIF, Font.BOLD, 26);
                g.setFont(font);
                g.drawString("" + level.getMoveN(), 420, 40);
                g.drawString("" + level.getLevelN(), 100, 40);
                g.drawString("" + level.getPushesN(), 570, 40);
            }
        }

    }

    private void setVolume(float gain) {
        Port.Info source = Port.Info.SPEAKER;
        //        source = Port.Info.LINE_OUT;
        //        source = Port.Info.HEADPHONE;

        if (AudioSystem.isLineSupported(source)) {
            try {
                Port outline = (Port) AudioSystem.getLine(source);
                outline.open();
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                volumeControl.setValue(gain);
            } catch (LineUnavailableException ex) {
                System.err.println("source not supported");
                ex.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        /**
         * Invoked when a key has been typed.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key typed event.
         */
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_R:
                    level = new Level(labs, levelN);
                    startX = -1;
                    startY = -1;
                    break;
                case KeyEvent.VK_L:

                    String s = (String) JOptionPane.showInputDialog(null, "Введите номер уровня (0-" + (labs.getSize() + 1) + "):", "Уровень", JOptionPane.OK_CANCEL_OPTION, null, null, levelN + 1);
                    int n = levelN + 1;
                    if (s != null) {
                        try {
                            n = Integer.parseInt(s);
                        } catch (NumberFormatException el) {
                            break;
                        }
                    } else break;
                    if (n - 1 < 0 || n > labs.getSize()) break;
                    levelN = n - 1;
                    level = new Level(labs, levelN);
                    startX = -1;
                    startY = -1;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    level.moveDown();
                    pixMe = pixMeFront;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    level.moveUp();
                    pixMe = pixMeBack;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    level.moveLeft();
                    pixMe = pixMeLeft;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    level.moveRight();
                    pixMe = pixMeRight;
                    break;
                case KeyEvent.VK_U:
                    level.undoMove();
                    break;
                case KeyEvent.VK_M:
                    if (!mute)
                        setVolume(0);
                    else setVolume(.1f);
                    mute = !mute;
                    break;
                case KeyEvent.VK_F1:
                case KeyEvent.VK_H:
                    new Help();
                    break;
            }
            repaint();
            if (level.isWin()) {
                if (levelN < labs.getSize()) {
                    levelN++;
                    level = new Level(labs, levelN);
                    startX = -1;
                    startY = -1;
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Вы Выиграли!!!");
                    System.exit(1);
                }

            }
        }
    }
}
