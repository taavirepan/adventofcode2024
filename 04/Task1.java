import java.util.*;

class Task1 {
    static String substring(String[] data, int i, int j, int di, int dj, int len) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < len; k++) {
            if (i + k * di < 0 || i + k * di >= data.length || j + k * dj < 0 || j + k * dj >= data[i + k * di].length()) {
                break;
            }
            sb.append(data[i + k * di].charAt(j + k * dj));
        }
        return sb.toString();
    }
    
    static boolean find_pattern_2(String[] data, int i, int j) {
        boolean diag[] = new boolean[2];
        for (int k = 0; k < 2; k++) {
            diag[k] = substring(data, i, j + 2*k, 1, 1 - k*2, 3).equals("SAM");
            diag[k] |= substring(data, i, j + 2*k, 1, 1 - k*2, 3).equals("MAS");
        }
        return diag[0] && diag[1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] lines = scanner.useDelimiter("\n").tokens().toArray(String[]::new);
        int n = lines.length;
        int m = lines[0].length();
        int di[] = {1, 0, -1, 0, 1, 1, -1, -1};
        int dj[] = {0, 1, 0, -1, 1, -1, 1, -1};

        int task1 = 0;
        int task2 = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < 8; k++) {
                    if (substring(lines, i, j, di[k], dj[k], 4).equals("XMAS")) {
                        task1++;
                    }
                }
                if (find_pattern_2(lines, i, j)) {
                    task2++;
                }
            }
        }

        System.out.println(task1);
        System.out.println(task2);
    }
}
