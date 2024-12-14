import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

class Task1 {
    static int solve(List<int[]> robots, int w, int h) {
        int time = 100;
        int[] ret = new int[4];
        for (int[] robot : robots) {
            int vx = (robot[2] + w) % w;
            int vy = (robot[3] + h) % h;
            int rx = (robot[0] + vx * time) % w;
            int ry = (robot[1] + vy * time) % h;
            if (rx < w/2 && ry < h/2) {
                ret[0]++;
            } else if (rx < w/2 && ry > h/2) {
                ret[1]++;
            } else if (rx > w/2 && ry < h/2) {
                ret[2]++;
            } else if (rx > w/2 && ry > h/2) {
                ret[3]++;
            }
        }
        return ret[0] * ret[1] * ret[2] * ret[3];
    }

    static void printState(int[][] state, int w, int h) {
        boolean[][] grid = new boolean[h][w];
        for (int[] robot : state) {
            grid[robot[1]][robot[0]] = true;
        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(grid[i][j] ? '#' : '.');
            }
            System.out.println();
        }
    }

    static int evolve(List<int[]> robots, int w, int h) {
        int[][] state = robots.toArray(new int[robots.size()][4]);
        int bestCloseness = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            int closeness = 0;
            
            for (int j = 0; j < state.length; j++) {
                int vx = (state[j][2] + w) % w;
                int vy = (state[j][3] + h) % h;
                state[j][0] = (state[j][0] + vx) % w;
                state[j][1] = (state[j][1] + vy) % h;
                for (int k = 0; k < j; k++) {
                    closeness += Math.abs(state[j][0] - state[k][0]) + Math.abs(state[j][1] - state[k][1]);
                }
            }
            if (closeness < bestCloseness) {
                bestCloseness = closeness;
                System.out.println((i + 1) + ": " + closeness);
                printState(state, w, h);
            }
        }
        return 0;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern p = Pattern.compile("\\d+");
        List<int[]> robots = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] robot = Pattern.compile("-?\\d+")
                .matcher(line)
                .results()
                .map(MatchResult::group)
                .mapToInt(Integer::parseInt)
                .toArray();
            robots.add(robot);
        }
        System.out.println(solve(robots, 11, 7));
        System.out.println(solve(robots, 101, 103));
        System.out.println(evolve(robots, 101, 103));
    }
}
