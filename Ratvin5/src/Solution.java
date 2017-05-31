import java.util.stream.IntStream;
import java.math.BigInteger;

/**
 * Created by kitty on 31.05.2017.
 */
public class Solution {
    private int n;

    public static void main(String[] args) {
        System.out.println(factorial(27));
    }

    public static BigInteger factorial(int n) {
        if(n < 2) return BigInteger.valueOf(1);
        return IntStream.rangeClosed(2, n).parallel().mapToObj(BigInteger::valueOf).reduce(BigInteger::multiply).get();
    }
}
