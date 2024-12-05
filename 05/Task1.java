import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Task1 {
    static int[][] readRules(Scanner scanner) {
        String line;
        int table[][] = new int[100][100];
        while (!(line = scanner.nextLine()).equals("")) {
            String[] p = line.split("\\|");
            int a = Integer.parseInt(p[0]);
            int b = Integer.parseInt(p[1]);
            table[a][b] = -1;
            table[b][a] = 1;
        }
        return table;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] sorting = readRules(scanner);
        int task1 = 0;
        int task2 = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Integer> numbers = Arrays.stream(line.split(","))
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());
            List<Integer> sorted = new ArrayList<>(numbers);
            sorted.sort((a, b) -> sorting[a][b]);
            if (sorted.equals(numbers)) {
                task1 += numbers.get(numbers.size() / 2);
            }
            else {
                task2 += sorted.get(sorted.size() / 2);
            }
        }
        System.out.println(task1);
        System.out.println(task2);
    }
}
