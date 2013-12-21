package socoban;

/**
 * Created by kneo82 on 16.12.13.
 */
public class Labirint {
    private String name;
    private char[][] lab;

    public String getName() {
        return name;
    }

    public char[][] getLabirint() {
        if (lab == null || lab.length <= 0 || lab[0].length <= 0) return null;
        char[][] tmp = new char[lab.length][lab[0].length];
        for (int i = 0; i < lab.length; i++)
            for (int j = 0; j < lab[0].length; j++)
                tmp[i][j] = lab[i][j];
        return tmp;
    }

    public Labirint(String name, char[][] lab) {
        this.name = name;
        this.lab = lab;
    }
}
