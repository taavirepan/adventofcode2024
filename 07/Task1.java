import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

class Task1 {
    static boolean solve(BigInteger target, BigInteger[] numbers) {
        if (numbers.length == 1) {
            return target.equals(numbers[0]);
        }

        BigInteger[] numbers2 = Arrays.copyOfRange(numbers, 1, numbers.length);
        BigInteger a = numbers[0];
        BigInteger b = numbers[1];

        numbers2[0] = new BigInteger(a.toString() + b.toString());
        if (numbers2[0].compareTo(target) <= 0 && solve(target, numbers2)) {
            return true;
        }

        numbers2[0] = a.multiply(b);
        if (numbers2[0].compareTo(target) <= 0 && solve(target, numbers2)) {
            return true;
        }
        numbers2[0] = a.add(b);
        return numbers2[0].compareTo(target) <= 0 && solve(target, numbers2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        BigInteger task1 = BigInteger.ZERO;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            BigInteger[] list = pattern.matcher(line).results().map(MatchResult::group).map(BigInteger::new).toArray(BigInteger[]::new);
            BigInteger target = list[0];
            BigInteger[] rest = Arrays.copyOfRange(list, 1, list.length);
            if (solve(target, rest)) {
                task1 = task1.add(target);
            }
        }
        System.out.println(task1);
    }
}
