package socoban;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kneo82 on 16.12.13.
 */
public class Labirints {


    private List<Labirint> labirints;

    public Labirints() {
        labirints = new ArrayList<Labirint>();
    }

    public void addLabirint(Labirint lab) {
        labirints.add(lab);
    }

    public int getSize() {
        return labirints.size();
    }

    public boolean isLabirint(String name) {
        for (Labirint l : labirints) {
            if (l.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public Labirint getLabirint(String name) {
        for (Labirint l : labirints) {
            if (l.getName().equalsIgnoreCase(name)) return l;
        }
        return null;
    }

    public Labirint getLabirint(int n) {
        if (n < labirints.size()) return labirints.get(n);
        else return null;
    }

    public List<String> getListName() {

        if (getSize() > 0) {
            List<String> names = new ArrayList<String>();
            for (Labirint l : labirints) {
                names.add(l.getName());
            }
            return names;
        }
        return null;
    }

}
