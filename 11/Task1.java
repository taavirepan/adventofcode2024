import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task1 {

    public static List<BigInteger> blink(List<BigInteger> numbers) {
        List<BigInteger> newNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            BigInteger n = numbers.get(i);
            String s = n.toString();

            if (n.equals(BigInteger.ZERO)) {
                newNumbers.add(BigInteger.ONE);
            }
            else if (s.length() % 2 == 0) {
                int h = s.length() / 2;
                BigInteger a = new BigInteger(s.substring(0, h));
                BigInteger b = new BigInteger(s.substring(h));
                newNumbers.add(a);
                newNumbers.add(b);
            }
            else {
                newNumbers.add(n.multiply(BigInteger.valueOf(2024)));
            }
        }
        return newNumbers;
    }

    public static void main(String[] args) {
        List<BigInteger> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            numbers.add(BigInteger.valueOf(scanner.nextInt()));
        }
        for (int i = 0; i < 25; i++) {
            numbers = blink(numbers);
        }

        System.out.println(numbers.size());
    }
}
