import java.util.*;

class Task1 {
    static int find_region(char[][] grid, int x, int y) {
        char r = grid[x][y];
        int area = 0;
        int perimeter = 0;
        List<int[]> stack = new ArrayList<>();
        stack.add(new int[] { x, y });
        while (!stack.isEmpty()) {
            int[] p = stack.remove(0);
            int px = p[0];
            int py = p[1];

            if ((grid[px][py] & 256) != 0) {
                continue;
            }

            area++;
            grid[px][py] |= 256;

            int[] dx = { 0, 1, 0, -1 };
            int[] dy = { 1, 0, -1, 0 };
            for (int i = 0; i < 4; i++) {
                int nx = px + dx[i];
                int ny = py + dy[i];
                if (nx < 0 || nx >= grid.length || ny < 0 || ny >= grid[0].length) {
                    perimeter ++;
                } else if (grid[nx][ny] == r) {
                    stack.add(new int[] { nx, ny });
                } else if (grid[nx][ny] != (r|256)) {
                    perimeter++;
                }
            }
        }
        return area * perimeter;
    }

    static int task1(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        int ret = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if ((grid[i][j] & 256) == 0) {
                    char c = grid[i][j];
                    int r = find_region(grid, i, j);
                    ret += r;
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        System.out.println(task1(lines));
    }
}
