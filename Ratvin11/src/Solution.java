import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 31.05.2017.
 */
public class Solution {
    private static int[] input;
    private static int len;
    private static int maxval = 50;
    private static int numChunks = 5;
    private static boolean solved = false;

    public static void main(String[] args) {
        len = 20;
        input = new int[len];
        for (int i = 0; i < len; i++) {
            input[i] = ThreadLocalRandom.current().nextInt(0, maxval);
        }
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");
        }
        System.out.println();
        asort();
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");
        }
        System.out.println();

        for (int i = 0; i < len; i++) {
            input[i] = ThreadLocalRandom.current().nextInt(0, maxval);
        }
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");
        }
        System.out.println();
        bsort();
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");

        }
        System.out.println();

        len = 12;
        for (int i = 0; i < len; i++) {
            input[i] = ThreadLocalRandom.current().nextInt(0, maxval);
        }
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");
        }
        System.out.println();
        csort();
        for (int i = 0; i < len; i++) {
            System.out.print(input[i]+ " ");

        }
        System.out.println();
    }

    private static void swapa(int i, int j){
        int c = input[i];
        input[i] = input [j];
        input[j] = c;
    }

    public static void asort(){
        Thread[] thr1 = new Thread[(int)len/2];
        Thread[] thr2 = new Thread[(int)len/2];
        int count1;
        int count2;

        for(int i = 0; i<len-1;i++){
            if(i % 2 == 0){ // i even
                count1 = 0;

                for(int j = 0; j<len/2; j++){
                    final int tmp = j;
                    count1++;
                    thr1[tmp] = new Thread(){
                        public void run(){
                            if (input[2*tmp]>input[2*tmp+1])
                                swapa(2*tmp,2*tmp+1);
                        }
                    };
                    thr1[tmp].start();
                }
                //waiting for threads to finish
                for(int m = 0; m<count1; m++){
                    try {
                        thr1[m].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }}
            }
            else{ // i odd
                count2 = 0;
                for(int k = 0; k<len/2-1;k++){
                    final int tmp = k;
                    count2++;
                    thr2[tmp] = new Thread(){
                        public void run(){
                            if (input[2*tmp+1]>input[2*tmp+2])
                                swapa(2*tmp+1,2*tmp+2);
                        }
                    };
                    thr2[tmp].start();
                }
                // Waiting for threads to finish
                for(int m = 0; m<count2; m++){
                    try {
                        thr2[m].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }}
        }
    }

    public static void bsort(){
        Thread[] thr = new Thread[numChunks];
        SortedSet[] set = new SortedSet[numChunks];
        for (int i = 0; i < numChunks; i++) {
           set[i]= new TreeSet();
        }

        for(int i = 0; i<numChunks;i++) {
            final int tmp = i;
            thr[tmp] = new blocksorter(tmp, set[tmp]);
            thr[tmp].start();
        }
            // Waiting for threads to finish
        for(int m = 0; m<numChunks; m++) {
            try {
                thr[m].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int index = 0;
        for(int m = 0; m<numChunks; m++){
            Iterator it = set[m].iterator();

            while (it.hasNext()) {
                // Get element
                input[index++] = (int) it.next();
                //System.out.println(element.toString());
            }
        }
    }

    public static class blocksorter extends Thread {
        int id;
        SortedSet myDuty;
        blocksorter(int id, SortedSet set) {
            this.id = id;
            myDuty = set;
        }
        @Override
        public void run() {
            for(int j = 0; j<len; j++) {
                if ((input[j] >= (maxval / numChunks)*id) &&
                        (input[j] < (maxval / numChunks)*(id+1)))
                    myDuty.add(input[j]);
            }
        }
    }
    public static void csort(){
        Thread[] thr = new Thread[numChunks];

        for(int i = 0; i<numChunks;i++) {
            final int tmp = i;
            thr[tmp] = new bogosorter();
            thr[tmp].start();
        }
        // Waiting for threads to finish
        for(int m = 0; m<numChunks; m++) {
            try {
                thr[m].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class bogosorter extends Thread {
        int[] array;
        @Override
        public void run() {
            array = new int[len];
            for (int i = 0; i < len; i++)
                array [i] = input[i];
            while (!solved){
                for (int i = 0; i < len; i++)
                    swap(i, ThreadLocalRandom.current().nextInt(0, len));
                if(check()) {
                    solved = true;
                    input = array;
                }
            }
        }

        private void swap(int i, int j){
            int c = array[i];
            array[i] = array [j];
            array[j] = c;
        }

        private boolean check(){
            for (int j = 0; j < len-1; j++) {
                if(array[j] > array[j+1])
                    return false;
            }
            return true;
        }
    }
}

