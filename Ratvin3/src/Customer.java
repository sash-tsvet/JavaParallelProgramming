public class Customer {
    private int id;

    Customer(int num){
        id = num;
    }
    public void getHairCut() {
        System.out.printf("customer %d: I got my hair cut!%n", id);
    }

    public void goAway() {
        System.out.printf("customer %d: Leaves...%n", id);
    }
}
