import java.util.*;

class Task1 {
    static int rot4(int x) {
        return (x << 1 | x >> 3) & 0b1111;
    }

    static int rot(int x) {
        return rot4(x & 0b1111) | rot4(x >> 4) << 4;
    }

    static int count_corners(int bits) {
        int ret = 0;
        for (int i = 0; i < 4; i++) {
            int nbors = bits & 0b1111;
            int diags = bits >> 4;
            bits = rot(bits);

            if ((nbors & 0b1001) == 0) { /* /\ corner */
                ret ++;
            }
            if ((nbors & 0b1001) == 0b1000 && (diags & 0b1000) != 0) { /* \/ corner */
                ret ++;
            }
        }
        return ret;
    }

    static int[] find_region(char[][] grid, int x, int y) {
        char r = grid[x][y];
        int area = 0;
        int perimeter = 0;
        int edges = 0;
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

            int[] dx = { 0, 1, 0, -1, 1, 1, -1, -1 };
            int[] dy = { 1, 0, -1, 0, 1, -1, -1, 1 };
            int bits = 0;
            for (int i = 0; i < 8; i++) {
                int nx = px + dx[i];
                int ny = py + dy[i];
                if (nx < 0 || nx >= grid.length || ny < 0 || ny >= grid[0].length) {
                    perimeter += i < 4 ? 1 : 0;
                } else if (grid[nx][ny] == r) {
                    if (i < 4) {
                        stack.add(new int[] { nx, ny });
                    }
                    bits |= 1 << i;
                } else if (grid[nx][ny] != (r|256)) {
                    perimeter += i < 4 ? 1 : 0;
                } else {
                    bits |= 1 << i;
                }
                
            }
            int corners = count_corners(bits);
            edges += corners;
        }
        return new int[] { area * perimeter, area * edges };
    }

    static int[] solve(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        int[] ret = { 0, 0 };
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if ((grid[i][j] & 256) == 0) {
                    char c = grid[i][j];
                    int[] r = find_region(grid, i, j);
                    ret[0] += r[0];
                    ret[1] += r[1];
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
        int[] ret = solve(lines);
        System.out.println(ret[0]);
        System.out.println(ret[1]);
    }
}
