import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DayOne {
    public static ArrayList<Integer> leftList = new ArrayList<>();
    public static ArrayList<Integer> rightList = new ArrayList<>();
    public static HashMap<Integer, Integer> rightListHashMap = new HashMap<>();

    public static void fileToList() {
        String filePath = "DayOne.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\\s+");
                int leftInt = Integer.parseInt(columns[0]);
                int rightInt = Integer.parseInt(columns[1]);

                leftList.add(leftInt);
                rightList.add(rightInt);

                if (!rightListHashMap.containsKey(rightInt)) {
                    rightListHashMap.put(rightInt, 1);
                } else if (rightListHashMap.containsKey(rightInt)) {
                    int existingValue = rightListHashMap.get(rightInt);
                    rightListHashMap.put(rightInt, existingValue + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e);
        }
    }

    public static void findTotalDistance() {
        Collections.sort(leftList);
        Collections.sort(rightList);
        int totalDistance = 0;

        for(int i = 0; i < leftList.size(); i++) {
            int difference = leftList.get(i) - rightList.get(i);

            if (difference < 0) {
                difference = -difference;
            }

            totalDistance = totalDistance + difference;
        }

        System.out.println("The total distance is: " + totalDistance);
    }

    public static void findSimilarityScore() {
        int similarityScore = 0;

        for (int i = 0; i < leftList.size(); i++) {
            int key = leftList.get(i);

            if (rightListHashMap.containsKey(key)) {
                if (rightListHashMap.get(key) != null) {
                    int frequency = rightListHashMap.get(key);
                    similarityScore = similarityScore + key * frequency;
                }
            }
        }

        System.out.println("Similarity score is: " + similarityScore);
    }


    public static void main(String[] args) {
        fileToList();
        findTotalDistance();
        findSimilarityScore();
    }
}
