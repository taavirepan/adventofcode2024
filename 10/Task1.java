import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

interface TryDirection {
    void try_direction(int xn, int yn);
}

class Path {
    List<int[]> path;
    public boolean[][] visited;

    static Path init(int x, int y, int n) {
        List<int[]> p = new ArrayList<>();
        p.add(new int[] { x, y });
        boolean[][] v = new boolean[n][n];
        v[x][y] = true;
        return new Path(p, v);
    }

    public Path(List<int[]> p, boolean[][] v) {
        path = p;
        visited = v;
    }

    @Override
    public Path clone() {
        boolean[][] v = new boolean[visited.length][visited[0].length];
        for (int x = 0; x < visited.length; x++) {
            for (int y = 0; y < visited[0].length; y++) {
                v[x][y] = visited[x][y];
            }
        }
        
        return new Path(new ArrayList<>(path), v);
    }

    public Path extend(int x, int y) {
        Path ret = clone();
        ret.path.add(new int[] { x, y });
        ret.visited[x][y] = true;
        return ret;
    }
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

    static int trails(int[][] grid, int i, int j) {
        int ret = 0;
        Queue<Path> queue = new LinkedList<>();
        queue.add(Path.init(i, j, grid.length));
        while (!queue.isEmpty()) {
            Path current = queue.poll();
            int x = current.path.get(current.path.size() - 1)[0];
            int y = current.path.get(current.path.size() - 1)[1];
            if (grid[x][y] == 9) {
                ret++;
            }
            int dx[] = { 1, -1, 0, 0 };
            int dy[] = { 0, 0, 1, -1 };
            for (int d = 0; d < 4; d++) {
                int xn = x + dx[d];
                int yn = y + dy[d];
                if (xn < 0 || xn >= grid.length || yn < 0 || yn >= grid[0].length) {
                    continue;
                }
                if (grid[xn][yn] - grid[x][y] == 1 && !current.visited[xn][yn]) {
                    queue.add(current.extend(xn, yn));
                }
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
