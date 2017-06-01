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

    public boolean canRunOnServer(Server server) {
        if ((server.getStartByte() <= startByte) && server.getEndByte() >= endByte)
            return true;
        if  ((server.getEndByte() >= startByte) && (server.getEndByte() <= endByte))
            return true;
        if ((server.getStartByte() <= endByte) && (server.getStartByte() >= startByte))
            return true;
        return false;
    }

    public synchronized Task taskToRun(Server server) {
        if ((server.getStartByte() <= startByte) && server.getEndByte() >= endByte)
            return this;
        if ((server.getEndByte() >= startByte) && (server.getEndByte() <= endByte))
            return new Task(startByte, server.getEndByte());
        if ((server.getStartByte() <= endByte) && (server.getEndByte() >= startByte))
            return new Task(server.getStartByte(), endByte);
        return null;
    }

    public Task restOfTask(Server server) {
        if ((server.getStartByte() <= startByte) && server.getEndByte() >= endByte)
            return null;
        if  ((server.getEndByte() >= startByte) && (server.getEndByte() <= endByte))
            return new Task(server.getEndByte()+1, endByte);
        if ((server.getStartByte() <= endByte) && (server.getStartByte() >= startByte))
            return new Task(startByte, server.getStartByte()-1);
        return this;
    }
}
