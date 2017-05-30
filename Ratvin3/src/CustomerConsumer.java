import java.util.Random;

public class CustomerConsumer implements Runnable, Barber {

    private final Drop<Customer> drop;
    boolean haveToWork = true;
    private final Random random = new Random();

    public CustomerConsumer(Drop<Customer> drop) {
        this.drop = drop;
    }

    @Override
    public void doHairCut(Customer customer) {
        System.out.println("the barber: Working...");
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
        }

        customer.getHairCut();
    }

    @Override
    public void run() {
        while (haveToWork) {
            Customer next = drop.take();
            if (next != null) {
                doHairCut(next);
            }
        }
    }
}
