import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    static void connectedNodes(String node, Map<String, Set<String>> connections, Consumer<String> consumer) {
        for (String key: connections.get(node)) {
            consumer.accept(key);
        }
        for (String key: connections.keySet()) {
            if (connections.get(key).contains(node)) {
                consumer.accept(key);
            }
        }
    }

    static boolean isFullyConnected(Set<String> set, Map<String, Set<String>> connections) {
        for (String key: set) {
            for (String other: set) {
                if (key.equals(other)) {
                    continue;
                }
                if (!connections.get(key).contains(other) && !connections.get(other).contains(key)) {
                    return false;
                }
            }
        }
        return true;
    }

    static void fullyConnectedSubsets(Set<String> set, Map<String, Set<String>> connections, Consumer<Set<String>> consumer) {
        int size = set.size();
        String[] array = set.toArray(new String[0]);
        for (int i = 0; i < 1 << size; i++) {
            Set<String> subset = new HashSet<>();
            for (int j = 0; j < size; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(array[j]);
                }
            }
            if (isFullyConnected(subset, connections)) {
                consumer.accept(subset);
            }
        }
    }

    static void connectedSets(Map<String, Set<String>> connections, Consumer<Set<String>> consumer) {
        Set<String> seen = new HashSet<>();
        for (String key: connections.keySet()) {
            if (seen.contains(key)) {
                continue;
            }
            Set<String> set = new HashSet<>();
            set.add(key);
            connectedNodes(key, connections, set::add);
            fullyConnectedSubsets(set, connections, consumer);
        }
    }

    static Set<String> largestSet(Map<String, Set<String>> connections) {
        Set<String> largest = new HashSet<>();
        connectedSets(connections, set -> {
            if (set.size() > largest.size()) {
                largest.clear();
                largest.addAll(set);
            }
        });
        return largest;
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
        
        System.out.println(String.join(",", largestSet(connections).stream().sorted().collect(Collectors.toList())));
    }
}
