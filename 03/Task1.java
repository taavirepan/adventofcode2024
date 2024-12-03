import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        int task1 = 0;        
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                i = matcher.end();
                int a = Integer.parseInt(matcher.group(1));
                int b = Integer.parseInt(matcher.group(2));
                task1 += a * b;
            }
        }

        System.out.println(task1);
    }
}
