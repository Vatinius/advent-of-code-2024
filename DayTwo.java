import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DayTwo {
    public static ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();

    public static void fileToList() {
        String filePath = "DayTwo.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] levels = line.split("\\s+");
                listOfLists.add(new ArrayList<>());

                for (int i = 0; i < levels.length; i++) {
                    listOfLists.get(counter).add(Integer.parseInt(levels[i]));
                }

                counter++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e);
        }
    }

    public static void countSafeReports() {
        int safeCount = 0;
        boolean isSafe = true;

        for (int i = 0; i < listOfLists.size(); i++) {
            ArrayList<Integer> report = listOfLists.get(i);
            isSafe = true;
            boolean isIncreasing = report.get(0) > report.get(1);
            boolean isDecreasing = report.get(0) < report.get(1);

            for (int j = 0; j < report.size() - 1; j++) {
                int diff = report.get(j) - report.get(j + 1);

                if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                   isSafe = false;
                }

                if (isIncreasing && diff < 0 || isDecreasing && diff > 0) {
                    isSafe = false;
                }
            }

            if (isSafe) {
                safeCount++;
            }
        }

        System.out.println("Number of safe reports: " + safeCount);
    }

    public static void countSafeReportsWithTolerance() {
        int safeCount = 0;

        for (int i = 0; i < listOfLists.size(); i++) {
            if (isSafeWithRemoval(listOfLists.get(i))) {
                safeCount++;
            }
        }

        System.out.println("Number of safe reports: " + safeCount);
    }

    public static boolean isValid(ArrayList<Integer> levels) {
        if (levels == null || levels.size() < 2) {
            // A list with less than 2 elements is trivially valid
            return true;
        }

        boolean isIncreasing = levels.get(1) > levels.get(0);
        boolean isDecreasing = levels.get(1) < levels.get(0);

        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);

            // Check difference constraint
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check monotonicity
            if ((isIncreasing && diff < 0) || (isDecreasing && diff > 0)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isSafeWithRemoval(ArrayList<Integer> levels) {
        if (isValid(levels)) {
            // Already safe without any removals
            return true;
        }

        // Check if removing one level makes the report safe
        for (int i = 0; i < levels.size(); i++) {
            ArrayList<Integer> modified = new ArrayList<>(levels);
            modified.remove(i); // Temporarily remove the level
            if (isValid(modified)) {
                return true; // Found a valid report after removal
            }
        }

        return false; // Unsafe even with a single removal
    }

    public static void main(String[] args) {
        fileToList();
        countSafeReportsWithTolerance();
    }
}
