import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 01.06.2017.
 */
public class Solution {
    private static boolean F;
    private static int peopleInside; //same as free state
    private static final Semaphore TABLE_SEMAPHORE = new Semaphore(1, true);

    public static void main(String[] args) {
        peopleInside = 0;

        Thread womanProducer = new Thread(){
            public void run(){
                int i = 0;
                while (true) {
                    Thread newWoman = new Woman(i++);
                    newWoman.start();
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread manProducer = new Thread(){
            public void run(){
                int i = 0;
                while (true) {
                    Thread newMan = new Man(i++);
                    newMan.start();
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        womanProducer.start();
        manProducer.start();
    }


    private static boolean womanWantsToEnter() {
        try {
            TABLE_SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(peopleInside == 0) {
            peopleInside = 1;
            F = true;
            TABLE_SEMAPHORE.release();
            return true;
        }
        else if (F == true) {
            peopleInside++;
            TABLE_SEMAPHORE.release();
            return  true;
        }
        TABLE_SEMAPHORE.release();
        return  false;
    }

    private static boolean manWantsToEnter() {
        try {
            TABLE_SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(peopleInside == 0) {
            peopleInside = 1;
            F = false;
            TABLE_SEMAPHORE.release();
            return true;
        }
        else if (F == false) {
            peopleInside++;
            TABLE_SEMAPHORE.release();
            return  true;
        }
        TABLE_SEMAPHORE.release();
        return  false;
    }

    private static void womanLaeves() {
        try {
            TABLE_SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        peopleInside--;
        if (F!=true)//shout
            System.out.println("AAAAAAAAAAA!");
        TABLE_SEMAPHORE.release();
    }

    private static void manLeaves() {
        try {
            TABLE_SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        peopleInside--;
        if (F!=false)
            System.out.println("fap-fap-fap!");
        TABLE_SEMAPHORE.release();
    }

    public static class Woman extends Thread {
        int id;
        Woman(int id){
            this.id = id;
        }
        @Override
        public void run() {
            System.out.println("Woman " + id + " enters hall");
            while (!womanWantsToEnter()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 10); //Wait a bit more
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Woman " + id + " comes in");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 1000); //come in
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Woman " + id + " leaves");
            womanLaeves();
        }
    }

    public static class Man extends Thread {
        int id;
        Man(int id){
            this.id = id;
        }
        @Override
        public void run() {
            System.out.println("Man " + id + " enters hall");
            while (!manWantsToEnter()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 10); //Wait a bit more
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Man " + id + " comes in");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 500); //come in
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Man " + id + " leaves");
            manLeaves();
        }
    }
}
