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

    public synchronized void download (int startByte, int endByte) {
        if ((startByte > this.startByte) || (endByte > this.endByte)) {
            System.out.println("Out of range error");
            return;
        }
        try {
            Thread.sleep((endByte - startByte) * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
