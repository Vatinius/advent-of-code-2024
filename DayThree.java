import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThree {

    public static void multiplier() {
        String filePath = "DayThree.txt";
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String corruptedMemory = fileContent.toString();

        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(corruptedMemory);

        int sum = 0;

        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            sum += x * y;
        }

        System.out.println("Total sum of valid mul instructions: " + sum);
    }

    public static void conditionalMultiplier() {
        String filePath = "DayThree.txt";
        StringBuilder fileContent = new StringBuilder();

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String corruptedMemory = fileContent.toString();

        String mulRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        String toggleRegex = "do\\(\\)|don't\\(\\)"; // Toggle enable/disable

        // Pattern for matching instructions
        Pattern mulPattern = Pattern.compile(mulRegex);
        Pattern togglePattern = Pattern.compile(toggleRegex);

        int sum = 0;
        boolean isEnabled = true; // Initially, mul instructions are enabled

        // Process the corrupted memory sequentially
        Matcher toggleMatcher = togglePattern.matcher(corruptedMemory);
        Matcher mulMatcher = mulPattern.matcher(corruptedMemory);

        int currentIndex = 0;

        // Loop through the corrupted memory
        while (currentIndex < corruptedMemory.length()) {
            // Check for the next toggle instruction
            if (toggleMatcher.find(currentIndex)) {
                currentIndex = toggleMatcher.start();

                // Update the enabled/disabled state
                if (toggleMatcher.group().equals("do()")) {
                    isEnabled = true;
                } else if (toggleMatcher.group().equals("don't()")) {
                    isEnabled = false;
                }

                // Move past the toggle instruction
                currentIndex = toggleMatcher.end();
            }

            // Check for the next mul instruction
            if (mulMatcher.find(currentIndex)) {
                currentIndex = mulMatcher.start();

                // Process mul only if enabled
                if (isEnabled) {
                    int x = Integer.parseInt(mulMatcher.group(1));
                    int y = Integer.parseInt(mulMatcher.group(2));
                    sum += x * y; // Multiply and add to the sum
                }

                // Move past the mul instruction
                currentIndex = mulMatcher.end();
            } else {
                // If no more mul instructions, exit the loop
                break;
            }
        }

        // Output the result
        System.out.println("Total sum of valid mul instructions: " + sum);
    }

    public static void main(String[] args) {
        multiplier();
        conditionalMultiplier();
    }
}
