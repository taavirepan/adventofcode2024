import java.util.*;

class Task1 {
    static int task1(char[][] grid, int i, int j) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int di = -1;
        int dj = 0;
        int ret = 0;
        int n = 0;
        while (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
            if (n > 130*130)
                return -1;
            if (grid[i][j] == '#') {
                int tmp = di;
                i -= di;
                j -= dj;
                di = dj;
                dj = -tmp;
                continue;
            }
            if (!visited[i][j]) {
                ret++;
            }
            visited[i][j] = true;
            i += di;
            j += dj;
            n++;
        }
        return ret;
    }

    static int task2(char[][] grid, int startLine, int startCol) {
        int ret = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '#') {
                    continue;
                }
                if (i == startLine && j == startCol) {
                    continue;
                }
                grid[i][j] = '#';
                if (task1(grid, startLine, startCol) == -1) {
                    ret++;
                }
                grid[i][j] = '.';
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        int startLine = -1;
        int startCol = -1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
            if (line.indexOf('^') != -1) {
                startLine = lines.size() - 1;
                startCol = line.indexOf('^');
            }
        }
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        System.out.println(task1(grid, startLine, startCol));
        System.out.println(task2(grid, startLine, startCol));
    }
}
