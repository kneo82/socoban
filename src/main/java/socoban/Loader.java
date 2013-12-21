package socoban;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by kneo82 on 16.12.13.
 */
public class Loader {

    private String fileName = "res/ClassicQuest.txt";
    private ArrayList<String> fileText = new ArrayList<String>();
    private Labirints labs = new Labirints();

    public Labirints getLabs() {
        return labs;
    }

    public Loader() {
        DataInputStream dis = new DataInputStream(getClass().getClassLoader().getResourceAsStream(fileName));
        StringBuffer strBuff = new StringBuffer();
        int ch = 0;
        try {
            while ((ch = dis.read()) != -1) {
                strBuff.append((char) ((ch >= 0xc0 && ch <= 0xFF) ? (ch + 0x350) : ch));
            }
            dis.close();
        } catch (Exception e) {
            System.err.println("ERROR in getText() " + e);
        }
        String[] s = strBuff.toString().split("\r\n");
        for (int i = 0; i < s.length; i++) {
            if (s[i].isEmpty()) continue;
            fileText.add(s[i]);
        }
        parseLabirints();
    }


    private void parseLabirints() {
        int start = 0;
        int end = 0;
        int i = 0;
        for (String str : fileText) {
            if (str.toLowerCase().contains("Level".toLowerCase())) {
                end = i;
                int max = 0;
                for (int k = start; k < end; k++) {
                    if (max < fileText.get(k).length()) max = fileText.get(k).length();
                }
                char[][] lab = new char[end - start][max];
                int p = 0;
                for (int k = start; k < end; k++) {
                    String strLine = fileText.get(k);
                    if (strLine.length() < max) {
                        int b = max - strLine.length();
                        String c = "";
                        for (int q = 0; q < b; q++) c += " ";
                        strLine = strLine + c;
                    }
                    lab[p] = strLine.toCharArray();
                    p++;
                }
                normalize(lab);
                Labirint l = new Labirint(fileText.get(end), lab);
                labs.addLabirint(l);
                start = end = end + 1;
            }
            i++;
        }
        Labirint labirint = labs.getLabirint(0);
    }

    private void normalize(char[][] lab) {
        for (int y = 0; y < lab.length; y++) {
            for (int x = 0; x < lab[0].length; x++) {
                br:
                if (lab[y][x] == Level.GROUND) {
                    int dX, dY;
                    for (dX = x; dX >= 0; dX--) {
                        if (lab[y][dX] == Level.WALL) break;
                        if (dX <= 0) {
                            lab[y][x] = Level.EMPTY;
                            break br;
                        }
                    }
                    for (dX = x; dX < lab[0].length; dX++) {
                        if (lab[y][dX] == Level.WALL) break;
                        if (dX >= lab[0].length - 1) {
                            lab[y][x] = Level.EMPTY;
                            break br;
                        }
                    }
                    for (dY = y; dY >= 0; dY--) {
                        if (lab[dY][x] == Level.WALL) break;
                        if (dY <= 0) {
                            lab[y][x] = Level.EMPTY;
                            break br;
                        }
                    }
                    for (dY = y; dY < lab.length; dY++) {
                        if (lab[dY][x] == Level.WALL) break;
                        if (dY >= lab.length - 1) {
                            lab[y][x] = Level.EMPTY;
                            break br;
                        }
                    }
                }
            }
        }
    }

}
