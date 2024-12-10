import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


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
            int[] dx = { 1, -1, 0, 0 };
            int[] dy = { 0, 0, 1, -1 };
            for (int d = 0; d < 4; d++) {
                int xn = x + dx[d];
                int yn = y + dy[d];
                if (xn < 0 || xn >= grid.length || yn < 0 || yn >= grid[0].length) {
                    continue;
                }
                if (grid[xn][yn] - grid[x][y] != 1) {
                    continue;
                }
                queue.add(new int[] { xn, yn });
            }
        }
        return ret;
    }

    static int trails(int[][] grid, int i, int j) {
        int ret = 0;
        Queue<List<int[]>> queue = new LinkedList<>();
        queue.add(new ArrayList<>(Arrays.asList(new int[] { i, j })));
        while (!queue.isEmpty()) {
            List<int[]> current = queue.poll();
            int x = current.get(current.size() - 1)[0];
            int y = current.get(current.size() - 1)[1];
            if (grid[x][y] == 9) {
                ret ++;
            }

            int[] dx = { 1, -1, 0, 0 };
            int[] dy = { 0, 0, 1, -1 };
            for (int d = 0; d < 4; d++) {
                int xn = x + dx[d];
                int yn = y + dy[d];
                if (xn < 0 || xn >= grid.length || yn < 0 || yn >= grid[0].length) {
                    continue;
                }
                if (grid[xn][yn] - grid[x][y] != 1) {
                    continue;
                }
                if (current.contains(new int[] { xn, yn })) {
                    continue;
                }
                List<int[]> new_current = new ArrayList<>(current);
                new_current.add(new int[] { xn, yn });
                queue.add(new_current);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[][] grid = readGrid();
        int task1 = 0;
        int task2 = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    task1 += destinations(grid, i, j);
                    task2 += trails(grid, i, j);
                }
            }
        }
        System.out.println(task1);
        System.out.println(task2);
    }
}
