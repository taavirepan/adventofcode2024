import java.util.Arrays;
import java.util.Scanner;

class Task1 {
    public static int[] delta(int[] numbers) {
        int[] ret = new int[numbers.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = numbers[i + 1] - numbers[i];
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ret = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int[] numbers = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
            int[] deltas = delta(numbers);
            if (Arrays.stream(deltas).allMatch(d -> d > 0 && d < 4)) {
                ret ++;
            }
            else if (Arrays.stream(deltas).allMatch(d -> d > -4 && d < 0)) {
                ret ++;
            }
        }
        System.out.println(ret);
    }
}
