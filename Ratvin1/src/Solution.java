/**
 * Created by kitty on 29.05.2017.
 */
public class Solution {
    public static void main (String[] args){
        final int numberOfPhilosophs = 5;
        Fork[] fork = new Fork[numberOfPhilosophs];
        for(int i=0;i<numberOfPhilosophs;i++)
            fork[i] = new Fork(i);

        Thread[] philosophsInThreads=new Thread[numberOfPhilosophs];
        for(int i=0;i<numberOfPhilosophs;i++){
            philosophsInThreads[i]=new Thread(new Philosopher(fork[i], fork[(i+1)%numberOfPhilosophs], i));
            philosophsInThreads[i].start();
        }
    }
}
