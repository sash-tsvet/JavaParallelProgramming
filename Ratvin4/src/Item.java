/**
 * Created by kitty on 31.05.2017.
 */
public class Item {
    boolean onTable = false;

    public synchronized boolean take() {
        if (onTable) {
            onTable = false;
            return true;
        }
        return false;
    }

    public synchronized boolean free() {
        return onTable;
    }

    public synchronized  void put() {
        onTable = true;
    }
}
