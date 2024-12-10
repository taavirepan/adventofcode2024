import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

interface TryMove<T> {
    T tryMove(T current, int dx, int dy);
}   

interface GetPosition<T> {
    int[] getPosition(T current);
}

class Search<T>
{
    int[][] grid;
    Queue<T> queue;
    GetPosition<T> getPosition;

    public Search(int[][] grid, T start, GetPosition<T> getPosition) {
        this.grid = grid;
        this.queue = new LinkedList<>();
        this.queue.add(start);
        this.getPosition = getPosition;
    }

    public int search(TryMove<T> tryMove) {
        int ret = 0;
        while (!queue.isEmpty()) {
            T current = queue.poll();
            int[] position = getPosition.getPosition(current);
            int x = position[0];    
            int y = position[1];
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
                T next = tryMove.tryMove(current, xn, yn);
                if (next != null) {
                    queue.add(next);
                }
            }
        }
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
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Search<int[]> search = new Search<>(grid, new int[] { i, j }, (int[] current) -> {
            return current;
        });
        return search.search((int[] current, int xn, int yn) -> {
            if (visited[xn][yn]) {
                return null;
            }
            visited[xn][yn] = true;
            return new int[] { xn, yn };
        });
    }

    static int trails(int[][] grid, int i, int j) {
        Search<List<int[]>> search = new Search<>(grid, new ArrayList<>(Arrays.asList(new int[] { i, j })), (List<int[]> current) -> {
            return current.get(current.size() - 1);
        });
        return search.search((List<int[]> current, int xn, int yn) -> {
            if (current.contains(new int[] { xn, yn })) {
                return null;
            }
            List<int[]> new_current = new ArrayList<>(current);
            new_current.add(new int[] { xn, yn });
            return new_current;
        });
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
