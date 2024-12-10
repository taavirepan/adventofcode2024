import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

interface TryDirection {
    void try_direction(int xn, int yn);
}

class Task1 {
    static int[][] readGrid() {
        int[][] ret = null;
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (ret == null) {
                ret = new int[line.length()][line.length()];
            }
            ret[i] = line.chars().map(c -> c - '0').toArray();
            i++;
        }
        return ret;
    }

    static int destinations(int[][] grid, int i, int j) {
        int ret = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { i, j });
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            if (visited[x][y]) {
                continue;
            }
            if (grid[x][y] == 9) {
                ret ++;
            }
            visited[x][y] = true;

            TryDirection L = (int xn, int yn) -> {
                if (xn < 0 || xn >= grid.length || yn < 0 || yn >= grid[0].length) {
                    return;
                }
                if (grid[xn][yn] - grid[x][y] == 1) {
                    queue.add(new int[] { xn, yn });
                }
            };

            L.try_direction(x + 1, y);
            L.try_direction(x - 1, y);
            L.try_direction(x, y + 1);
            L.try_direction(x, y - 1);
        }
        return ret;
    }

    public static void main(String[] args) {
        int[][] grid = readGrid();
        int task1 = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    task1 += destinations(grid, i, j);
                }
            }
        }
        System.out.println(task1);
    }
}
