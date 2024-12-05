import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task1 {
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
        }
        System.out.println(task1);
    }
}
