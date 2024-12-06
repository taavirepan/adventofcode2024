import java.util.*;

class Task1 {
    static int task1(List<String> lines, int i, int j) {
        boolean[][] visited = new boolean[lines.size()][lines.get(0).length()];
        int di = -1;
        int dj = 0;
        int ret = 0;
        while (i >= 0 && i < lines.size() && j >= 0 && j < lines.get(0).length()) {
            if (lines.get(i).charAt(j) == '#') {
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
        System.out.println(task1(lines, startLine, startCol));
    }
}
