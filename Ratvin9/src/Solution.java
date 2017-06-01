import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 * Created by kitty on 01.06.2017.
 */
public class Solution {
    private static final Semaphore QUEUE_SEMAPHORE = new Semaphore(1, true);
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        int numServers = 4;
        int fileLen = 2047;
        Server[] servers = new Server[numServers];

        servers[0] = new Server(0, 2047);
        servers[1] = new Server(0, 511);
        servers[2] = new Server(0, 1023);
        servers[3] = new Server(0, 511);

        tasks = new ArrayList<>();
        for (int i = 0; i < fileLen-100; i += 100) {
            Task newTask = new Task(i, i+99);
            tasks.add(newTask);
        }
        tasks.add(new Task(fileLen - fileLen%100, fileLen));

        for (int i = 0; i < numServers; i++) {
            new Thread(new Runner(servers[i])).start();;
        }
    }

    private static class Runner extends Thread {
        Server myServer;
        boolean finished;
        Runner(Server serser) {
            myServer = serser;
            finished = false;
        }

        @Override
        public void run() {
            while (!finished) {
                Task curTask=null;
                try {
                    QUEUE_SEMAPHORE.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Iterator<Task> it = tasks.listIterator();
                if (it.hasNext()) {
                    curTask = it.next();
                    while (it.hasNext() && !curTask.canRunOnServer(myServer)) {
                        curTask = it.next();
                    }
                    it.remove();
                    if (curTask.restOfTask(myServer) != null) {
                        tasks.add(curTask.restOfTask(myServer));
                    }
                }
                QUEUE_SEMAPHORE.release();
                if ((curTask != null) && curTask.canRunOnServer(myServer)) {
                    myServer.download(curTask.taskToRun(myServer));
                }
                else {
                    finished = true;
                    System.out.println(myServer + " finished");
                }
            }
        }
    }
}
