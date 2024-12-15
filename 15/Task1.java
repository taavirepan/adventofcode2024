import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class Grid {
    char[][] data;

    public Grid(String[] data) {
        this.data = new char[data.length][];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i].toCharArray();
        }
    }

    public int[] find(char c) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == c) {
                    return new int[] { j, i };
                }
            }
        }
        return null;
    }

    public void forEach(char c, Consumer<int[]> consumer) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == c) {
                    consumer.accept(new int[] { j, i });
                }
            }
        }
    }

    public char get(int x, int y) {
        return data[y][x];
    }

    public void set(int x, int y, char c) {
        data[y][x] = c;
    }

    public String getString(int x, int y, int dx, int dy) {
        StringBuilder sb = new StringBuilder();
        sb.append(get(x, y));
        while (sb.charAt(sb.length() - 1) != '#') {
            x += dx;
            y += dy;
            if (x < 0 || x >= data[0].length || y < 0 || y >= data.length) {
                break;
            }
            sb.append(get(x, y));
        }
        return sb.toString();
    }

    public void setString(int x, int y, int dx, int dy, String s) {
        for (int i = 0; i < s.length(); i++) {
            set(x + i * dx, y + i * dy, s.charAt(i));
        }
    }

    public String toString() {
        return Arrays.stream(data).map(String::new).collect(Collectors.joining("\n"));
    }
}

class Task1 {
    static String[] readMap(Scanner scanner) {
        String line = scanner.nextLine();
        String[] map = new String[line.length()];
        map[0] = line;
        for (int i = 1; i < line.length(); i++) {
            map[i] = scanner.nextLine();
        }
        return map;
    }

    static int execute(String[] map, String commands) {
        Grid grid = new Grid(map);
        // System.out.println(grid);
        for (char command : commands.toCharArray()) {
            int dx = 0, dy = 0;
            switch (command) {
                case 'v' -> dy = 1;
                case '^' -> dy = -1;
                case '>' -> dx = 1;
                case '<' -> dx = -1;
            }
            int[] pos = grid.find('@');
            char t = grid.get(pos[0] + dx, pos[1] + dy);
            if (t == '.') {
                grid.set(pos[0] + dx, pos[1] + dy, '@');
                grid.set(pos[0], pos[1], '.');
            }
            else if (t != '#') {
                String s = grid.getString(pos[0], pos[1], dx, dy);
                int i = s.indexOf('.');
                if (i != -1) {
                    s = ".@" + s.substring(1, i) + s.substring(i + 1);
                    grid.setString(pos[0], pos[1], dx, dy, s);
                }
            }
        }
        int[] ret = {0};
        grid.forEach('O', pos -> ret[0] += pos[1] * 100 + pos[0]);
        // System.out.println(grid);
        return ret[0];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] map = readMap(scanner);
        String commands = scanner.nextLine();
        while (scanner.hasNextLine()) {
            commands += scanner.nextLine();
        }
        System.out.println(execute(map, commands));
    }
}
