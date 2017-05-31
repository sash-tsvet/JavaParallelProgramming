import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerProducer implements Runnable {
    private final Drop<Customer> drop;
    private final Random random = new Random();
    // customers always want to get a haircut
    boolean cunstomersWantHairCut = true;
    private final int producerNumber;
    private final int numberOfProducers;

    public CustomerProducer(Drop<Customer> drop, int num, int fullnum) {
        this.drop = drop;
        producerNumber = num;
        numberOfProducers = fullnum;
    }

    @Override
    public void run() {
        int i = producerNumber;
        while (cunstomersWantHairCut) {
            System.out.printf("customer %d walks in%n", i);
            Customer newCustomer = new Customer(i);
            i += numberOfProducers;

            if (!drop.put(newCustomer)) {
                newCustomer.goAway();
            }

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10000));
            } catch (InterruptedException e) {
            }
        }
    }

}
