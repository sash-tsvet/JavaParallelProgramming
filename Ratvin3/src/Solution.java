import java.util.Random;

/**
 * Created by kitty on 30.05.2017.
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        int limit = 5;
        Drop<Customer> drop = new BlockingQueueDrop<Customer>(limit);

        Thread consumer = new Thread(new CustomerConsumer(drop));
        consumer.start();

        new Thread(new CustomerProducer(drop, 0, 5)).start();
        new Thread(new CustomerProducer(drop, 1, 5)).start();
        new Thread(new CustomerProducer(drop, 2, 5)).start();
        new Thread(new CustomerProducer(drop, 3, 5)).start();
        new Thread(new CustomerProducer(drop, 4, 5)).start();

        consumer.join();
    }
}

