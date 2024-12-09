import java.math.BigInteger;
import java.util.Scanner;

class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input = scanner.nextLine().toCharArray();
        for (int i = 0; i < input.length; i++) {
            input[i] -= '0';
        }

        BigInteger task1 = BigInteger.ZERO;
        int i = 0;
        int j = input.length - 1;
        int p = 0;

        while (i <= j) {
            int n = input[i];
            for (int k = 0; k < n; k++) {
                task1 = task1.add(BigInteger.valueOf((i/2) * (p++)));
            }
            i++;
            if (i > j) {
                break;
            }
            while (input[i] > 0) {
                if (input[j] == 0) {
                    j -= 2;
                }
                if (j < i) {
                    break;
                }
                task1 = task1.add(BigInteger.valueOf((j/2) * (p++)));
                input[i]--;
                input[j]--;
            }
            i++;
        }

        System.out.println(task1);
    }
}
