package socoban;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by kneo82 on 15.12.13.
 */
public class Level {
    /*
            '#"  - Wall
            '.' - Ground
            '@' - Loader
            '&' - Box
            '*' - Place
            '$' - Box On Place
            'Space' - Empty Place
     */

    public final static char WALL = '#';
    public final static char GROUND = ' ';
    public final static char LOADER = '@';
    public final static char BOX = '$';
    public final static char SPOT_FREE = '.';
    public final static char SPOT_TAKEN = '*';
    public final static char EMPTY = '^';

    private char[][] lev = {
            {'^', '^', '^', '^', '#', '#', '#', '#', '#', '^', '^', '^', '^', '^', '^', '^', '^', '^', '^'},
            {'^', '^', '^', '^', '#', ' ', ' ', ' ', '#', '^', '^', '^', '^', '^', '^', '^', '^', '^', '^'},
            {'^', '^', '^', '^', '#', '$', ' ', ' ', '#', '^', '^', '^', '^', '^', '^', '^', '^', '^', '^'},
            {'^', '^', '#', '#', '#', ' ', ' ', '$', '#', '#', '^', '^', '^', '^', '^', '^', '^', '^', '^'},
            {'^', '^', '#', ' ', ' ', '$', ' ', '$', ' ', '#', '^', '^', '^', '^', '^', '^', '^', '^', '^'},
            {'#', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#', '^', '^', '^', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', ' ', '.', '.', '#'},
            {'#', ' ', '$', ' ', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '.', '.', '#'},
            {'#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '@', '#', '#', ' ', ' ', '.', '.', '#'},
            {'^', '^', '^', '^', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'^', '^', '^', '^', '#', '#', '#', '#', '#', '#', '#', '^', '^', '^', '^', '^', '^', '^', '^'}
    };


    private int moveN = 0;
    private int levelN = 1;
    private int pushesN = 0;
    private Stack<char[][]> undo;

    public int getMoveN() {
        return moveN;
    }

    public int getPushesN() {
        return pushesN;
    }

    public int getLevelN() {
        return levelN;
    }

    private ArrayList<Point> pointSpot = new ArrayList<>();
    private int coordX, coordY;

    public Level() {
        undo = new Stack<>();
        foundSpot();
        foundCoord();
        moveN = 0;
        pushesN = getPush();


    }

    public Level(Labirints l, int n) {
        undo = new Stack<>();
        lev = l.getLabirint(n).getLabirint();
        levelN = n + 1;
        foundSpot();
        foundCoord();
        moveN = 0;
        pushesN = getPush();
    }

    public char[][] getLev() {
        if (lev == null || lev.length <= 0 || lev[0].length <= 0) return null;
        char[][] tmp = new char[lev.length][lev[0].length];
        for (int i = 0; i < lev.length; i++)
            for (int j = 0; j < lev[0].length; j++)
                tmp[i][j] = lev[i][j];
        return tmp;
    }

    private void foundSpot(){
        for (int i = 0; i < lev[0].length; i++)
            for (int j = 0; j < lev.length; j++)
                if (lev[j][i] == SPOT_FREE || lev[j][i] == SPOT_TAKEN) {
                    pointSpot.add(new Point(j, i));
                }
    }
    private void foundCoord() {
        // pointSpot=new ArrayList<>();
        for (int i = 0; i < lev[0].length; i++)
            for (int j = 0; j < lev.length; j++) {
                if (lev[j][i] == LOADER) {
                    coordX = j;
                    coordY = i;
                }

            }
    }

    public boolean isWin() {
        pushesN = getPush();
        for (Point p : pointSpot) {
            if (lev[p.x][p.y] != SPOT_TAKEN) return false;
        }

        return true;
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }

    public void moveDown() {
        move(0, 1);
    }

    public void moveUp() {
        move(0, -1);
    }

    public void undoMove() {
        if (undo.size() > 0) {
            lev = undo.pop();
            moveN--;
            foundCoord();
            pushesN = getPush();
        }
    }

    private int getPush() {
        int n = 0;
        int x = 0;
        for (Point p : pointSpot) {
            if (lev[p.x][p.y] == SPOT_TAKEN) n++;

        }
        return pointSpot.size() - n;

    }

    private void move(int mY, int mX) {
        if (coordX + mX < 0 || coordX + mX >= lev.length || coordY + mY < 0 || coordY + mY >= lev[0].length) return;
        //Если впереди стена остаемся на месте
        if (lev[coordX + mX][coordY + mY] == WALL) {
            return;
        }
        //если впереди пустое пространство то идем
        if (lev[coordX + mX][coordY + mY] == GROUND || lev[coordX + mX][coordY + mY] == SPOT_FREE) {
            undo.push(getLev());
            lev[coordX + mX][coordY + mY] = LOADER;
            lev[coordX][coordY] = GROUND;
            coordY += mY;
            coordX += mX;
            moveN++;
        } else {
            //если впереди ящик то проверяем возможность его движения
            if (lev[coordX + mX][coordY + mY] == BOX || lev[coordX + mX][coordY + mY] == SPOT_TAKEN) {
                if (coordX + 2 * mX < 0 || coordX + 2 * mX >= lev.length || coordY + 2 * mY < 0 || coordY + 2 * mY >= lev[0].length)
                    return;
                if (lev[coordX + 2 * mX][coordY + 2 * mY] == BOX
                        || lev[coordX + 2 * mX][coordY + 2 * mY] == WALL
                        || lev[coordX + 2 * mX][coordY + 2 * mY] == SPOT_TAKEN) {
                    return;
                } else {
                    undo.push(getLev());
                    lev[coordX + 2 * mX][coordY + 2 * mY] = BOX;

                    lev[coordX + mX][coordY + mY] = LOADER;
                    lev[coordX][coordY] = GROUND;
                    coordY += mY;
                    coordX += mX;
                    moveN++;

                }

            }
        }

        for (Point p : pointSpot) {
            switch (lev[p.x][p.y]) {
                case BOX:
                    lev[p.x][p.y] = SPOT_TAKEN;
                    break;
                case GROUND:
                    lev[p.x][p.y] = SPOT_FREE;
                    break;
            }
        }
        Thread audioThreadPush = new Thread(new AudioThreadPush());
        audioThreadPush.start();


    }

}

