/**
 * Created by kitty on 29.05.2017.
 */
public class Fork {
    boolean taken = false;
    int occupiedBy = -1;
    final int forkNumber;

    Fork (int i) {
        forkNumber = i;
    }

    public synchronized boolean  take(int id) {
        if (!taken) {
            occupiedBy = id;
            taken = true;
//            System.out.println("Fork number " + forkNumber + " occupied by " + id);
            return true;
        }
//        System.out.println(id + " failed to occupy fork number " + forkNumber);
        return false;
    }
    public synchronized  void drop() {
        taken = false;
//        System.out.println(occupiedBy + " dropped fork number " + forkNumber);
        occupiedBy = -1;

    }
}
