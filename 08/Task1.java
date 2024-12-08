import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
class IntPair {
    int first;
    int second;

    IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    IntPair add(IntPair other, int k) {
        return new IntPair(first + other.first * k, second + other.second * k);
    }

    IntPair diff(IntPair other) {
        return new IntPair(first - other.first, second - other.second);
    }

    boolean is_on_grid(int size) {
        return first >= 0 && first < size && second >= 0 && second < size;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object other) {
        return first == ((IntPair) other).first && second == ((IntPair) other).second;
    }

    @Override
    public int hashCode() {
        return first * 1000000 + second;
    }
}

class Task1 {
    static HashMap<Character, List<IntPair>> read_map() {
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, List<IntPair>> map = new HashMap<>();
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c != '.') {
                    List<IntPair> list = map.get(c);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new IntPair(x, y));
                    map.put(c, list);
                }
            }
            y++;
        }
        map.put('.', new ArrayList<>());
        map.get('.').add(new IntPair(y, y));
        return map;
    }

    static void solve(List<IntPair> points, int size, Set<IntPair> antinodes, int max_dist) {
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                IntPair p1 = points.get(i);
                IntPair p2 = points.get(j);
                IntPair d = p2.diff(p1);

                int start = max_dist == 2 ? 2 : 1;
                for (int k = start; k <= max_dist; k++) {
                    IntPair n1 = p1.add(d, k);
                    IntPair n2 = p2.add(d, -k);
                    if (n1.is_on_grid(size)) {
                        antinodes.add(n1);
                    }
                    if (n2.is_on_grid(size)) {
                        antinodes.add(n2);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HashMap<Character, List<IntPair>> map = read_map();
        Set<IntPair> antinodes1 = new HashSet<>();
        Set<IntPair> antinodes2 = new HashSet<>();
        int size = map.get('.').get(0).first;
        for (char c : map.keySet()) {
            if (c != '.') {
                solve(map.get(c), size, antinodes1, 2);
                solve(map.get(c), size, antinodes2, size);
            }
        }
        System.out.println(antinodes1.size());
        System.out.println(antinodes2.size());
    }
}
