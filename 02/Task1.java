import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

class Task1 {
    public static int[] delta(int[] numbers) {
        int[] ret = new int[numbers.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = numbers[i + 1] - numbers[i];
        }
        return ret;
    }

    public static int[] delta2(int[] numbers, int skip) {
        int[] ret = IntStream.range(0, numbers.length)
                .filter(i -> i != skip)
                .map(i -> numbers[i])
                .toArray();
        return delta(ret);
    }

    public static boolean task1(int[] deltas) {
        return Arrays.stream(deltas).allMatch(d -> d > 0 && d < 4) || Arrays.stream(deltas).allMatch(d -> d > -4 && d < 0);
    }

    public static boolean task2(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (task1(delta2(numbers, i))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ret1 = 0;
        int ret2 = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int[] numbers = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
            if (task1(delta(numbers))) {
                ret1 ++;
                ret2 ++;
            }
            else if (task2(numbers)) {
                ret2 ++;
            }
        }
        System.out.println(ret1);
        System.out.println(ret2);
    }
}
