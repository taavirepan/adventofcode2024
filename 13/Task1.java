import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Task1 {
    static int solve(int[] btnA, int[] btnB, int[] prize) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int x = btnA[0] * i + btnB[0] * j;
                int y = btnA[1] * i + btnB[1] * j;
                if (x == prize[0] && y == prize[1]) {
                    return 3 * i + j;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern numbers = Pattern.compile("\\d+");
        int[] btnA = new int[2];
        int[] btnB = new int[2];
        int[] prize = new int[2];
        int task1 = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Button A")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                btnA[0] = Integer.parseInt(matcher.group());
                matcher.find();
                btnA[1] = Integer.parseInt(matcher.group());
            }
            else if (line.startsWith("Button B")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                btnB[0] = Integer.parseInt(matcher.group());
                matcher.find();
                btnB[1] = Integer.parseInt(matcher.group());
            }
            else if (line.startsWith("Prize")) {
                Matcher matcher = numbers.matcher(line);
                matcher.find();
                prize[0] = Integer.parseInt(matcher.group());
                matcher.find();
                prize[1] = Integer.parseInt(matcher.group());
                task1 += solve(btnA, btnB, prize);
            }
        }
        System.out.println(task1);
    }
}