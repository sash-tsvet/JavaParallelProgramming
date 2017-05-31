import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 30.05.2017.
 */
public class Solution {
    private static final Semaphore Tobacco_busy_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore Paper_busy_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore Matches_busy_SEMAPHORE = new Semaphore(1, true);
//    private static final Semaphore Tobacco_SEMAPHORE = new Semaphore(1, true);
//    private static final Semaphore Paper_SEMAPHORE = new Semaphore(1, true);
//    private static final Semaphore Matches_SEMAPHORE = new Semaphore(1, true);

    public static void main(String[] args) {
        Person[] smokers = new Person[3];
        Item[] items = new Item[3];
        for (int i = 0; i < 3; i++) {
            items[i] = new Item();
        }
        smokers[0] = new TobaccoPerson(items, 0);
        smokers[1] = new PaperPerson(items, 1);
        smokers[2] = new MatchesPerson(items, 2);

        new Thread(smokers[0]).start();
        new Thread(smokers[1]).start();
        new Thread(smokers[2]).start();

        while(true) {
            if(!items[0].free()&&!items[1].free()&&!items[2].free()) {
                int i = ThreadLocalRandom.current().nextInt(0, 3);
                smokers[i].giveItem();
                smokers[(i + 1) % 3].giveItem();
            }
        }
    }

    public static class TobaccoPerson implements Person {
        Item[] items;
        int id;

        TobaccoPerson(Item[] items, int id){
            this.items = items;
            this.id = id;
        }

        @Override
        public void giveItem(){
            try {
                Tobacco_busy_SEMAPHORE.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            items[id].put();
            System.out.println("Tobacco on the table");
            Tobacco_busy_SEMAPHORE.release();
        }

        @Override
        public void smoke() {
            try {
                Tobacco_busy_SEMAPHORE.acquire();
//                items[(id+1)%3].take();
//                items[(id+2)%3].take();
                System.out.println("TobaccoPerson smoking..." + (items[(id + 1) % 3].take() && items[(id + 2) % 3].take()));
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Tobacco_busy_SEMAPHORE.release();
        }

        @Override
        public void run() {
            while (true) {
                if (items[(id + 1) % 3].free() && items[(id + 2) % 3].free()) {
                    this.smoke();
                }
            }
        }
    }

    public static class PaperPerson implements Person {
        Item[] items;
        int id;

        PaperPerson(Item[] items, int id){
            this.items = items;
            this.id = id;
        }


        @Override
        public void giveItem(){
            try {
                Paper_busy_SEMAPHORE.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            items[id].put();
            System.out.println("Paper on the table");
            Paper_busy_SEMAPHORE.release();
        }

        @Override
        public void smoke() {
            try {
                Paper_busy_SEMAPHORE.acquire();
                System.out.println("PaperPerson smoking..." + (items[(id + 1) % 3].take() && items[(id + 2) % 3].take()));
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Paper_busy_SEMAPHORE.release();
        }

        @Override
        public void run() {
            while (true) {
                if (items[(id + 1) % 3].free() && items[(id + 2) % 3].free()) {
                    this.smoke();
                }
            }
        }
    }

    public static class MatchesPerson implements Person{
        Item[] items;
        int id;

        MatchesPerson(Item[] items, int id){
            this.items = items;
            this.id = id;
        }


        @Override
        public void giveItem(){
            try {
                Matches_busy_SEMAPHORE.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            items[id].put();
            System.out.println("Matches on the table");
            Matches_busy_SEMAPHORE.release();
        }

        @Override
        public void smoke() {
            try {
                Matches_busy_SEMAPHORE.acquire();
                System.out.println("MatchesPerson smoking..." + (items[(id + 1) % 3].take() && items[(id + 2) % 3].take()));
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Matches_busy_SEMAPHORE.release();
        }

        @Override
        public void run() {
            while (true) {
                if (items[(id + 1) % 3].free() && items[(id + 2) % 3].free()) {
                    this.smoke();
                }
            }
        }
    }

}
