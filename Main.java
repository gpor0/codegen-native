import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        int length = 6;
        int number = 1;

        if (args.length < 2) {
            System.err.println("Invalid arguments. usage: codeGen {numberOfCodes} {codeLength}");
            System.exit(1);
        }

        if (args.length > 0) {
            String arg1 = args[0];
            try {
                number = Integer.parseInt(arg1);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number: " + arg1);
                System.exit(1);
            }

            if (args.length > 1) {
                String arg2 = args[1];
                try {
                    length = Integer.parseInt(arg2);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number: " + arg1);
                    System.exit(1);
                }
            }
        }

        String fileName = "code.db";
        File file = new File(fileName);
        file.createNewFile();
        Set<String> codeSet;
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            codeSet = lines.collect(Collectors.toSet());
        }
        System.out.println("Loaded " + codeSet.size() + " codes. Generating " + number + " codes of length " + length);

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true), "UTF-8"));

        int i = 0;
        while (i < number) {
            String code = getRandomString(length);
            if (codeSet.contains(code)) {
                continue;
            }
            writer.append(code + "\n\r");
            writer.flush();
            System.out.println(code);
            i++;
        }

    }

    protected static String getRandomString(int length) {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }

        return salt.toString();
    }

}
