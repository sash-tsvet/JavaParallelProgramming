import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 29.05.2017.
 */
public class Philosopher implements Runnable {
    static int numberOfSleepThreads = 0;
    private int id;
    private Fork leftFork, rightFork;

    Philosopher(Fork left, Fork right, int number) {
        leftFork = left;
        rightFork = right;
        id = number;
    }

    private void dropAll() {
        if (rightFork.occupiedBy == id)
            rightFork.drop();
        if (leftFork.occupiedBy == id)
            leftFork.drop();
    }

    public void run() {
        while (true) {
            leftFork.take(id);
            rightFork.take(id);

            if ((leftFork.occupiedBy == id) && (rightFork.occupiedBy == id)) {
                System.out.println("Successful meal by " + id);
                try {
                    Thread.sleep(1); //We need some time to eat
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dropAll();

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1)*100); //Now sleep for some time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
//                System.out.println(id + " Failed to eat");
                dropAll();
            }
        }
    }
}
