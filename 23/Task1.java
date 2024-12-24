import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

class Task1 {
    static void triplets(Map<String, Set<String>> connections, Consumer<String[]> consumer) {
        for (String first: connections.keySet()) {
            for (String second: connections.get(first)) {
                for (String third: connections.get(second)) {
                    if (connections.get(third).contains(first)) {
                        consumer.accept(new String[] { first, second, third });
                    }
                    else if (connections.get(first).contains(third)) {
                        consumer.accept(new String[] { first, third, second });
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Set<String>> connections = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("-");
            String first = parts[0];
            String second = parts[1];
            
            connections.computeIfAbsent(first, k -> new HashSet<>()).add(second);
        }

        int[] task1 = {0};
        Set<String> seen = new HashSet<>();
        triplets(connections, (String[] triplet) -> {
            if (Arrays.stream(triplet).noneMatch(s -> s.charAt(0) == 't')) {
                return;
            }
            Arrays.sort(triplet);
            String key = String.join(",", triplet);
            if (!seen.contains(key)) {
                task1[0]++;
                seen.add(key);
            }
        });
        System.out.println(task1[0]);
    }
}