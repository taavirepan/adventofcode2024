import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task1 {
    static List<Integer> reorder(List<Integer> numbers, List<Integer> rules1, List<Integer> rules2) {
        List<Integer> result = new ArrayList<>();
        return result;
    }

    static int[][] processRules(List<Integer> rules1, List<Integer> rules2) {
        int table[][] = new int[100][100];
        for (int i = 0; i < rules1.size(); i++) {
            int a = rules1.get(i);
            int b = rules2.get(i);
            table[a][b] = 1;
            table[b][a] = -1;
        }
        return table;
    }

    static int solve_task2(List<Integer> numbers, int[][] sorting) {
        numbers.sort((a, b) -> sorting[a][b]);
        System.out.println(numbers);
        return numbers.get(numbers.size() / 2);
    }

    public static void main(String[] args) {
        List<Integer> rules1 = new ArrayList<>();
        List<Integer> rules2 = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            String[] parts = line.split("\\|");
            rules1.add(Integer.parseInt(parts[0]));
            rules2.add(Integer.parseInt(parts[1]));
        }

        int task1 = 0;
        int task2 = 0;
        int[][] sorting = processRules(rules1, rules2);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                numbers.add(Integer.parseInt(parts[i]));
            }
            boolean valid = true;
            for (int i = 0; i < rules1.size(); i++) {
                int i1 = numbers.indexOf(rules1.get(i));
                int i2 = numbers.indexOf(rules2.get(i));
                if (i1 == -1 || i2 == -1) {
                    continue;
                }
                if (i1 > i2) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                task1 += numbers.get(numbers.size() / 2);
            }
            else {
                task2 += solve_task2(numbers, sorting);
            }
        }
        System.out.println(task1);
        System.out.println(task2);
    }
}
