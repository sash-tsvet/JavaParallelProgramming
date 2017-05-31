/**
 * Created by kitty on 01.06.2017.
 */
public class Task {
    private int startByte;
    private int endByte;

    Task(int startByte, int endByte) {
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }
}
