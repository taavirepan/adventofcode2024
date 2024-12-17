import java.io.*;
import java.util.*;
import java.util.regex.*;

class Task1 {
    static String run(List<Integer> numbers) {
        int[] registers = numbers.subList(0, 3).stream().mapToInt(Integer::intValue).toArray();
        int[] program = numbers.subList(3, numbers.size()).stream().mapToInt(Integer::intValue).toArray();
        StringBuilder result = new StringBuilder();

        int ip = 0;
        while (ip < program.length) {
            int opcode = program[ip];
            int literal = program[ip+1];
            int combo = switch (literal) {
                case 4 -> registers[0];
                case 5 -> registers[1];
                case 6 -> registers[2];
                default -> literal;
            };
            switch (opcode) {
                case 0 -> {registers[0] = registers[0] / (1 << combo);}
                case 6 -> {registers[1] = registers[0] / (1 << combo);}
                case 7 -> {registers[2] = registers[0] / (1 << combo);}

                case 1 -> {registers[1] = registers[1] ^ literal;}
                case 2 -> {registers[1] = combo % 8;}
                case 3 -> {ip = (registers[0] != 0) ? literal - 2 : ip;}
                case 4 -> {registers[1] = registers[1] ^ registers[2];}
                case 5 -> {result.append(combo % 8 + ",");}

                default -> throw new RuntimeException("Unknown opcode: " + opcode);
            }
            ip += 2;
        }

        return result.toString().substring(0, result.length() - 1);
    }

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                numbers.add(Integer.parseInt(matcher.group()));
            }
        }
        System.out.println(run(numbers));
    }
}