import java.io.*;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.regex.*;

class Task1 {
    static boolean run(long[] registersIn, List<Integer> numbers, IntPredicate predicate, int loops) {
        long[] registers = Arrays.copyOf(registersIn, 3);
        int[] program = numbers.stream().mapToInt(Integer::intValue).toArray();

        int ip = 0;
        while (ip < program.length) {
            int opcode = program[ip];
            int literal = program[ip+1];
            long combo = switch (literal) {
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
                case 3 -> {
                    if (loops == -1) {
                        ip = (registers[0] != 0) ? literal - 2 : ip;
                    }
                    else if (loops > 0){
                        ip = literal - 2;
                        loops --;
                    }
                }
                case 4 -> {registers[1] = registers[1] ^ registers[2];}
                case 5 -> {if (!predicate.test((int)(combo % 8))) {return false;}}

                default -> throw new RuntimeException("Unknown opcode: " + opcode);
            }
            ip += 2;
        }
        return true;
    }

    static Set<Integer> subsolve(List<Integer> numbers, int num, long start, int shift) {
        long[] registers = {0, 0, 0};
        List<Integer> program = numbers.subList(3, numbers.size());
        Set<Integer> counts = new HashSet<>();
        for (long i = 0; i < 1L << 16; i++) {
            registers[0] = start | (i << shift);
            List<Integer> check = new ArrayList<>();
            run(registers, program, check::add, num);
            if (check.subList(0, num).equals(program.subList(0, num))) {
                int pattern = (int)(i & ((1 << 8) - 1));
                counts.add(pattern);
            }
        }
        return counts;
    }

    static long task2(List<Integer> numbers, int num, long start, int shift) {
        Set<Integer> counts = subsolve(numbers, num, start, shift);
        long ret = Long.MAX_VALUE;
        for (int key : counts) {
            long next = start | ((long)key << shift);
            if (num == 15) {
                ret = Math.min(ret, task2(numbers, num + 1, next, shift + 8));
            }
            else if (num == 16) {
                long registers[] = {next, 0, 0};
                List<Integer> result = new ArrayList<>();
                run(registers, numbers.subList(3, numbers.size()), result::add, -1);
                if (result.equals(numbers.subList(3, numbers.size()))) {
                    ret = Math.min(ret, next);
                }
            }
            else {
                ret = Math.min(ret, task2(numbers, num + 3, next, shift + 8));
            }
        }
        return ret;
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
        {
            long[] registers = numbers.subList(0, 3).stream().mapToLong(Integer::longValue).toArray();
            List<Integer> result = new ArrayList<>();
            run(registers, numbers.subList(3, numbers.size()), i -> {result.add(i); return true;}, -1);
            System.out.println(String.join(",", result.stream().map(Object::toString).toArray(String[]::new)));
        }

        System.out.println(task2(numbers, 3, 0, 0));
    }
}
