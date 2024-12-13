import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Task1 {
    static BigInteger[] transform(BigInteger[] v, BigInteger m11, BigInteger m12, BigInteger m21, BigInteger m22) {
        return new BigInteger[] { m11.multiply(v[0]).add(m12.multiply(v[1])), m21.multiply(v[0]).add(m22.multiply(v[1])) };
    }


    static BigInteger solve(BigInteger[] btnA, BigInteger[] btnB, BigInteger[] prize) {
        BigInteger[] tA = transform(btnA, btnA[0], BigInteger.ZERO, btnA[1].negate(), btnA[0]);
        BigInteger[] tB = transform(btnB, btnA[0], BigInteger.ZERO, btnA[1].negate(), btnA[0]);
        BigInteger[] tp = transform(prize, btnA[0], BigInteger.ZERO, btnA[1].negate(), btnA[0]);

        tp = transform(tp, tB[1], tB[0].negate(), BigInteger.ZERO, tB[1]);
        tA = transform(tA, tB[1], tB[0].negate(), BigInteger.ZERO, tB[1]);
        tB = transform(tB, tB[1], tB[0].negate(), BigInteger.ZERO, tB[1]);

        BigInteger r1 = tp[0].abs().mod(tA[0].abs());
        BigInteger r2 = tp[1].abs().mod(tB[1].abs());
        if (r1.compareTo(BigInteger.ZERO) != 0 || r2.compareTo(BigInteger.ZERO) != 0) {
            return BigInteger.ZERO;
        }

        return (tp[0].divide(tA[0]).multiply(BigInteger.valueOf(3))).add(tp[1].divide(tB[1]));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern numbers = Pattern.compile("\\d+");
        BigInteger[] btnA = new BigInteger[2];
        BigInteger[] btnB = new BigInteger[2];
        BigInteger[] prize = new BigInteger[2];
        BigInteger task1 = BigInteger.ZERO;
        BigInteger task2 = BigInteger.ZERO;
        BigInteger offset = new BigInteger("10000000000000");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Button A")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                btnA[0] = new BigInteger(matcher.group());
                matcher.find();
                btnA[1] = new BigInteger(matcher.group());
            }
            else if (line.startsWith("Button B")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                btnB[0] = new BigInteger(matcher.group());
                matcher.find();
                btnB[1] = new BigInteger(matcher.group());
            }
            else if (line.startsWith("Prize")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                prize[0] = new BigInteger(matcher.group());
                matcher.find();
                prize[1] = new BigInteger(matcher.group());
                task1 = task1.add(solve(btnA, btnB, prize));
                prize[0] = prize[0].add(offset);
                prize[1] = prize[1].add(offset);
                task2 = task2.add(solve(btnA, btnB, prize));
            }
        }
        System.out.println(task1);
        System.out.println(task2);
    }
}