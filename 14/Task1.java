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
    }
}
