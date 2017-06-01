import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 01.06.2017.
 */
public class Server {
    private int startByte;
    private int endByte;

    Server(int startByte, int endByte) {
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }

    public synchronized void download (Task inTask) {
        if ((inTask.getStartByte() < this.startByte) || (inTask.getEndByte() > this.endByte)) {
            System.out.println("Out of range error");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            System.out.println("Downloading bytes " + inTask.getStartByte() + " to " + inTask.getEndByte());
            Thread.sleep((inTask.getEndByte() - inTask.getStartByte()) * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
