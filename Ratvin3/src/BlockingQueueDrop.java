import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDrop<E> implements Drop<E> {
    private final Queue<E> queue;

    public BlockingQueueDrop(int limit) {
        queue = new LinkedBlockingQueue<E>(limit);
    }

    @Override
    public E take() {
        return queue.poll(); //Get customer from queue
    }

    @Override
    public boolean put(E elem) {
        return queue.offer(elem); //Return true if customer added to queue successfully; else return false
    }
}
