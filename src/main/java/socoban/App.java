package socoban;


import javax.swing.*;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JFrame f = new JFrame("SOCOBAN");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(new Board());

        f.setSize(680, 680);

        f.setResizable(false);

        f.setVisible(true);


    }
}
