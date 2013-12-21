package socoban;

import javax.swing.*;


/**
 * Created by kneo82 on 21.12.13.
 */
public class Help {
    private final static String HELP =
            "<html>\n" +
                    "\t<h1 style='padding:20;'> Rules\n" +
                    "\t</h1>\n" +
                    "\t<p style='padding:20; font-size:14'>The game is played on a board of squares, where each square is a floor or a wall. Some floor squares are marked as storage locations, and some of them have boxes.<br>\n" +
                    "\tThe player is confined to the board, and may move horizontally or vertically onto empty squares (never through walls or boxes). The player can also move into a box,\n" +
                    "\twhich pushes it into the square beyond. Boxes may not be pushed into other boxes or walls, and they cannot be pulled. The puzzle is solved when all boxes are at storage locations.</p>\n" +
                    "\t<h1 style='padding:20;'> Controls\n" +
                    "\t</h1>\n" +
                    "\t<p style='padding:20; font-size:14'> &uarr; , W - Move UP<br>\n" +
                    "\t&darr; , S - Move DOWN<br>\n" +
                    "\t&rarr; , D - Move RIGHT<br>\n" +
                    "\t&larr; , A - Move LEFT<br>\n" +
                    "\tU - UNDO Move<br>\n" +
                    "\tF1 , H - HELP<br>\n" +
                    "\tM - MUTE<br>\n" +
                    "\tR - Restart Level<br>\n" +
                    "\tL - Choose Level<br>\n" +
                    "</html>";

    public Help() {
        JFrame f = new JFrame("HELP SOCOBAN");

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel(HELP);

        f.add(label);

        f.setSize(680, 680);

        f.setResizable(false);

        f.setVisible(true);
    }

}
